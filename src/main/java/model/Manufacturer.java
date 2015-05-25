package model;

/**
 * Manufacturer class.
 */
public class Manufacturer {
	/**
	 * The id of the manufacturer.
	 */
	private int manId;
	/**
	 * The name of the manufacturer.
	 */
	private String manufacturerName;
	
	/**
	 * Constructs a {@code Manufacturer} object.
	 * 
	 * @param manId The id of the manufacturer
	 * @param manufacturerName The name of the manufacturer
	 */
	public Manufacturer( int manId, String manufacturerName ){
		this.manId = manId;
		this.manufacturerName = manufacturerName;
	}
	
	/**
	 * Gets the id of the manufacturer.
	 *
	 * @return the id of the manufacturer
	 */
	public int getManId() {
		return manId;
	}
	
	/**
	 * Sets the id of the manufacturer.
	 *
	 * @param manId - the id of the manufacturer
	 */
	public void setManId(int manId) {
		this.manId = manId;
	}
	
	/**
	 * Gets the name of the manufacturer.
	 *
	 * @return the name of the manufacturer
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}
	
	/**
	 * Sets the name of the manufacturer.
	 *
	 * @param manufacturerName - the name of the manufacturer
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * Creates a {@code toString} method for the class.
	 * 
	 * @return the string representation of a manufacturer
	 */
	public String toString() {
		return "manId: " + manId + ", manName: " + manufacturerName;
	}
}
