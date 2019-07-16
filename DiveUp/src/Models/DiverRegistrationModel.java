package Models;

public class DiverRegistrationModel {
	
	private String firstName = "";
	private String lastName = "";
	private String ssn = "";
	private String diverID = "";
	private String email = "";
	private String phone = "";
	private boolean hasInsurance = false;
	
	public DiverRegistrationModel() {
		
	}
	
	public DiverRegistrationModel(
			String firstName,
			String lastName,
			String ssn,
			String diverID,
			String email,
			String phone,
			boolean hasInsurance)
	{	
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.diverID = diverID;
		this.email = email;
		this.phone = phone;
		this.hasInsurance = hasInsurance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getDiverID() {
		return diverID;
	}

	public void setDiverID(String diverID) {
		this.diverID = diverID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isHasInsurance() {
		return hasInsurance;
	}

	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	
	
}
