package com.nexus.simplify.parser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainParser extends TokenParser {
	Logger LOGGER = LoggerFactory.getLogger(MainParser.class.getName());
	OperationParser opParser = new OperationParser();
	ParamParser paramParser = new ParamParser();

	final String MESSAGE_UNPARSED_TOKENS = "Unparsed tokens detected:";

	@Override
	public String[] parseTokens(String[] tokenList) {

		try {
			tokenList = opParser.parseTokens(tokenList);
			tokenList = paramParser.parseTokens(tokenList);

			// There should not be remaining unidentified tokens who are not recognised as operation or parameters
			if (!isTokenListEmpty(tokenList)) {
				throw new Exception(appendStringArr(MESSAGE_UNPARSED_TOKENS, tokenList));
			}
		} catch(Exception e) {
			LOGGER.error("Parse Error on: {}", tokenList, e);
		}
		return tokenList;			
	}

	private String appendStringArr(String string, String[] stringArr) {
		String newString = string;
		for (String str : stringArr) {
			string += " " + str;
		}
		return newString;
	}
}
