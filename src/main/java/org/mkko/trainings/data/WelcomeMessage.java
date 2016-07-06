/**
 * 
 */
package org.mkko.trainings.data;

/**
 * @author mkokott
 *
 */
public class WelcomeMessage {

	private String _id;
	private String _rev;
	private int numberOfVisits;
	
	public WelcomeMessage(String id){
		
		this.setId(id);
		this.numberOfVisits = 0;
	}
	
	public int getNumberOfVisits() {
		return numberOfVisits;
	}
	public void setNumberOfVisits(int numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getRev() {
		return _rev;
	}

	public void setRev(String rev) {
		this._rev = rev;
	}
}
