package Classes;
import java.util.List;

public class Diver extends Entity {

	public Diver(String licenseID) {
		super();
		this.licenseID = licenseID;
	}
	String licenseID;
	List<Course> completedCourses;
	List<Course> futureCourses;
	DivingBook book;
	
	
	public String getLicenseID() {
		return licenseID;
	}
	public void setLicenseID(String licenseID) {
		this.licenseID = licenseID;
	}
	
	@Override
	public String toString() {
		return "Diver [licenseID=" + licenseID + ", firstName=" + firstName + ", lastName=" + lastName + ", id=" + id
				+ ", contactInfo=" + contactInfo + "]";
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
