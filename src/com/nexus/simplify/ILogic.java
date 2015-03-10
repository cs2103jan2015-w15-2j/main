package com.nexus.simplify;

public interface ILogic {
	public String executeCommand(Command comd);
	public CommandResult initialise(String filename);
}
