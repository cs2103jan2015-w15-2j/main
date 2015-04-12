package com.nexus.simplify.parser.core;

import com.nexus.simplify.parser.data.CommandData;
/**
 * Parses display preference string. This parser is called immediate when display command is parsed.
 * 
 * @author Davis
 *
 */
public class DisplayParser extends TokenParser {
	CommandData commandData = CommandData.getInstance();
	
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
