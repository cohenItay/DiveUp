package Classes;
import java.util.Date;
import java.util.List;

public class Course extends Asset{

	List<Diver> divers; //list of all Participants
	String instructorID; //Scheduling an instructor
	int MaxDivers; //Max Divers per course
	int currentAmount;
	Date startDay;
	Date endDay;
	String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Course [instructorID=" + instructorID + ", MaxDivers=" + MaxDivers + ", currentAmount=" + currentAmount
				+ ", startDay=" + startDay + ", endDay=" + endDay + ", id=" + id + ", type=" + type + "]";
	}
	public List<Diver> getDivers() {
		return divers;
	}
	public int getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	public int getId() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setDivers(List<Diver> divers) {
		this.divers = divers;
	}
	public String getInstructor() {
		return instructorID;
	}
	public void setInstructor(String instructorID) {
		this.instructorID = instructorID;
	}
	public int getMaxDivers() {
		return MaxDivers;
	}
	public void setMaxDivers(int maxDivers) {
		MaxDivers = maxDivers;
	}
	
	
	
}
