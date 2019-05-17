package Classes;

import java.util.Date;

public class Dive {

	@Override
	public String toString() {
		return "Dive [diveID=" + diveID + ", diverID=" + diverID + ", locationID=" + locationID + ", date=" + date
				+ "]";
	}
	int diveID;
	String diverID;
	int locationID;
	Date date;
	public int getDiveID() {
		return diveID;
	}
	public void setDiveID(int diveID) {
		this.diveID = diveID;
	}
	public String getDiverID() {
		return diverID;
	}
	public void setDiverID(String diverID) {
		this.diverID = diverID;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
