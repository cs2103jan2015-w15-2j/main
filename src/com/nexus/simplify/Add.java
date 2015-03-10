package com.nexus.simplify;

public class Add {
	private static final int NAME_POS = 0;
	private static final int DEADLINE_POS = 1;
	private static final int WORKLOAD_POS = 2;
	
	public Add(){}
	
	public CommandResult execute(String[] parameter){
		String name = parameter[NAME_POS];
		String deadline = parameter[DEADLINE_POS];
		String workload = parameter[WORKLOAD_POS];
		
		
		writeToFile();
		return null;
		
	}
}
