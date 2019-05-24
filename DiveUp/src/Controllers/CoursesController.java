package Controllers;

import Models.courseSqlQueries;
import Models.sqlConnection;

public class CoursesController implements Controller {

	
	//Register new course
	public void registerNewCourse(int courseID,String diverID)
	{
		//opening sqlQuries instance
		courseSqlQueries dbConnection = new courseSqlQueries();
		dbConnection.registerCourse(courseID, diverID);
	}
	
	//Validate course and diver selected
	public boolean validateCourseRegistration(int courseID, String diverID)
	{
		if(courseID !=-1 && diverID !=null && !(diverID.equals("")))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
