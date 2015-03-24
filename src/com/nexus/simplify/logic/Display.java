/*
 * @author David Zhao
 * */

package com.nexus.simplify.logic;


public class Display {
	
	public Display() {}
		
	public String execute(String[] parameter){
		String option = parameter[0];
		Database database = new Database();
		if(isNumeric(option)){
			database.display(option);
			
		}
	}
	
	private static boolean isNumeric(String str){
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
}
