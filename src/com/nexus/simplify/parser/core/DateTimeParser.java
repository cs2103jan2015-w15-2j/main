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
 * 
 * This DateTime parser relies heavily on Natty library @
 * http://natty.joestelmach.com}
 *
 */
public class DateTimeParser extends TokenParser {

	private static final int MIN_NUMBER_OF_DATEGROUP = 0;
	private static final int MAX_NUMBER_OF_DATEGROUP = 1;
	private static final int MAX_NUMBER_OF_DATETIME = 2;
	private static final int SIZE_OF_SINGLE_DATETIME = 1;
	private static final int SIZE_OF_RANGED_DATETIME = 2;

	private static final String ERROR_TOO_MANY_TEMPORAL_ELEMENTS = "Too many groups of temporal elements provided.";
	private static final String ERROR_TOO_MANY_TEMPORAL_ELEMENTS_TIMERANGE = "Too many temporal elements in specifed time range";
	private static final String ERROR_PARTIAL_TOKEN_NOT_FOUND = "Partial token given is not found in the provided token list";
	private static final String LOG_NATTY_STRING_PARSED = "String parsed by natty: {}";

	private static final String REGEX_ONE_OR_MORE_WHITESPACE = "\\s+";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateTimeParser.class.getName());
	private static final CommandData _commandData = CommandData.getInstance();

	// Key strings to search for in user input parse tree
	// Used to identify what aspect of time the user is searching for
	private final String YEAR = "YEAR_OF";
	private final String MONTH = "MONTH_OF_YEAR";
	private final String DAY_OF_MONTH = "DAY_OF_MONTH";
	private final String DAY_OF_WEEK = "DAY_OF_WEEK";
	private final String HOURS = "HOURS_OF_DAY";

	private Parser natty = new Parser();

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String[] remainingValid = tokenList.clone();

			while (isEnclosedInDoubleQuotes(remainingValid)) {
				remainingValid = trimTokensInQuotes(remainingValid);
			}
			// Convert remaingValid tokens to string to use natty library.
			String tokenString = tokenListToStr(remainingValid);
			List<DateGroup> groupList = natty.parse(tokenString);

			// Do not proceed with parsing if no DateTime is parsed from
			// tokenString
			if (groupList.size() < 1) {
				// Natty has cornercase bug where it does not detect DateTime
				// elements that
				// follows a word containing timezone letters and IN token
				// for example, add testing 1600, where EST is a timezone and IN
				// is a valid token
				// other examples are psting 1800, cstin
				String invalidTokens = "";
				boolean retrySuccess = false;

				for (int i = 0; i < remainingValid.length; i++) {
					String string = remainingValid[i];
					groupList = natty.parse(string);
					if (groupList.size() > MIN_NUMBER_OF_DATEGROUP) {
						retrySuccess = true;
						invalidTokens = invalidTokens.trim();
						remainingValid = getRemainingTokens(invalidTokens,
								remainingValid);
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
			LOGGER.info(LOG_NATTY_STRING_PARSED, stringParsed);

			// Ensure that we proceed setting time elements for fully parsed
			// words.
			// This is because Natty will try to guess the date and time for
			// tokens that "appear" to be valid
			// We do not second guess the user's input. If an entire word is not
			// parsed by natty, we reject it as a DateTime element
			while (!isParsedValid(stringParsed, remainingValid)) {
				// need retrieve the actual correct stringParsed variable
				String misparsedToken = findFullToken(stringParsed,
						remainingValid);
				remainingValid = getRemainingTokens(misparsedToken,
						remainingValid);
				String remainingString = tokenListToStr(remainingValid);
				groupList = natty.parse(remainingString);
				if (groupList.size() < 1) {
					// no actual correct DateTime token is found
					LOGGER.info("No valid DateTime token is found");
					return tokenList;
				}
				dateGroup = groupList.get(0);
				stringParsed = dateGroup.getText();
				dates = dateGroup.getDates();

			}

			// Proceed with parsing of DateTime...

			// Throw exception when user includes too many groups of dates.
			// e.g ... from 03/04 to 03/05 starting tomorrow
			if (groupList.size() > MAX_NUMBER_OF_DATEGROUP) {
				throw new Exception(ERROR_TOO_MANY_TEMPORAL_ELEMENTS);
				// Throw exception when user input a range with too many
				// temporal elements
				// e.g ... from 03/04 to 03/05 to 03/06
			} else if (dates.size() > MAX_NUMBER_OF_DATETIME) {
				throw new Exception(ERROR_TOO_MANY_TEMPORAL_ELEMENTS_TIMERANGE);
			} else {
				// Finally, depending on the operation, we process the dates
				// (which should be the correct by now...)
				if (dates.size() == SIZE_OF_SINGLE_DATETIME) {
					// DateTime parsing for search
					if (_commandData.getUserOp() == OperationType.SEARCH) {
						// natty does not support user specifying the year only
						// we have to support simple "search year XXXX" and
						// "search XXXX year"
						if (containsYear(tokenList)) {
							doSearchYear(stringParsed);
							// add "year" to list of used tokens
							stringParsed += " " + "year";
						} else {
							doSearchDateTime(dateGroup);
						}
					} else {
						// Parsing single non-search DateTime.
						_commandData.setTime(dates.get(0).toString());
					}
				} else if (dates.size() == SIZE_OF_RANGED_DATETIME) {
					// Parsing single non-search DateTime.
					_commandData.setTime(dates.get(0).toString(), dates.get(1)
							.toString());
				}
				return getRemainingTokens(stringParsed, tokenList);
			}
		}
	}

	/**
	 * Processes the search for year values.
	 * 
	 * @param stringParsed
	 */
	private void doSearchYear(String stringParsed) {
		List<DateGroup> groupList;
		List<Date> dates;
		String yearDigits = stringParsed;
		// "1 Jan" is prefixed so that natty can parse
		// yearDigits as year value
		String forcedFormalDate = "1 Jan " + yearDigits;
		groupList = natty.parse(forcedFormalDate);
		dates = groupList.get(0).getDates();
		_commandData.setTime(dates.get(0).toString());
		_commandData.setYearSearch();
	}

	/**
	 * Processses the search for temporal search terms that appears in DateGroup
	 * syntax tree.
	 * 
	 * @param dateGroup
	 *            DateGroup of Temporal search terms.
	 * @param dates
	 */
	private void doSearchDateTime(DateGroup dateGroup) {
		// By searching the syntax tree produced by natty
		// parser, we can identity what kind
		// of date or time was explicitly specified by the
		// user
		String syntaxTree = dateGroup.getSyntaxTree().toStringTree();
		List<Date> dates = dateGroup.getDates();

		if (syntaxTree.contains(YEAR)) {
			_commandData.setTime(dates.get(0).toString());
			_commandData.setYearSearch();
		}

		if (syntaxTree.contains(MONTH)) {
			_commandData.setTime(dates.get(0).toString());
			_commandData.setMonthSearch();
		}

		if (syntaxTree.contains(DAY_OF_MONTH)) {
			_commandData.setTime(dates.get(0).toString());
			_commandData.setDayOfMonthSearch();
		}

		if (syntaxTree.contains(DAY_OF_WEEK)) {
			_commandData.setTime(dates.get(0).toString());
			_commandData.setDayOfWeekSearch();
		}

		if (syntaxTree.contains(HOURS)) {
			_commandData.setTime(dates.get(0).toString());
			_commandData.setHourSearch();
		}
	}

	/**
	 * Return false if natty parser parsed a word partially. E.g "CS3230" is
	 * parsed into "230"
	 * 
	 * @param stringParsed
	 * @param tokenList
	 * @return
	 */
	protected boolean isParsedValid(String stringParsed, String[] tokenList) {
		String[] parsedArray = stringParsed.split(REGEX_ONE_OR_MORE_WHITESPACE);
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
	 * Return the full word of a partial word. This is a helper function used
	 * when natty parsed a partial word.
	 * 
	 * @param partialToken
	 *            A substring of a token from tokenList parsed by natty.
	 * @param tokenList
	 *            List of tokens containing partialToken.
	 * @return Full token containing partialToken
	 * @throws Exception
	 *             Exception occurs when partialTokens is not found in
	 *             tokenList.
	 */
	protected String findFullToken(String partialTokens, String[] tokenList)
			throws Exception {
		String[] partialArray = partialTokens.trim().split(
				REGEX_ONE_OR_MORE_WHITESPACE);

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

		LOGGER.warn("findFullToken failed on {} and {}", partialTokens,
				tokenList);

		throw new Exception(ERROR_PARTIAL_TOKEN_NOT_FOUND);
	}

	/**
	 * getRemainingTokens method for DateTimeParser is modified to ignore tokens
	 * enclosed in double quotation marks.
	 * 
	 * @param tokenList
	 *            List of tokens.
	 * @return tokenList with tokens in usedTokens removed, ignoring tokens
	 *         between double quotation marks.
	 */
	@Override
	protected String[] getRemainingTokens(String usedTokens, String[] tokenList) {
		// Specified token list cannot be a null object, else there is nothing
		// to remove tokens from.
		assert (usedTokens != null);

		if (usedTokens.equals("")) {
			return tokenList;
		}

		String[] usedTokenList = strToTokenList(usedTokens);
		// You cannot use up more tokens than it is provided.
		assert (usedTokenList.length <= tokenList.length);

		String[] newTokenList = new String[tokenList.length
				- usedTokenList.length];
		String[] tempTokenList = replaceTokensWithNullIgnoreQuotes(tokenList,
				usedTokenList);
		String[] remainingTokens = removeNullTokens(newTokenList, tempTokenList);
		return remainingTokens;
	}

	/**
	 * Clones the specified token list and replacing tokens found in used token
	 * list with {@code null}. Ignores tokens enclosed in double quotes.
	 * 
	 * @param tokenList
	 * @param usedTokenList
	 * @return
	 */
	private String[] replaceTokensWithNullIgnoreQuotes(String[] tokenList,
			String[] usedTokenList) {
		String[] tempTokenList = tokenList.clone();
		boolean ignoreToken = false;

		for (int i = 0; i < tempTokenList.length; i++) {
			String currentUserToken = tempTokenList[i];

			// case where single word within double quotes
			if (currentUserToken.charAt(0) == DOUBLE_QUOTE
					&& currentUserToken.charAt(currentUserToken.length() - 1) == DOUBLE_QUOTE) {
				continue;
			}

			// case where single word within double quotes
			if (currentUserToken.charAt(0) == DOUBLE_QUOTE
					|| currentUserToken.charAt(currentUserToken.length() - 1) == DOUBLE_QUOTE) {
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
		return tempTokenList;
	}

	/**
	 * A helper function for search operation. Searches for a "year" (ignore
	 * case) token and change that token to lower case.
	 * 
	 * @param tokenList
	 *            List of tokens.
	 * @return {@code true} if tokenList contains "year" (ignore case).
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
}
