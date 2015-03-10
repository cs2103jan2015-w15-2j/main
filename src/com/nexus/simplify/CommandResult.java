package com.nexus.simplify;

public class CommandResult {
	//------------------//
	// Class Attributes //
	//------------------//
	
	private TaskList modifiedTaskList;
	private String resultantFeedback;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public CommandResult(TaskList modifiedTaskList, String resultantFeedback) {
		this.setModifiedTaskList(modifiedTaskList);
		this.setResultantFeedback(resultantFeedback);
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public TaskList getModifiedTaskList() {
		return modifiedTaskList;
	}

	public String getResultantFeedback() {
		return resultantFeedback;
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
	
	public void setModifiedTaskList(TaskList modifiedTaskList) {
		this.modifiedTaskList = modifiedTaskList;
	}

	public void setResultantFeedback(String resultantFeedback) {
		this.resultantFeedback = resultantFeedback;
	}	
}
