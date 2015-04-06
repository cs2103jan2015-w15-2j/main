package com.nexus.simplify.parser.parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;

public class SearchDateTimeParser extends DateTimeParser {

	// Search string for user input parse tree
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
					commandData.setTime(dates.get(0).toString());
					String syntaxTree = dateGroup.getSyntaxTree().toStringTree();
					if (syntaxTree.contains(YEAR)) {
						System.out.println("YEAR");
					}
					if (syntaxTree.contains(MONTH)) {
						System.out.println("MONTH");
					}
					if (syntaxTree.contains(DAY_OF_MONTH)) {
						System.out.println("DAY_OF_MONTH");
					}
					if (syntaxTree.contains(DAY_OF_WEEK)) {
						System.out.println("DAY_OF_WEEK");
					}
					if (syntaxTree.contains(HOURS)) {
						System.out.println("HOURS");
					}
					if (syntaxTree.contains(MINUTES)) {
						System.out.println("MINUTES");
					}
					return getRemainingTokens(stringParsed, tokenList);
				}
			}
		}
	}
}
