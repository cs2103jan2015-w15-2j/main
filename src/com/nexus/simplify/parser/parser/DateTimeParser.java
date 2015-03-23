package com.nexus.simplify.parser.parser;

import java.util.Date;
import java.util.List;

import com.nexus.simplify.parser.data.CommandData;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateTimeParser extends TokenParser {
	Parser natty = new Parser();
	CommandData commandData = CommandData.getInstance();

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			// Convert tokenList to string to use natty library.
			String tokenString = tokenListToStr(tokenList);
			List<DateGroup> groupList = natty.parse(tokenString);
			// Gets a list of all dates in the only group of dates parsed.
			DateGroup dateGroup = groupList.get(0);
			List<Date> dates = groupList.get(0).getDates();

			// Throw exception when user includes too many groups of dates.
			// e.g ... from 03/04 to 03/05 starting tomorrow
			if (groupList.size() > 1) {
				throw new Exception ("Too many groups of temporal elements provided.");
				// Throw exception when user input a range with too many temporal elements
				// e.g ... from 03/04 to 03/05 to 03/06
			} else if (dates.size() > 3) {
				throw new Exception ("Too many temporal elements in specifed time range");
			} else {
				String stringParsed = dateGroup.getText();
				if (dates.size() == 1) {
					commandData.setTime(dates.get(0).toString());
				} else if (dates.size() == 2) {
					commandData.setTime(dates.get(0).toString(), dates.get(1).toString());
				}
				return getRemainingTokens(stringParsed, tokenList);
			}
		}
	}
}

		//	List of functions available from natty library
		//		Parser parser = new Parser();
		//		List<DateGroup> groups = parser.parse("from 03/04 to 03/05 to 03/06");
		//		for(DateGroup group:groups) {
		//			List<Date> dates = group.getDates();
		//
		//			int line = group.getLine();
		//			int column = group.getPosition();
		//			String matchingValue = group.getText();
		//
		//			String syntaxTree = group.getSyntaxTree().toStringTree();
		//			Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
		//			boolean isRecurreing = group.isRecurring();
		//			Date recursUntil = group.getRecursUntil();
		//
		//			System.out.println(dates.toString());
		//			System.out.println(matchingValue);
		//		}
		//	}


