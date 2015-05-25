package view;

import java.util.Date;
import java.util.EventObject;

public class FormEvent extends EventObject {
	private String name;
	private String phoneNum;
	private Date creationDate;

	private String carMan;
	private String carModel;
	private String licensePlateNo;
	private String year;
	private String color;
	private Integer odometer;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String name, String phoneNum,
			Date creationDate, String carMan, String carModel, String licensePlateNo,
			String year, String color, Integer odometer) {
		super(source);

		this.name = name;
		this.phoneNum = phoneNum;
		this.creationDate = creationDate;
		this.carMan = carMan;
		this.carModel = carModel;
		this.licensePlateNo = licensePlateNo;
		this.year = year;
		this.color = color;
		this.odometer = odometer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCarManufacturer() {
		return carMan;
	}

	public String getCarModel() {
		return carModel;
	}

	public String getLicensePlateNo() {
		return licensePlateNo;
	}

	public String getYear() {
		return year;
	}

	public String getColor() {
		return color;
	}

	public Integer getOdometer() {
		return odometer;
	}
}
