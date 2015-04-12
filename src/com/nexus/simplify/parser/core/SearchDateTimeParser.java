package com.nexus.simplify.parser.core;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.nexus.simplify.parser.data.CommandData;

public class SearchDateTimeParser extends DateTimeParser {

	// Search string for user input parse tree
	final String YEAR = "YEAR_OF";
	final String MONTH = "MONTH_OF_YEAR";
	final String DAY_OF_MONTH = "DAY_OF_MONTH";
	final String DAY_OF_WEEK = "DAY_OF_WEEK";
	final String HOURS = "HOURS_OF_DAY";
	final String MINUTES = "MINUTES_OF_HOUR";
	CommandData commandData = CommandData.getInstance();

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			// Convert tokenList to string to use natty library.
			String tokenString = tokenListToStr(tokenList);
			List<DateGroup> groupList = natty.parse(tokenString);

			// Do not proceed with parsing if no DateTime is parsed from tokenString
			if (groupList.size() <1) {
				return tokenList;
			} else {
				// Gets a list of all dates in the only group of dates parsed.
				DateGroup dateGroup = groupList.get(0);
				List<Date> dates = groupList.get(0).getDates();

				// Throw exception when user includes too many groups of dates.
				// e.g ... from 03/04 to 03/05 starting tomorrow
				if (groupList.size() > 1) {
					throw new Exception ("Too many groups of temporal elements provided.");
					// Throw exception when user input a range with too many temporal elements
					// e.g ... from 03/04 to 03/05 to 03/06
				} else if (dates.size() > 2) {
					throw new Exception ("Please specify only one temporal element for search");
				} else {
					String stringParsed = dateGroup.getText();
					LOGGER.info("String parsed: {}", stringParsed);
					
					// Ensure that we proceed setting time elements for fully parsed words.
					// This is because Natty will try to guess the date and time for tokens that "appear" to be valid
					// We do not second guess the user's input. If an entire word is not parsed by natty, we reject it as a DateTime element
					if (isParsedValid(stringParsed, tokenList)) {
						if (dates.size() == 1) {
							commandData.setTime(dates.get(0).toString());
					
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
							throw new Exception("Please only specify one Date & Time for search");
						}
						return getRemainingTokens(stringParsed, tokenList);
					} else {
						LOGGER.info("String parsed is considered invalid");
						return tokenList;
					}
				}
			}
		}
	}
}
