package Models;

public class Employee extends Entity {

	String seniority;
	double salary;

	public Employee(String seniority, double salary) {
		super();
		this.seniority = seniority;
		this.salary = salary;
	}
	
	
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
}
