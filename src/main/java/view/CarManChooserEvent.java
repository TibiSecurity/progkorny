package view;

import java.util.EventObject;

public class CarManChooserEvent extends EventObject {
	private String manufacturerName;
	
	public CarManChooserEvent(Object source) {
		super(source);
	}
	
	public CarManChooserEvent(Object source, String manufacturerName) {
		super(source);
		
		this.manufacturerName = manufacturerName;
	}
	
	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
}
