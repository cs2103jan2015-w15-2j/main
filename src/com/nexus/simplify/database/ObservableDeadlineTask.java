/*
 * @author Tan Qian Yi
 */

package com.nexus.simplify.database;

import javafx.beans.property.*;

import org.joda.time.format.*;
import org.joda.time.DateTime;

public class ObservableDeadlineTask extends ObservableGenericTask {
	
	DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/M/yyyy km").toFormatter();
	
	//-----------------//
	// Class Attribute //
	//-----------------//
	
	private final ObjectProperty<DateTime> deadline;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public ObservableDeadlineTask(String name, DateTime deadline, int workload, String id) {
		super(name, workload, id);
		this.deadline = new SimpleObjectProperty<DateTime>(deadline);
	}
	
	//--------------------//
	// Attribute Accessor //
	//--------------------//
	
	public DateTime getDeadline() {
		return deadline.get();
	}
	
	//-------------------//
	// Attribute Mutator //
	//-------------------//
	
	public void setDeadline(DateTime deadline) {
		this.deadline.set(deadline);
	}
}
