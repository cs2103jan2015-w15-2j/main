package com.nexus.simplify.parser.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.parser.data.CommandData;

public class OperationParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();
	DisplayParser displayParser = new DisplayParser();
	IndexParser indexParser = new IndexParser();
	FileLocationParser fileLocationParser = new FileLocationParser();
	Logger LOGGER = LoggerFactory.getLogger(OperationParser.class.getName());

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String opString = tokenList[0].toLowerCase();
			tokenList[0] = opString;
			OperationType userOp = commandData.getOperationType(opString);

			// Implement adding of task without providing keywords for add e.g
			// "add"
			if (userOp == OperationType.INVALID) {
				userOp = OperationType.ADD;
				opString = "";
			}
			String[] remainingTokens = getRemainingTokens(opString, tokenList);

			// Handle commands that have special first parameters
			switch (userOp) {
			case DISPLAY:
				commandData.setOp(userOp);
				return displayParser.parseTokens(remainingTokens);

			case MODIFY:
				commandData.setOp(userOp);
				String[] postIndexParseTokens = indexParser
						.parseTokens(remainingTokens);
				if (postIndexParseTokens.equals(remainingTokens)) {
					return fileLocationParser.parseTokens(remainingTokens);
				} else {
					return postIndexParseTokens;
				}

			case DONE:
				commandData.setOp(userOp);
				return indexParser.parseTokens(remainingTokens);

			case DELETE:
				commandData.setOp(userOp);
				return indexParser.parseTokens(remainingTokens);

			default:
				commandData.setOp(userOp);
				return remainingTokens;
			}
		}
	}
}