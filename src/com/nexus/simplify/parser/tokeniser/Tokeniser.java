//@author A0111035A

package com.nexus.simplify.parser.tokeniser;

/**
 * A tokeniser that splits a string into substrings that are seperated by
 * whitespaces.
 *
 */
public class Tokeniser {

	private static final String REGEX_WHITESPACE_ONCE_OR_MORE = "\\s+";

	/**
	 * Lexical analyser which takes in a string and produces a list of tokens. A
	 * token in Simplify is a combination of character sequences seperated by
	 * one whitespace character
	 * 
	 * @param userInput
	 *            Input string from user.
	 * @return An array of string tokens.
	 */
	public String[] tokenise(String userInput) {
		String[] stringArray = userInput.split(REGEX_WHITESPACE_ONCE_OR_MORE);
		return stringArray;
	}
}
