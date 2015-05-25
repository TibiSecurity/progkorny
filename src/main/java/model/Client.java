package model;

import java.util.Date;

/**
 * Client class.
 */
public class Client {
	/**
	 * The id of the client.
	 */
	private int id;
	/**
	 * The name of the client.
	 */
	private String name;
	/**
	 * The phone number of the client.
	 */
	private String phoneNum;
	/**
	 * The date when the client was added to the database.
	 */
	private Date creationDate;
	/**
	 * The date when the client was modified in the database.
	 */
	private Date modificationDate;
	/**
	 * Flag that indicates when the client is deleted on the GUI by the user.
	 */
	boolean markedForDeletion;

	/**
	 * Constructs a {@code Client} object.
	 * 
	 * @param name The number of the client
	 * @param phoneNum The phone number of the client
	 * @param todaysDate The date when the client was added to the database
	 */
	public Client(String name, String phoneNum, Date todaysDate) {
		this.name = name;
		this.phoneNum = phoneNum;
		this.creationDate = todaysDate;
	}
	
	/**
	 * Constructs a {@code Client} object.
	 * 
	 * @param id The id of the client
	 * @param name The name of the client
	 * @param phoneNum The phone number of the client
	 * @param todaysDate The date when the client was added to the database
	 * @param modificationDate The date when the client was modified in the database
	 * 
	 */
	public Client(int id, String name, String phoneNum, Date todaysDate,
			Date modificationDate) {
		this.id = id;
		this.name = name;
		this.phoneNum = phoneNum;
		this.creationDate = todaysDate;
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the id of the client.
	 *
	 * @return the id of the client
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the client.
	 *
	 * @param id - the id of the client
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the client.
	 *
	 * @return the name of the client
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the client.
	 *
	 * @param name - the name of the client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the phone number of the client.
	 *
	 * @return the phone number of the client
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * Sets the phone number of the client.
	 *
	 * @param phoneNum - the phone number of the client
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * Gets the date when the client was added to the database.
	 *
	 * @return the date when the client was added to the database
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the date when the client was added to the database.
	 *
	 * @param creationDate - the date when the client was added to the database
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Gets the date when the client was modified in the database.
	 *
	 * @return  the date when the client was modified in the database
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the date when the client was modified in the database.
	 *
	 * @param modificationDate - the date when the client was modified in the database
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets whether the client is marked for deletion or not.
	 *
	 * @return whether the client is marked for deletion or not
	 */
	public boolean isMarkedForDeletion() {
		return markedForDeletion;
	}

	/**
	 * Changes the client's marked-for-deletion status.
	 *
	 * @param markedForDeletion - the client's marked-for-deletion status
	 */
	public void setMarkedForDeletion(boolean markedForDeletion) {
		this.markedForDeletion = markedForDeletion;
	}
	
	/**
	 * Creates a {@code toString} method for the class.
	 *
	 * @return the string representation of a client
	 */
	public String toString() {
		return "ID: " + id + ", name: " + name + ", phone number: " + phoneNum
				+ ", creation date: " + creationDate + ", modification date: "
				+ modificationDate;
	}
}
