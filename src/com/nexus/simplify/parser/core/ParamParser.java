package com.nexus.simplify.parser.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.parser.data.CommandData;

public class ParamParser extends TokenParser {
	DateTimeParser dtParser = new DateTimeParser();
	WorkloadParser wlParser = new WorkloadParser();
	NameParser nameParser = new NameParser();
	CommandData commandData = CommandData.getInstance();
	Logger LOGGER = LoggerFactory.getLogger(CoreParser.class.getName());

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			try {

				tokenList = dtParser.parseTokens(tokenList);

				/*
				 * workload parameter should either be either the first or last
				 * token as it is the second last parameter type to be parsed.
				 */
				tokenList = wlParser.parseTokens(tokenList);

				// remaining tokens are take to be new name as it is the last
				// supported parameter
				tokenList = nameParser.parseTokens(tokenList);
				return tokenList;
			} catch (Exception e) {
				LOGGER.error("Parse Error on: {}", tokenList, e);
				throw e;
			}
		}

	}

}
