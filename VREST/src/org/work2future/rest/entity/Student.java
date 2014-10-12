package org.work2future.rest.entity;

import java.io.Serializable;
import java.util.Date;

import com.mongodb.BasicDBObject;

public class Student extends BasicDBObject implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1585063991403382106L;
	
	
	private String _id;
	private Date dateofbirth;
	private String address;
	private Date fromDate;
	private Date toDate;
	//private ArrayList<String>skills = new ArrayList<String>();
	private String currentEducation;
	private String phoneNo;
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/*public ArrayList<String> getSkills() {
		return skills;
	}
	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}*/
	public String getCurrentEducation() {
		return currentEducation;
	}
	public void setCurrentEducation(String currentEducation) {
		this.currentEducation = currentEducation;
	}
	
	
	
}
