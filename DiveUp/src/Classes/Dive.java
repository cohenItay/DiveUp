package Classes;

import java.util.Date;

public class Dive {

	@Override
	public String toString() {
		return "Dive [diveID=" + diveID + ", diverID=" + diver + ", location=" + location + ", date=" + date
				+ "]";
	}
	int diveID;
	String diver;
	String location;
	Date date; //Date function in order to be written in the diving book
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
