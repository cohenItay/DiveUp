package Controllers;

import Models.courseSqlQueries;
import Models.sqlConnection;

public class CoursesController implements Controller {

	
	//Register new course
	public boolean registerNewCourse(int courseID,String diverID)
	{
		//opening sqlQuries instance
		courseSqlQueries dbConnection = new courseSqlQueries();
		if(dbConnection.getCurrentAmount(courseID) < dbConnection.getMaxAmount(courseID))
		{
			dbConnection.registerCourse(courseID, diverID);
			return true;
		}
		else
		{
			return false;
		}
		
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
	
	

}
