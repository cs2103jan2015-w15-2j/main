//@author A0111035A

package com.nexus.simplify.parser.core;

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
			boolean workloadFound = false;
			String workloadString = null;
			String workloadValueString = null;
			for (int i = 0; i < tokenList.length; i++) {
				String currentToken = tokenList[i];

				if (currentToken.matches("[wW][1-5]")) {
					if (workloadFound) {
						throw new Exception("More than one workload values were present. Please specify one workload value only.");
					}
					workloadString = tokenList[i];
					workloadValueString = workloadString.substring(INDEX_START_OF_WORKLOAD);
					commandData.setWorkload(workloadValueString);
					workloadFound = true;
				}
			}
			if (workloadFound) {
				return getRemainingTokens(workloadString, tokenList);
			} else {
				return tokenList;
			}
		}

	}
}
