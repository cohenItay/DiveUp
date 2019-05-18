package Classes;

import java.util.Date;

public class Dive {

	
	
	int diveID;
	String diver;
	String location;
	Date date; //Date function in order to be written in the diving book
	int maxDepth;
	String startTime;
	String endTime;
	int airStart;
	int airEnd;
	
	
	
	
	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
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
	public int getDiveID() {
		return diveID;
	}
	public void setDiveID(int diveID) {
		this.diveID = diveID;
	}
	public String getDiver() {
		return diver;
	}
	public void setDiver(String diverID) {
		this.diver = diverID;
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
