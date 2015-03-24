package com.nexus.simplify.parser.api;

import com.nexus.simplify.logic.usercommand.UserCommand;


public interface IParser {
	/**
	 * Parses a given input and call on Logic to execute user command
	 * 
	 * @param userInput
	 * @return string feedback for user
	 */
	public UserCommand parseInput(String userInput);
}
