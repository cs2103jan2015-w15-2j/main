package com.nexus.simplify;

import java.util.HashMap;
import com.nexus.simplify.ParameterType;
import com.nexus.simplify.OperationType;

public class Parser implements IParser {

	/*
	 * Need identify Keywords in enum or hash, etc
	 * http://stackoverflow.com/questions/717588/writing-a-cleaner-and-more-modular-command-parser
	 * 
	 */

	// Logic component to exceute parsed command
	Logic logic = new Logic();
	
	//Parameter constants
	
	HashMap<String, OperationType> cmdHash = new HashMap<String, OperationType>();

	/**
	 * Initialise command string hashtable by 
	 * adding all recognised command strings as keys to their operation type
	 */
	private void initCommandHash() {
		// adding support for all supported commands 
		cmdHash.put("display", OperationType.DISPLAY);
		cmdHash.put("add", OperationType.ADD);
		cmdHash.put("modify", OperationType.MODIFY);
		cmdHash.put("update", OperationType.MODIFY);
		cmdHash.put("clear", OperationType.CLEAR);
		cmdHash.put("done", OperationType.ARCHIVE);
		cmdHash.put("delete", OperationType.DELETE);
		cmdHash.put("filelocation", OperationType.FILELOCATION);
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
		String opString = tokenList[0];
		String[] rawParam = getParameterArray(tokenList);
		String[] parameterArray = parseParam(rawParam);
		try {
			switch (cmdHash.get(opString)) {

			case DISPLAY : 
				return new Command(OperationType.DISPLAY, parameterArray);
				
			case ADD : 
				return new Command(OperationType.ADD, parameterArray);

			case MODIFY :
				return new Command(OperationType.MODIFY, parameterArray);

			case CLEAR :
				return new Command(OperationType.CLEAR, parameterArray);
			
			case ARCHIVE :
				return new Command(OperationType.ARCHIVE, parameterArray);

			case DELETE :
				return new Command(OperationType.DELETE, parameterArray);

			default : 
				return new Command(OperationType.INVALID, new String[]{"switch failure"});
			}
		} catch (Exception e) {
			return new Command(OperationType.INVALID, new String[]{tokenList[0]});
		}

	}

	private String[] getParameterArray(String[] tokenList) {
		// Return null string if there are no parameters provided
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
	
	private String[] parseParam (String[] rawParam) {
		String [] param = new String[ParameterType.MAX_SIZE];
		return rawParam;
		
	}
	
	public CommandResult parseInput(String userInput) {
		String[] userTokens = tokeniser(userInput);
		Command userCommand = parseTokens(userTokens);	
		return logic.executeCommand(userCommand);
	}

}
