package com.nexus.simplify.parser.parser;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.parser.data.CommandData;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

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
				remainingValid = trimInQuotes(remainingValid);
			}
			// Convert remaingValid tokens to string to use natty library.
			String tokenString = tokenListToStr(remainingValid);
			List<DateGroup> groupList = natty.parse(tokenString);

			// Do not proceed with parsing if no DateTime is parsed from tokenString
			if (groupList.size() <1) {
				LOGGER.info("No DateTime is parsed.");
				return tokenList;
			} else {
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
					if (remainingString.equals("")) {
						// no actual correct DateTime token is found
						LOGGER.info("No valid DateTime token is found");
						return tokenList;
					}
					groupList = natty.parse(remainingString);
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
							// By searching the syntax tree produced by natty parser, we can identity what kind
							// of date or time was explicitly specified by the user
							String syntaxTree = dateGroup.getSyntaxTree().toStringTree();
							if (syntaxTree.contains(YEAR)) {
								commandData.setYearSearch();
							}
							if (syntaxTree.contains(MONTH)) {
								commandData.setMonthSearch();
							}
							if (syntaxTree.contains(DAY_OF_MONTH)) {
								commandData.setDayOfMonthSearch();
							}
							if (syntaxTree.contains(DAY_OF_WEEK)) {
								commandData.setDayOfWeekSearch();
							}
							if (syntaxTree.contains(HOURS)) {
								commandData.setHourSearch();
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
	}

	private boolean enclosedInDoubleQuotes(String[] tokenList) {
		final char DOUBLE_QUOTE = 34;
		boolean first = false;
		boolean second = false;
		String[] temp = tokenList.clone();
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			if (string.charAt(0) == DOUBLE_QUOTE) {
				first = true;
			}
			if (first && string.charAt(string.length()-1) == DOUBLE_QUOTE) {
				second = true;
			}
		}
		return first&&second;
	}

	private String[] trimInQuotes(String[] tokenList) {
		final char DOUBLE_QUOTE = 34;
		String[] temp = tokenList.clone();
		int start = -1;
		int end = -1;
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			if (string.charAt(0) == DOUBLE_QUOTE) {
				start = i;
			}
			if (start >= 0 && string.charAt(string.length()-1) == DOUBLE_QUOTE) {
				end = i;
			}
		}
		int lengthOfTrimmed = (end - start + 1);
		String[] remaining = new String[temp.length - lengthOfTrimmed];
		// keep partition that is left of trimmed
		for (int i = 0; i < start; i++) {
			remaining[i] = temp[i];
		}
		// keep partition that is right of trimmed
		for (int i = (end + 1); i < temp.length; i++) {
			remaining[i - lengthOfTrimmed] = temp[i];
		}
		return remaining;
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
	protected String findFullToken(String partialToken, String[] tokenList) throws Exception {
		// partialToken cannot be more than 1 word
		assert(partialToken.trim().split("\\s+").length == 1);
		for (int i = 0; i < tokenList.length; i++) {
			String current = tokenList[i];
			if (current.contains(partialToken)) {
				return current;
			}
		}
		LOGGER.warn("findFullToken failed on {} and {}", partialToken, tokenList);
		throw new Exception("Partial token given is not found in the provided token list");
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


