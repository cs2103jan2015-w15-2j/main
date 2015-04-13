//@author A0111035A

package com.nexus.simplify.parser.core;

/**
 * An abstract class to generalise the role of token parsers in Parser
 * component. TokenParser contains many useful methods that parsers might need
 * while they parse for specific type of tokens
 * 
 * @author Davis
 *
 */
public abstract class TokenParser {
	// Char value of double quote ("), used for detection in tokens
	protected final char DOUBLE_QUOTE = 34;

	private final int ARRAY_EMPTY_SIZE = 0;
	private final String EMPTY_TOKEN = "";

	/**
	 * Takes a list of tokens and process the tokens that it is able to parse.
	 * 
	 * @param tokenList
	 *            List of tokens for different parsers to identify its
	 *            respective tokens.
	 * @return List of remaining tokens that were not used.
	 * @throws Exception
	 *             When parsing of specified token list fails.
	 */
	public abstract String[] parseTokens(String[] tokenList) throws Exception;

	protected String[] getRemainingTokens(String usedTokens, String[] tokenList) {
		// Specified token list cannot be a null object, else there is nothing
		// to remove tokens from.
		assert (usedTokens != null);

		if (usedTokens.equals(EMPTY_TOKEN)) {
			return tokenList;
		}

		String[] usedTokenList = strToTokenList(usedTokens);
		// You cannot use up more tokens than it is provided.
		assert (usedTokenList.length <= tokenList.length);

		String[] newTokenList = new String[tokenList.length
				- usedTokenList.length];
		String[] tempTokenList = replaceTokensWithNull(tokenList, usedTokenList);
		String[] remainingTokens = removeNullTokens(newTokenList, tempTokenList);
		return remainingTokens;
	}

	/**
	 * Return {@code true} if the specified array contains {@code null} element.
	 * 
	 * @param tokenList
	 *            Token list to be checked for {@code null} element.
	 * @return {@code true} if token list contains null element, {@code false}
	 *         if otherwise.
	 */
	protected boolean hasNull(String[] tokenList) {
		for (int i = 0; i < tokenList.length; i++) {
			String string = tokenList[i];
			if (string == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Transfer non-null elements from oldTokenList to newTokenList. Length of
	 * newTokenList specified must be exactly the same with number of non-null
	 * elements
	 * 
	 * @param newTokenList
	 *            New empty token that has the length of non {@code null}
	 *            elements in oldTokenList.
	 * @param oldTokenList
	 *            List of tokens with {@code null} elements.
	 * @return New token list that is fully filled with tokens.
	 */
	protected String[] removeNullTokens(String[] newTokenList,
			String[] oldTokenList) {
		// Populate newTokenList with remaining unused tokens.
		if (newTokenList.length == ARRAY_EMPTY_SIZE) {
			return newTokenList;
		} else {
			int count = 0;

			for (int i = 0; i < oldTokenList.length; i++) {
				if (oldTokenList[i] != null) {
					newTokenList[count] = oldTokenList[i];
					count++;
				}
			}

			// New list should be fully filled. Presence of null suggests that
			// more than one remaining token was not brought over
			assert (!hasNull(newTokenList));
			return newTokenList;
		}
	}

	/**
	 * Clones the specified token list and replacing tokens found in used token
	 * list with {@code null}.
	 * 
	 * @param tokenList
	 *            List of tokens to remove tokens from.
	 * @param usedTokenList
	 *            List of tokens to be removed.
	 * @return tokenList with used tokens removed.
	 */
	protected String[] replaceTokensWithNull(String[] tokenList,
			String[] usedTokenList) {
		String[] tempTokenList = tokenList.clone();

		for (int i = 0; i < tempTokenList.length; i++) {
			for (int j = 0; j < usedTokenList.length; j++) {
				if (usedTokenList[j].equals(tempTokenList[i])) {
					tempTokenList[i] = null;
				}
			}
		}
		return tempTokenList;
	}

	/**
	 * Returns {@code true} if specified tokenList contains at least one token
	 * enclosed in double quotations.
	 * 
	 * @param tokenList
	 *            List of tokens
	 * @return {@code true} if tokenList contains at least one token enclosed in
	 *         double quotations, otherwise {@code false}.
	 */
	protected boolean isEnclosedInDoubleQuotes(String[] tokenList) {
		boolean first = false;
		boolean second = false;
		String[] temp = tokenList.clone();

		// Search for a pair of quotation marks in the whole array
		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			final int startOfString = 0;
			final int endOfString = string.length() - 1;

			if (string.charAt(startOfString) == DOUBLE_QUOTE) {
				first = true;
			}

			if (first && string.charAt(endOfString) == DOUBLE_QUOTE) {
				second = true;
			}
		}
		return first && second;
	}

	/**
	 * Removes words enclosed in a pair of double quotation marks from a token
	 * array.
	 * 
	 * @param tokenList
	 *            List of tokens to be trimmed.
	 * @return tokenList without words enclosed in a pair of double quotation
	 *         marks.
	 */
	protected String[] trimTokensInQuotes(String[] tokenList) {
		String[] temp = tokenList.clone();
		int start = -1;
		int end = -1;

		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			final int startOfString = 0;
			final int endOfString = string.length() - 1;

			if (string.charAt(startOfString) == DOUBLE_QUOTE) {
				start = i;
			}

			if (start >= 0 && string.charAt(endOfString) == DOUBLE_QUOTE) {
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

	/**
	 * Trims double quotation marks that encloses at least one tokens.
	 * 
	 * @param tokenList
	 *            List of tokens.
	 * @return List of tokens where no tokens are enclosed in double quotes
	 */
	protected String[] trimQuotesInTokens(String[] tokenList) {
		String[] temp = tokenList.clone();

		for (int i = 0; i < temp.length; i++) {
			String string = temp[i];
			final int startOfString = 0;
			final int endOfString = string.length() - 1;
			final int startOfTrimmed = 1;

			if (string.charAt(startOfString) == DOUBLE_QUOTE
					&& string.charAt(endOfString) == DOUBLE_QUOTE) {
				temp[i] = string.substring(startOfTrimmed, endOfString);
			} else {
				temp[i] = string.replaceFirst("\"", EMPTY_TOKEN);
			}
		}
		return temp;
	}

	/**
	 * Returns {@true} if specified tokenList is an empty array.
	 * 
	 * @param tokenList
	 *            List of tokens.
	 * @return {@true} if specified tokenList is an empty array.
	 */
	protected boolean isTokenListEmpty(String[] tokenList) {
		// Case when all tokens are used up by the parsers
		if (tokenList.length == 0) {
			return true;
			// Case when empty string "" is entered by user
		} else if (tokenList.length == 1 & tokenList[0].equals(EMPTY_TOKEN)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tokenises a tokenString by splitting tokenString into substrings
	 * separated by one or more whitespaces.
	 * 
	 * @param tokenString
	 *            A string containing tokens separated by whitespaces.
	 * @return List of all tokens contained in tokenString.
	 */
	protected String[] strToTokenList(String tokenString) {
		String[] tokenList = tokenString.split("\\s+");
		return tokenList;
	}

	/**
	 * Concatenate tokens in tokenList to one long token string with tokens
	 * seperated by single whitespace.
	 * 
	 * @param tokenList
	 *            List of tokens to be concatenated.
	 * @return A long string containing all tokens in tokenList seperated by
	 *         single whitespace.
	 */
	protected String tokenListToStr(String[] tokenList) {
		StringBuilder builder = new StringBuilder();
		for (String s : tokenList) {
			builder.append(s);
			builder.append(" ");
		}
		return builder.toString().trim();
	}
}
