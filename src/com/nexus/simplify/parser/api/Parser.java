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
 * 	
 * 
 * Coding Style: 
 * https://docs.google.com/a/nuscomputing.com/document/pub?id=1iAESIXM0zSxEa5OY7dFURam_SgLiSMhPQtU0drQagrs&amp
 * 
 * @author davis
 * 
 */

public class Parser implements IParser {
	Logger LOGGER = LoggerFactory.getLogger(CoreParser.class.getName());
	String givenInput;
	Tokeniser tokeniser = new Tokeniser();
	CoreParser parser = new CoreParser();
	CommandData commandData = CommandData.getInstance();
	String[] userTokens;

	@Override
	public UserCommand parseInput(String userInput) throws Exception {
		try {
			givenInput = userInput;
			LOGGER.info("Parsing user input: {}", userInput);
			userTokens = tokeniser.tokenise(userInput);
			parser.parseTokens(userTokens);	
			UserCommand userCommand = commandData.createCommand();
			return userCommand;
		} catch(Exception e) {
			throw new Exception("Error parsing command: " + userInput
					+ System.lineSeparator() + "Please specify parameters as clear as possible");
		}
	}
	
	public String getGivenInput() {
		return givenInput;
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
