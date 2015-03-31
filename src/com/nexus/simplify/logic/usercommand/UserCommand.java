package com.nexus.simplify.logic.usercommand;


public class UserCommand {
	public final int CURRENT_TIME_POS = 0;
	public final int DAY_OF_WEEK_POS = 1;
	public final int DAY_OF_MONTH_POS = 2;
	public final int MONTH_OF_YEAR = 3;
	public final int CURRENT_YEAR_POS = 4;
	
	private OperationType operation;
	private String[] parameter;
	private boolean[] searchField;
	
	public UserCommand(OperationType operation, String[] parameter){
		this.operation = operation;
		this.parameter = parameter;
	}
	
	public UserCommand(OperationType operation, String[] parameter, boolean[] searchField){
		this.operation = operation;
		this.parameter = parameter;
		this.searchField = searchField;
	}
	
	public OperationType getOperationType(){
		return operation;
	}
	
	public String[] getParameter(){
		return parameter;
	}
	
	public boolean[] getSearchField(){
		return searchField;
	}
}
