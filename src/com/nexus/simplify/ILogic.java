package com.nexus.simplify;

public interface ILogic {
	public CommandResult executeCommand(Command comd);
	public CommandResult initialise(String filename);
}
