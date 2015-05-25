package model;

import java.util.Date;
import java.util.List;

/**
 * Car class.
 */
public class Car {
	/**
	 * The license plate number of the car.
	 */
	String licensePlateNo;
	/**
	 * The car's manufacturer name.
	 */
	String manufacturerName;
	/**
	 * The car's model name.
	 */
	String modelName;
	/**
	 * The car owner's name.
	 */
	String clientName;
	/**
	 * The year when the car was made.
	 */
	String year;
	/**
	 * The car's color.
	 */
	String color;
	/**
	 * The status of the car's odometer.
	 */
	Integer odometer;
	/**
	 * Flag that indicates when the car is deleted on the GUI by the user.
	 */
	boolean markedForDeletion;

	/**
	 * Constructs a {@code Car} object.
	 * 
	 * @param licensePlateNo The license plate number of the car
	 * @param manufacturerName The car's manufacturer name
	 * @param modelName The car's model name
	 * @param clientName The car owner's name
	 * @param year The year when the car was made
	 * @param color The car's color
	 * @param odometer The status of the car's odometer
	 */
	public Car(String licensePlateNo, String manufacturerName,
			String modelName, String clientName, String year, String color,
			Integer odometer) {
		
		this.licensePlateNo = licensePlateNo;
		this.manufacturerName = manufacturerName;
		this.modelName = modelName;
		this.clientName = clientName;
		this.year = year;
		this.color = color;
		this.odometer = odometer;
		this.markedForDeletion = false;
	}

	/**
	 * Gets the license plate number of the car.
	 *
	 * @return the license plate number
	 */
	public String getLicensePlateNo() {
		return licensePlateNo;
	}

	/**
	 * Sets the license plate number of the car.
	 *
	 * @param licensePlateNo - the license plate number of the car
	 */
	public void setLicensePlateNo(String licensePlateNo) {
		this.licensePlateNo = licensePlateNo;
	}

	/**
	 * Gets the car's manufacturer name.
	 *
	 * @return the manufacturer name
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * Sets the car's manufacturer name.
	 *
	 * @param manufacturerName - the manufacturer's name
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * Gets the car's model name.
	 *
	 * @return the car's model name
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Sets the car's model name.
	 *
	 * @param modelName - the model's name
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * Gets the car owner's name.
	 *
	 * @return car owner's name
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Sets the car owner's name.
	 *
	 * @param clientName - the car owner's name
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * Gets the year when the car was made.
	 *
	 * @return the year when the car was made
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Sets the year when the car was made.
	 *
	 * @param year - the year when the car was made
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Gets the car's color.
	 *
	 * @return the car's color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the car's color.
	 *
	 * @param color - the car's color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Gets the status of the car's odometer.
	 *
	 * @return the status of the car's odometer
	 */
	public Integer getOdometer() {
		return odometer;
	}

	/**
	 * Sets the status of the car's odometer.
	 *
	 * @param odometer - the status of the car's odometer
	 */
	public void setOdometer(Integer odometer) {
		this.odometer = odometer;
	}
	
	/**
	 * Gets whether the car is marked for deletion or not.
	 *
	 * @return whether the car is marked for deletion or not
	 */
	public boolean isMarkedForDeletion() {
		return markedForDeletion;
	}

	/**
	 * Changes the car's marked-for-deletion status.
	 *
	 * @param markedForDeletion - the car's marked-for-deletion status
	 */
	public void setMarkedForDeletion(boolean markedForDeletion) {
		this.markedForDeletion = markedForDeletion;
	}
	
	/**
	 * Creates a {@code toString} method for the class.
	 *
	 * @return the string representation of a car
	 */
	public String toString() {
		return "Lic.plate.no: " + licensePlateNo + ", manName: "
				+ manufacturerName + ", modelName: " + modelName + ", clientName: "
				+ clientName + ", year: " + year + ", color: " + color
				+ ", odometer: " + odometer;
	}
}
