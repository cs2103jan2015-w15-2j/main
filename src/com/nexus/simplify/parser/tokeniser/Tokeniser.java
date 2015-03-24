package com.nexus.simplify.parser.tokeniser;

public class Tokeniser {
	
	/**
	 * Lexical analyser which takes in a string and produces a list of tokens.
	 * A token in simplify is a combination of character sequences seperated by one whitespace character
	 * 
	 * @param userInput Input string from user.
	 * @return          A string token array.
	 */
	public String[] tokenise(String userInput){
		String[] stringArray = userInput.split("\\s");
		return stringArray;
	}
}
