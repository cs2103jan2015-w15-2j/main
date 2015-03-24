package com.nexus.simplify.parser.parser;

public abstract class TokenParser {

	/**
	 * Takes a list of tokens and process the tokens that it is able to parse. 
	 * 
	 * @param tokenList List of tokens for different parsers to identify its respective tokens
	 * @return          List of remaining tokens
	 * @throws Exception 
	 */
	public abstract String[] parseTokens(String[] tokenList) throws Exception;

	protected String[] getRemainingTokens(String usedTokens, String[] tokenList){
		assert(usedTokens != null);
		String[] usedTokenList = strToTokenList(usedTokens);

		assert(usedTokenList.length <= tokenList.length);
		String[] newTokenList = new String[tokenList.length - usedTokenList.length];

		// Replacing tokens in tokenList with null if they are used
		for (int i = 0; i < tokenList.length; i++) {
			for (int j = 0; j < usedTokenList.length; j++) {
				if (usedTokenList[j].equals(tokenList[i])) {
					tokenList[i] = null;
				}
			}
		}
		// Populating newTokenList with remaining unsed tokens
		if (newTokenList.length == 0) {
			return newTokenList;
		} else {
			int count = 0;
			for (int i = 0; i < tokenList.length; i++) {
				if (tokenList[i] != null) {
					newTokenList[count] = tokenList[i];
					count++;
				}
			}

			return newTokenList;
		}
	}

	protected boolean isTokenListEmpty(String[] tokenList) {
		// Case when all tokens are used up by the parsers
		if (tokenList.length == 0) {
			return true;
		// Case when empty string "" is entered by user
		} else if (tokenList.length == 1 & tokenList[0].equals("")) {
			return true;
		} else {
			return false;
		}
	}

	protected String[] strToTokenList(String str) {
		return str.split("//s");
	}

	protected String tokenListToStr(String[] strArr) {
		StringBuilder builder = new StringBuilder();
		for(String s : strArr) {
			builder.append(s);
		}
		return builder.toString();
	}
}
