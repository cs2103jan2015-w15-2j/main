package com.nexus.simplify.parser.parser;

import com.nexus.simplify.parser.data.CommandData;

public class FileLocationParser extends TokenParser {
	String usedTokens;
	CommandData commandData = CommandData.getInstance();
	String id;
	String filePathString;

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			id = tokenList[0]; 
			if (id.equalsIgnoreCase("filelocation") || id.equalsIgnoreCase("file location")) {
				filePathString = tokenList[1];
				commandData.setFileLocation(filePathString);

				usedTokens = tokenList[0] + " " + tokenList[1];
				return getRemainingTokens(usedTokens, tokenList);
			}
			return tokenList;
		}
	}

}
