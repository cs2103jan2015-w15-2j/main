package com.nexus.simplify.parser.parser;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.parser.data.CommandData;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateTimeParser extends TokenParser {
	Logger LOGGER = LoggerFactory.getLogger(DateTimeParser.class.getName());
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

			// Do not proceed with parsing if no DateTime is parsed from tokenString
			if (groupList.size() <1) {
				LOGGER.info("No DateTime is parsed.");
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
				} else if (dates.size() > 3) {
					throw new Exception ("Too many temporal elements in specifed time range");
				} else {
					String stringParsed = dateGroup.getText();
					LOGGER.info("String parsed: {}", stringParsed);
					
					// Ensure that we proceed setting time elements for fully parsed words.
					// This is because Natty will try to guess the date and time for tokens that "appear" to be valid
					// We do not second guess the user's input. If an entire word is not parsed by natty, we reject it as a DateTime element
					if (isParsedValid(stringParsed, tokenList)) {
						if (dates.size() == 1) {
							commandData.setTime(dates.get(0).toString());
						} else if (dates.size() == 2) {
							commandData.setTime(dates.get(0).toString(), dates.get(1).toString());
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


