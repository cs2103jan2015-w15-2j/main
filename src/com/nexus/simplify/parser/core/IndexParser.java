package com.nexus.simplify.parser.parser;

import com.nexus.simplify.parser.data.CommandData;

public class IndexParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();

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
