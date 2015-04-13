//@author A0111035A

package com.nexus.simplify.parser.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.core.CoreParser;
import com.nexus.simplify.parser.data.CommandData;
import com.nexus.simplify.parser.tokeniser.Tokeniser;

/**
 * A facade to facilitate interaction between Parser component with other
 * components.
 * 
 * The coding style was used as a guide for all code in Parser component. It can
 * be found in the link below. Coding Style: {@link https
 * ://docs.google.com/a/nuscomputing.com/document/pub?id=1
 * iAESIXM0zSxEa5OY7dFURam_SgLiSMhPQtU0drQagrs&amp}
 * 
 * @author davis
 * 
 */

public class Parser implements IParser {
	private static final String MESSAGE_PARSE_ERROR = "Error parsing command: %1$s. "
			+ "/n" + "Please specify parameters as clear as possible";

	private static final String LOG_USER_INPUT = "Parsing user input: {}";

	private static Logger LOGGER = LoggerFactory.getLogger(CoreParser.class.getName());
	private static String _input;
	private static Tokeniser _tokeniser = new Tokeniser();
	private static CoreParser _parser = new CoreParser();
	private static CommandData _commandData = CommandData.getInstance();
	private static String[] _userTokens;

	/**
	 * Takes in user string input and returns corresponding requested operation
	 * and given parameters stored in UserCommand
	 */
	@Override
	public UserCommand parseInput(String userInput) throws Exception {
		try {
			_input = userInput;
			LOGGER.info(LOG_USER_INPUT, _input);
			_userTokens = _tokeniser.tokenise(_input);
			_parser.parseTokens(_userTokens);

			// The individual parser classes update the commandData singleton
			// with the operation and parameters
			UserCommand userCommand = _commandData.createCommand();

			return userCommand;
		} catch (Exception e) {
			throw new Exception(String.format(MESSAGE_PARSE_ERROR, _input));
		}
	}

	/**
	 * Returns user input string that was given to Parser object through
	 * {@link parseInput}
	 * 
	 * 
	 * @return User Input String
	 * 
	 */
	public String getGivenInput() {
		return _input;
	}

	// main function to run white-box texting with console input
	public static void main(String[] args) {
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader br = new java.io.BufferedReader(inStream);
		Parser test = new Parser();
		while (true) {
			try {
				test.parseInput(br.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
