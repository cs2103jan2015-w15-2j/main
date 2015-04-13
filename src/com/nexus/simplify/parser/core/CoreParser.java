//@author A0111035A

package com.nexus.simplify.parser.core;

public class CoreParser extends TokenParser {
	private static OperationParser _opParser = new OperationParser();
	private static ParamParser _paramParser = new ParamParser();

	final String MESSAGE_UNPARSED_TOKENS = "Unparsed tokens detected: %1$s";

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		tokenList = _opParser.parseTokens(tokenList);
		tokenList = _paramParser.parseTokens(tokenList);

		// There should not be remaining unidentified tokens who are not
		// recognised as operation or parameters
		if (!isTokenListEmpty(tokenList)) {
			throw new Exception(appendStringArr(MESSAGE_UNPARSED_TOKENS,
					tokenList));
		}
		return tokenList;
	}

	private String appendStringArr(String string, String[] stringArr) {
		String newString = string;
		for (String str : stringArr) {
			string += " " + str;
		}
		return newString;
	}
}
