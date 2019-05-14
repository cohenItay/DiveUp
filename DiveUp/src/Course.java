import java.util.List;

public class Course extends Asset{

	List<Diver> divers;
	Employee instructor;
	int MaxDivers;
	
	public List<Diver> getDivers() {
		return divers;
	}
	public void setDivers(List<Diver> divers) {
		this.divers = divers;
	}
	public Employee getInstructor() {
		return instructor;
	}
	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}
	public int getMaxDivers() {
		return MaxDivers;
	}
	public void setMaxDivers(int maxDivers) {
		MaxDivers = maxDivers;
	}
	
	
}
