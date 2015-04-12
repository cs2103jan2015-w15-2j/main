//@author A0111035A
package com.nexus.simplify.parser.core;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.parser.data.CommandData;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * 
 * This DateTime parser relies heavily on Natty library @ http://natty.joestelmach.com}
 * 
 * @author Davis
 *
 */
public class DateTimeParser extends TokenParser {
	Logger LOGGER = LoggerFactory.getLogger(DateTimeParser.class.getName());
	Parser natty = new Parser();
	CommandData commandData = CommandData.getInstance();

	// Key strings to search for in user input parse tree
	// Used to identify what aspect of time the user is searching for
	final String YEAR = "YEAR_OF";
	final String MONTH = "MONTH_OF_YEAR";
	final String DAY_OF_MONTH = "DAY_OF_MONTH";
	final String DAY_OF_WEEK = "DAY_OF_WEEK";
	final String HOURS = "HOURS_OF_DAY";
	final String MINUTES = "MINUTES_OF_HOUR";

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String[] remainingValid = tokenList.clone();
			while (enclosedInDoubleQuotes(remainingValid)) {
				remainingValid = trimTokensInQuotes(remainingValid);
			}
			// Convert remaingValid tokens to string to use natty library.
			String tokenString = tokenListToStr(remainingValid);
			List<DateGroup> groupList = natty.parse(tokenString);

			// Do not proceed with parsing if no DateTime is parsed from tokenString
			if (groupList.size() <1) {
				String invalidTokens = "";
				// Natty has cornercase bug where it does not detect DateTime elements that 
				// follows a word containing timezone letters and IN token
				// for example, add testing 1600, where EST is a timezone and IN is a valid token
				// other examples are psting 1800, cstin 
				boolean retrySuccess = false;
				for (int i = 0; i < remainingValid.length; i++) {
					String string = remainingValid[i];
					groupList = natty.parse(string);
					if (groupList.size() > 0) {
						retrySuccess = true;
						invalidTokens = invalidTokens.trim();
						remainingValid = getRemainingTokens(invalidTokens, remainingValid);
						break;
					} else {
						invalidTokens += " " + remainingValid[i];
					}
				}
				if (!retrySuccess) {
					LOGGER.info("No DateTime is parsed.");
					return tokenList;
				}
			}
			// Gets a list of all dates in the only group of dates parsed.
			DateGroup dateGroup = groupList.get(0);
			List<Date> dates = groupList.get(0).getDates();
			String stringParsed = dateGroup.getText();
			LOGGER.info("String parsed: {}", stringParsed);				

			// Ensure that we proceed setting time elements for fully parsed words.
			// This is because Natty will try to guess the date and time for tokens that "appear" to be valid
			// We do not second guess the user's input. If an entire word is not parsed by natty, we reject it as a DateTime element
			while (!isParsedValid(stringParsed, remainingValid)) {
				// need retrieve the actual correct stringParsed variable
				String misparsedToken = findFullToken(stringParsed, remainingValid);
				remainingValid = getRemainingTokens(misparsedToken, remainingValid);
				String remainingString = tokenListToStr(remainingValid);
				groupList = natty.parse(remainingString);
				if (groupList.size() < 1) {
					// no actual correct DateTime token is found
					LOGGER.info("No valid DateTime token is found");
					return tokenList;
				}
				dateGroup = groupList.get(0);				
				stringParsed = groupList.get(0).getText();
				dates = groupList.get(0).getDates();

			} 

			// Throw exception when user includes too many groups of dates.
			// e.g ... from 03/04 to 03/05 starting tomorrow
			if (groupList.size() > 1) {
				throw new Exception ("Too many groups of temporal elements provided.");
				// Throw exception when user input a range with too many temporal elements
				// e.g ... from 03/04 to 03/05 to 03/06
			} else if (dates.size() > 3) {
				throw new Exception ("Too many temporal elements in specifed time range");
			} else {
				// Finally, depending on the operation, we process the dates (which should be the correct by now...)
				if (dates.size() == 1) {
					if (commandData.getUserOp() == OperationType.SEARCH) {

						// natty does not support user specifying the year only
						// we have to support simple "search year XXXX" and "search XXXX year"
						if (containsYear(tokenList)) {
							String yearDigits = stringParsed;
							String forcedFormalDate = "1 Jan " + yearDigits;
							groupList = natty.parse(forcedFormalDate);		
							dates = groupList.get(0).getDates();
							commandData.setTime(dates.get(0).toString());
							commandData.setYearSearch();
							// add "year" to list of used tokens
							stringParsed += " " + "year";
						} else {

							// By searching the syntax tree produced by natty parser, we can identity what kind
							// of date or time was explicitly specified by the user
							String syntaxTree = dateGroup.getSyntaxTree().toStringTree();
							if (syntaxTree.contains(YEAR)) {
								commandData.setTime(dates.get(0).toString());
								commandData.setYearSearch();
							}
							if (syntaxTree.contains(MONTH)) {
								commandData.setTime(dates.get(0).toString());
								commandData.setMonthSearch();
							}
							if (syntaxTree.contains(DAY_OF_MONTH)) {
								commandData.setTime(dates.get(0).toString());
								commandData.setDayOfMonthSearch();
							}
							if (syntaxTree.contains(DAY_OF_WEEK)) {
								commandData.setTime(dates.get(0).toString());
								commandData.setDayOfWeekSearch();
							}
							if (syntaxTree.contains(HOURS)) {
								commandData.setTime(dates.get(0).toString());
								commandData.setHourSearch();
							}
						}
					} else {
						commandData.setTime(dates.get(0).toString());
					}
				} else if (dates.size() == 2) {
					commandData.setTime(dates.get(0).toString(), dates.get(1).toString());
				}
				return getRemainingTokens(stringParsed, tokenList);
			}
		}
	}

	/**
	 * Return false if natty parser parsed a word partially.
	 * E.g "CS3230" is parsed into "230"
	 * @param stringParsed
	 * @param tokenList
	 * @return
	 */
	protected boolean isParsedValid(String stringParsed, String[] tokenList) {
		String[] parsedArray = stringParsed.split("\\s+");
		boolean found = false;
		for (int i = 0; i < parsedArray.length; i++) {
			for (int j = 0; j < tokenList.length; j++) {
				found = false;
				if (parsedArray[i].equals(tokenList[j])) {
					found = true;
					break;
				}
			}
			if (found) {
				continue;
			} else {
				return false;
			}
		}
		return found;
	}

	/**
	 * Return the full word of a partial word. This is a helper function used when natty parsed a partial word.
	 * @param partialToken
	 * @param tokenList
	 * @return
	 * @throws Exception
	 */
	protected String findFullToken(String partialTokens, String[] tokenList) throws Exception {
		String[] partialArray = partialTokens.trim().split("\\s+");
		for (int i = 0; i < tokenList.length; i++) {
			for (int j = 0; j < partialArray.length; j++) {
				String currentToken = tokenList[i];
				String currentPartial = partialArray[j];
				if (currentToken.equals(currentPartial)) {
					continue;
				} else if (currentToken.contains(currentPartial)) {
					return currentToken;
				}

			}
		}
		LOGGER.warn("findFullToken failed on {} and {}", partialTokens, tokenList);
		throw new Exception("Partial token given is not found in the provided token list");
	}

	/**
	 * getRemainingTokens is modified to ignore tokens enclosed in double quotation marks
	 * 
	 * @param tokenList
	 * @return tokenList without elements usedTokens, ignoring tokens between double quotation marks
	 */
	@Override
	protected String[] getRemainingTokens(String usedTokens, String[] tokenList){
		assert(usedTokens != null);

		if (usedTokens.equals("")) {
			return tokenList;
		}

		boolean ignoreToken = false;
		String[] usedTokenList = strToTokenList(usedTokens);
		assert(usedTokenList.length <= tokenList.length);
		String[] newTokenList = new String[tokenList.length - usedTokenList.length];

		// Replacing tokens in temp of TokenList with null if they are used
		// TokenList is cloned just in case tokenList is to be reused after calling getRemainingTokens method
		String[] tempTokenList = tokenList.clone();
		for (int i = 0; i < tempTokenList.length; i++) {
			String currentUserToken = tempTokenList[i];
			// case where single word within double quotes 
			if (currentUserToken.charAt(0) == DOUBLE_QUOTE && currentUserToken.charAt(currentUserToken.length() - 1) == DOUBLE_QUOTE) {
				continue;
			}
			// case where single word within double quotes
			if (currentUserToken.charAt(0) == DOUBLE_QUOTE || currentUserToken.charAt(currentUserToken.length() - 1) == DOUBLE_QUOTE) {
				ignoreToken = !ignoreToken;
			}
			if (!ignoreToken) {
				for (int j = 0; j < usedTokenList.length; j++) {
					if (usedTokenList[j].equals(tempTokenList[i])) {
						tempTokenList[i] = null;
					}
				}
			}
		}
		// Populating newTokenList with remaining unused tokens
		if (newTokenList.length == 0) {
			return newTokenList;
		} else {
			int count = 0;
			for (int i = 0; i < tempTokenList.length; i++) {
				if (tempTokenList[i] != null) {
					newTokenList[count] = tempTokenList[i];
					count++;
				}
			}
			assert(!containsNull(newTokenList));
			return newTokenList;
		}
	}

	/**
	 * A helper function for search operation. Searches for a "year" (ignore case) token and 
	 * change that token to lower case.
	 * @param tokenList
	 * @return
	 */
	private boolean containsYear(String[] tokenList) {
		final String year = "year";
		for (int i = 0; i < tokenList.length; i++) {
			String string = tokenList[i];
			if (string.equalsIgnoreCase(year)) {
				tokenList[i] = year;
				return true;
			}			
		}
		return false;
	}

	//	//List of functions available from natty library
	//	public static void main(String[] args) {
	//		Parser parser = new Parser();
	//		List<DateGroup> groups = parser.parse("do homework THURSDAY");
	//		for(DateGroup group:groups) {
	//			List<Date> dates = group.getDates();
	//
	//			int line = group.getLine();
	//			int column = group.getPosition();
	//			String matchingValue = group.getText();
	//
	//			String syntaxTree = group.getSyntaxTree().toStringTree();
	//			System.out.println(syntaxTree);
	//			//Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
	//			boolean isRecurreing = group.isRecurring();
	//			Date recursUntil = group.getRecursUntil();
	//
	//			System.out.println(dates.toString());
	//			System.out.println(matchingValue);
	//		}
	//	}

}


