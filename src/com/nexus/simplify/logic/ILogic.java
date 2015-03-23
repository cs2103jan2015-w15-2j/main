package com.nexus.simplify.logic;


public interface ILogic {
	public CommandResult executeCommand(Command comd);
	public CommandResult initialise(String filename);
}
