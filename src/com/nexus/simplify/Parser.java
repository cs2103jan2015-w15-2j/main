package com.nexus.simplify;

import java.util.HashMap;


public class Parser implements IParser {

	/*
	 * Need identify Keywords in enum or hash, etc
	 * http://stackoverflow.com/questions/717588/writing-a-cleaner-and-more-modular-command-parser
	 * 
	 */

	// String Messages

	// List of all possible tokens?
	//	enum Token {
	//		COMMAND, NAME, DUEDATE, WORKLOAD, INVALID_PARAMETER
	//	}

	enum OperationType {
		ADD, DELETE, MODIFY, ARCHIVE, CLEAR, INVALID
	};

	HashMap<String, OperationType> cmdHash = new HashMap<String, OperationType>();

	/**
	 * Initialise command string hashtable by 
	 * adding all recognised command strings as keys to their operation type
	 */
	private void initCommandHash() {
		cmdHash.put("add", OperationType.ADD);
		cmdHash.put("clear", OperationType.CLEAR);
		cmdHash.put("done", OperationType.ARCHIVE);
		cmdHash.put("modify", OperationType.MODIFY);
		cmdHash.put("update", OperationType.ADD);
		cmdHash.put("delete", OperationType.DELETE);
	}



	/**
	 * Lexical analyser which takes in a string and produces a list of tokens
	 * @param userInput
	 * @return 
	 */
	private String[] tokeniser(String userInput){
		initCommandHash();
		String[] stringArray = userInput.split("\\s", 2);
		//		Token[] tokenList = new Token[stringArray.length];

		return stringArray;
	}

	private Command parseTokens(String[] tokenList){
		String[] parameterArray = getParameterArray(tokenList);
		switch (cmdHash.get(tokenList[0])) {

		case ADD : 
			return new Command(OperationType.ADD, parameterArray);

		case DELETE :
			return new Command(OperationType.DELETE, parameterArray);

		case MODIFY :
			return new Command(OperationType.MODIFY, parameterArray);

		case ARCHIVE :
			return new Command(OperationType.ARCHIVE, parameterArray);

		case CLEAR :
			return new Command(OperationType.CLEAR, parameterArray);

		default : 
			return new Command(OperationType.INVALID, new String[1]);
		}

	}

	private String[] getParameterArray(String[] tokenList) {
		// Check for user input where there are no parameters, only operation
		if (tokenList.length == 1) {
			return new String[1];
		} else {
			String[] parameters = new String[tokenList.length -1];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = tokenList[i+1];
			}
			return parameters;
		}

	}

	public String parseInput(String userInput) {
		String[] userTokens = tokeniser(userInput);
		Command userCommand = parseTokens(userTokens);		
		return Logic.executeCommand(userCommand);
	}

}
