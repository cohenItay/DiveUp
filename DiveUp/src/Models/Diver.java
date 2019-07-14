package Models;
import java.util.List;

public class Diver extends Entity {

	public Diver(String licenseID) { //c'tor
		super(); //calling parent c'tor 
		this.licenseID = licenseID;
	}
	String licenseID;
	String insurance;
	List<Course> completedCourses;
	List<Course> futureCourses;
	DivingBook book;
	
	
	public String getLicenseID() {
		return licenseID;
	}
	public void setLicenseID(String licenseID) {
		this.licenseID = licenseID;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	
	@Override
	public String toString() {
		return "Diver [licenseID=" + licenseID + ", firstName=" + firstName + ", lastName=" + lastName + ", id=" + id
				+ ", contactInfo=" + email + phone+ "]";
	}
	public List<Course> getCompletedCourses() {
		return completedCourses;
	}
	public void setCompletedCourses(List<Course> completedCourses) {
		this.completedCourses = completedCourses;
	}
	public List<Course> getFutureCourses() {
		return futureCourses;
	}
	public void setFutureCourses(List<Course> futureCourses) {
		this.futureCourses = futureCourses;
	}
	public DivingBook getBook() {
		return book;
	}
	public void setBook(DivingBook book) {
		this.book = book;
	}
	
	
	
}
