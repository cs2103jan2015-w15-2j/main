package com.nexus.simplify;

import com.nexus.simplify.Command.OperationType;

public class Parser implements IParser {
	
	/*
	 * http://stackoverflow.com/questions/717588/writing-a-cleaner-and-more-modular-command-parser
	 * 
	 */
	
	// Need identify Keywords in enum or hash, etc
	
	// List of all possible tokens
	enum Token {
		COMMAND, NAME, DUEDATE, WORKLOAD
	}
	
	/**
	 * Lexical analyser which takes in a string and produces a list of tokens
	 * @param userInput
	 * @return 
	 */
	private Token[] tokeniser(String userInput){
		return null;
	}
	
	private Command parseTokens(Token[] tokenList){
		return new Command(OperationType.INVALID, null);
	}
	
	public String parseInput(String userInput) {
		Token[] userTokens = tokeniser(userInput);
		Command userCommand = parseTokens(userTokens);		
		return Logic.executeCommand(userCommand);
	}

}
