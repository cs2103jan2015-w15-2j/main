//@author A0111035A

package com.nexus.simplify.parser.core;

import com.nexus.simplify.logic.usercommand.OperationType;
import com.nexus.simplify.parser.data.CommandData;

public class OperationParser extends TokenParser {
	private static final CommandData commandData = CommandData.getInstance();
	private static final DisplayParser displayParser = new DisplayParser();
	private static final IndexParser indexParser = new IndexParser();
	private static final FileLocationParser fileLocationParser = new FileLocationParser();
	/**
	 * Parses tokenList for operation tokens.
	 */
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
			remainingTokens = switchUserOperation(userOp, remainingTokens);
			return remainingTokens;
		}
	}

	/**
	 * Switch case statements for user specified operation. Remove used
	 * operation token from remainingTokens
	 * 
	 * @param userOp			User specified operation token.
	 * @param remainingTokens	List of remaining tokens to be parsed.
	 * @return					List of remaining tokens with userOp remove.
	 * @throws Exception		When parsing of specified token list fails. 
	 */
	private String[] switchUserOperation(OperationType userOp,
			String[] remainingTokens) throws Exception {
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