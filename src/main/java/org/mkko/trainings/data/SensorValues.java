package org.mkko.trainings.data;

import java.io.Serializable;

public class SensorValues implements Serializable{

	private String sensor_airport;
	private String sensor_location;
	private String aircraft_number;
	private String sensor_type;
	private String sensor_id;
	private Integer sensor_value;
	private Long date;
	
	public String getSensor_airport() {
		return sensor_airport;
	}
	public void setSensor_airport(String sensor_airport) {
		this.sensor_airport = sensor_airport;
	}
	public String getSensor_location() {
		return sensor_location;
	}
	public void setSensor_location(String sensor_location) {
		this.sensor_location = sensor_location;
	}
	public String getAircraft_number() {
		return aircraft_number;
	}
	public void setAircraft_number(String aircraft_number) {
		this.aircraft_number = aircraft_number;
	}
	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(String sensor_id) {
		this.sensor_id = sensor_id;
	}
	public Integer getSensor_value() {
		return sensor_value;
	}
	public void setSensor_value(Integer sensor_value) {
		this.sensor_value = sensor_value;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	
	
}
