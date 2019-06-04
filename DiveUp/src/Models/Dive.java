package Models;

import java.util.Date;

public class Dive {

	
	
	int diveNum;
	String diverID;
	String location;
	Date date; //Date function in order to be written in the diving book
	double maxDepth;
	String startTime;
	String endTime;
	int airStart;
	int airEnd;
	
	
	
	
	public double getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(double maxDepth) {
		this.maxDepth = maxDepth;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getAirStart() {
		return airStart;
	}
	public void setAirStart(int airStart) {
		this.airStart = airStart;
	}
	public int getAirEnd() {
		return airEnd;
	}
	public void setAirEnd(int airEnd) {
		this.airEnd = airEnd;
	}
	public int getDiveNum() {
		return diveNum;
	}
	public void setDiveNum(int diveNum) {
		this.diveNum = diveNum;
	}
	public String getDiver() {
		return diverID;
	}
	public void setDiver(String diverID) {
		this.diverID = diverID;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
