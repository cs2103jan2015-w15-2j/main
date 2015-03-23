package com.nexus.simplify.parser.api;

import com.nexus.simplify.CommandResult;
import com.nexus.simplify.Logic;

public interface IParser {
	/**
	 * Parses a given input and call on Logic to execute user command
	 * 
	 * @param userInput
	 * @return string feedback for user
	 */
	public CommandResult parseInput(String userInput, Logic logic);
}
