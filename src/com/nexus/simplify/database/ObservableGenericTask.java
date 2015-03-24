/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.beans.property.*;

public class ObservableGenericTask {

	//------------------//
	// Class Attributes //
	//------------------//

	private final StringProperty id;
	private final StringProperty name;
	private final IntegerProperty workload;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableGenericTask(String name, int workload, String id) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.workload = new SimpleIntegerProperty(workload);
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//

	public String getId() {
		return id.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public int getWorkload() {
		return workload.get();
	}
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//

	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setWorkload(int workload) {
		this.workload.set(workload);
	}
}
