/**
 * 
 */
package org.mkko.trainings.data;

import java.util.Map;

/**
 * @author mkokott
 *
 */
public class SensorReading {

	private String _id;
	private String _rev;
	private Map<String, SensorValues> payload;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_rev() {
		return _rev;
	}
	public void set_rev(String _rev) {
		this._rev = _rev;
	}
	public Map<String, SensorValues> getPayload() {
		return payload;
	}
	public void setPayload(Map<String, SensorValues> payload) {
		this.payload = payload;
	}
}
