package com.nexus.simplify.parser.parser;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.UserCommand;
import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.parser.data.CommandData;

public class OperationParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();
	DisplayParser displayParser = new DisplayParser();
	IndexParser indexParser = new IndexParser();
	Logger LOGGER = LoggerFactory.getLogger(OperationParser.class.getName());
	
	@Override
	public String[] parseTokens(String[] tokenList) {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String opString = tokenList[0];
			OperationType userOp = commandData.getOperationType(opString);
			String[] remainingTokens = getRemainingTokens(opString, tokenList);

			// Handle commands that have special first parameters
			try {
				switch (userOp) {
				case DISPLAY : 
					commandData.setOp(userOp);
					return displayParser.parseTokens(remainingTokens);

				case MODIFY :
					commandData.setOp(userOp);
					return indexParser.parseTokens(remainingTokens);

				case DONE :
					commandData.setOp(userOp);
					return indexParser.parseTokens(remainingTokens);

				case DELETE :
					commandData.setOp(userOp);
					return indexParser.parseTokens(remainingTokens);

				default : 
					commandData.setOp(userOp);
					return remainingTokens;
				}
			} catch (Exception e) {
				LOGGER.error("Parse Error on: {}", tokenList, e);
			}
			return remainingTokens;
		}
	}
}