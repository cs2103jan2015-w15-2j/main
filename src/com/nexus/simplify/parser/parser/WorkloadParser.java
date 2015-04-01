package com.nexus.simplify.parser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexus.simplify.parser.data.CommandData;

public class WorkloadParser extends TokenParser {
	Logger LOGGER = LoggerFactory.getLogger(WorkloadParser.class.getName());
	CommandData commandData = CommandData.getInstance();
	
	final int INDEX_START_OF_WORKLOAD = 1;
	
	@Override
	public String[] parseTokens(String[] tokenList) throws Exception {
		if (isTokenListEmpty(tokenList)) {
			return tokenList;
		} else {
			String workloadString = null;
			String workloadValueString = null;
			if (tokenList[0].matches("[wW][1-5]")) {
				workloadString = tokenList[0];
				workloadString = workloadString.substring(INDEX_START_OF_WORKLOAD);
			}
			
			if (tokenList[tokenList.length - 1].matches("[wW][1-5]")) {
				workloadString = tokenList[tokenList.length - 1];
				workloadValueString = workloadString.substring(INDEX_START_OF_WORKLOAD);
			}
			
			if (workloadValueString != null && workloadValueString.matches("[1-5]")) {
					commandData.setWorkload(workloadValueString);
					return getRemainingTokens(workloadString, tokenList);
			}
			return tokenList;
		}

	}
}
