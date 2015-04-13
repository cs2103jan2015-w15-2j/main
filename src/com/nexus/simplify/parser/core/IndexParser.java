//@author A0111035A

package com.nexus.simplify.parser.core;

import com.nexus.simplify.parser.data.CommandData;

public class IndexParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();

	/**
	 * Parses tokenList for index tokens.
	 */
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String index = tokenList[0];
			if (index.matches("[0-9]+")) {
				commandData.setTaskIndex(index);
				return getRemainingTokens(index, tokenList);
			}
			return tokenList;
		}
	}
}
