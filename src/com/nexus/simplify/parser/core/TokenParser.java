package com.nexus.simplify.parser.core;

public abstract class TokenParser {

	// Char value of double quote ("), used for detection in tokens
	final char DOUBLE_QUOTE = 34;

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

		if (usedTokens.equals("")) {
			return tokenList;
		}

		String[] usedTokenList = strToTokenList(usedTokens);

		assert(usedTokenList.length <= tokenList.length);
		String[] newTokenList = new String[tokenList.length - usedTokenList.length];

		// Replacing tokens in temp of TokenList with null if they are used
		// TokenList is cloned just in case tokenList is to be reused after calling getRemainingTokens method
		String[] tempTokenList = tokenList.clone();
		for (int i = 0; i < tempTokenList.length; i++) {
			for (int j = 0; j < usedTokenList.length; j++) {
				if (usedTokenList[j].equals(tempTokenList[i])) {
					tempTokenList[i] = null;
				}
			}
		}
		// Populating newTokenList with remaining unused tokens
		if (newTokenList.length == 0) {
			return newTokenList;
		} else {
			int count = 0;
			for (int i = 0; i < tempTokenList.length; i++) {
				if (tempTokenList[i] != null) {
					newTokenList[count] = tempTokenList[i];
					count++;
				}
			}
			assert(!containsNull(newTokenList));
			return newTokenList;
		}
	}

	protected boolean containsNull(String[] newTokenList) {
		for (int i = 0; i < newTokenList.length; i++) {
			String string = newTokenList[i];
			if (string == null) {
				return true;
			}
		}
		return false;
	}

	protected boolean enclosedInDoubleQuotes(String[] tokenList) {
		final char DOUBLE_QUOTE = 34;
		boolean first = false;
		boolean second = false;
		String[] temp = tokenList.clone();
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			if (string.charAt(0) == DOUBLE_QUOTE) {
				first = true;
			}
			if (first && string.charAt(string.length()-1) == DOUBLE_QUOTE) {
				second = true;
			}
		}
		return first&&second;
	}

	/**
	 * Removes words enclosed in a pair of double quotation marks from a token array
	 *  
	 * @param  tokenList
	 * @return tokenList without words enclosed in a pair of double quotation marks
	 */
	protected String[] trimTokensInQuotes(String[] tokenList) {
		String[] temp = tokenList.clone();
		int start = -1;
		int end = -1;
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			if (string.charAt(0) == DOUBLE_QUOTE) {
				start = i;
			}
			if (start >= 0 && string.charAt(string.length()-1) == DOUBLE_QUOTE) {
				end = i;
			}
		}
		int lengthOfTrimmed = (end - start + 1);
		String[] remaining = new String[temp.length - lengthOfTrimmed];
		// keep partition that is left of trimmed
		for (int i = 0; i < start; i++) {
			remaining[i] = temp[i];
		}
		// keep partition that is right of trimmed
		for (int i = (end + 1); i < temp.length; i++) {
			remaining[i - lengthOfTrimmed] = temp[i];
		}
		return remaining;
	}

	protected String[] trimQuotesInTokens(String[] tokenList) {
		String[] temp = tokenList.clone();
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			if (string.charAt(0) == DOUBLE_QUOTE && string.charAt(string.length() - 1) == DOUBLE_QUOTE) {
				temp[i] = string.substring(1, string.length() - 1);
			} else {
				temp[i] = string.replaceFirst("\"", "");		
			}
		}
		return temp;
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
		String[] strArr = str.split("\\s+");
		return strArr;
	}

	protected String tokenListToStr(String[] strArr) {
		StringBuilder builder = new StringBuilder();
		for(String s : strArr) {
			builder.append(s);
			builder.append(" ");
		}
		return builder.toString().trim();
	}
}
