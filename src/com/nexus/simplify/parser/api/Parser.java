package com.nexus.simplify.parser.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.parser.data.CommandData;
import com.nexus.simplify.parser.parser.MainParser;
import com.nexus.simplify.parser.tokeniser.Tokeniser;

/**
 * 	
 * For V0.1, the input is fixed to be 
 * 
 * <command> "name" <name> "deadline" <deadline> "workload"
 *
 * 
 * Coding Style: 
 * https://docs.google.com/a/nuscomputing.com/document/pub?id=1iAESIXM0zSxEa5OY7dFURam_SgLiSMhPQtU0drQagrs&amp
 * 
 * Need identify Keywords in enum or hash, etc
 * http://stackoverflow.com/questions/717588/writing-a-cleaner-and-more-modular-command-parser
 * 
 */

public class Parser implements IParser {
	Logger LOGGER = LoggerFactory.getLogger(MainParser.class.getName());

	Tokeniser tokeniser = new Tokeniser();
	MainParser parser = new MainParser();
	CommandData commandData = CommandData.getInstance();
	String[] userTokens;

	@Override
	public UserCommand parseInput(String userInput) throws Exception {
		try {
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
