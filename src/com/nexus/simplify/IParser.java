package com.nexus.simplify;

public interface IParser {
	/**
	 * Parses a given input and call on Logic to execute user command
	 * 
	 * @param userInput
	 * @return string feedback for user
	 */
	public String parseInput(String userInput);
}
