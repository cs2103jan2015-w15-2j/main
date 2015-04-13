//@author A0111035A

package com.nexus.simplify.parser.api;

import com.nexus.simplify.logic.usercommand.UserCommand;

/**
 * Interface class for facade of Parser component.
 * 
 * @author Davis
 *
 */
public interface IParser {
	/**
	 * Returns the the user's command in terms of the operation type and parameters specified.
	 * 
	 * @param userInput		User command string.
	 * @return 				Operation type and parameters specified.
	 * @throws Exception 	When user input fail to parse.
	 */
	public UserCommand parseInput(String userInput) throws Exception;
}
