//@author A0111035A

package com.nexus.simplify.parser.core;

import com.nexus.simplify.parser.data.CommandData;

/**
 * Parses display preference string. This parser is called immediately when
 * display command is parsed.
 * 
 * @author Davis
 *
 */
public class DisplayParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();

	/**
	 * Parses tokenList for display preference string.
	 */
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String displayPref = tokenList[0];
			commandData.setDisplay(displayPref);
			return getRemainingTokens(displayPref, tokenList);
		}

	}

}
