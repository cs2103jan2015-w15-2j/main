//@author A0111035A

package com.nexus.simplify.parser.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamParser extends TokenParser {
	private static final DateTimeParser dtParser = new DateTimeParser();
	private static final WorkloadParser wlParser = new WorkloadParser();
	private static final NameParser nameParser = new NameParser();
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CoreParser.class.getName());

	private static final String LOG_PASRING_ERROR = "Parse Error on: {}";

	/**
	 * Parses unused tokens in tokenList for all parameters types.
	 */
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			try {
				// Parse DateTime tokens.
				tokenList = dtParser.parseTokens(tokenList);

				// Parse workload tokens.
				tokenList = wlParser.parseTokens(tokenList);

				// Remaining tokens are taken to be new name as it is the last
				// type of supported parameters
				tokenList = nameParser.parseTokens(tokenList);
				return tokenList;
			} catch (Exception e) {
				LOGGER.error(LOG_PASRING_ERROR, tokenList, e);
				throw e;
			}
		}

	}

}
