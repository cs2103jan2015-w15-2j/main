package com.nexus.simplify.parser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.parser.data.CommandData;

public class WorkloadParser extends TokenParser {
	Logger LOGGER = LoggerFactory.getLogger(WorkloadParser.class.getName());
	CommandData commandData = CommandData.getInstance();

	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			if (tokenList[0].matches("[1-5]") && tokenList[tokenList.length - 1].matches("[1-5]")) {
				commandData.setWorkload(tokenList[tokenList.length -1]);
				throw new Exception("Two possible values of deadline detected. The second one is considered");
			} else if (tokenList[0].matches("[1-5]")) {
				String workload = tokenList[0];
				commandData.setWorkload(workload);
				return getRemainingTokens(workload, tokenList);
			} else if (tokenList[tokenList.length - 1].matches("[1-5]")) {
				String workload = tokenList[tokenList.length -1];
				commandData.setWorkload(workload);
				return getRemainingTokens(workload, tokenList);
			}
			return tokenList;
		}

	}
}
