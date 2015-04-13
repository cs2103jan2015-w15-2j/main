//@author A0111035A

package com.nexus.simplify.parser.core;

import com.nexus.simplify.parser.data.CommandData;

/**
 * Parsing of name works by parsing the remaining tokens as new task name after
 * all other parameters had been parsed.
 * 
 * @author Davis
 *
 */
public class NameParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();

	/**
	 * Parses remaining tokens in tokenList as name tokens.
	 */
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			if (isEnclosedInDoubleQuotes(tokenList)) {
				tokenList = trimQuotesInTokens(tokenList);
			}
			String name = tokenListToStr(tokenList);
			commandData.setNewName(name);
			return getRemainingTokens(name, tokenList);
		}
	}
}
