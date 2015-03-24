package com.nexus.simplify.parser.parser;

import java.util.Arrays;

import com.nexus.simplify.parser.data.CommandData;

/**
 * Parsing of name works by parsing the remaining tokens as new task name after all other parameters had been parsed.
 * 
 * @author Davis
 *
 */
public class NameParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();
	
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String name = tokenListToStr(tokenList);
			commandData.setNewName(name);
			return getRemainingTokens(name, tokenList);
		}
	}
}