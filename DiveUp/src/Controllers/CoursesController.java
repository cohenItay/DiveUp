package Controllers;

import Models.sqlConnection;

public class CoursesController implements Controller {

	public void registerNewCourse(int courseID,String diverID)
	{
		
		sqlConnection dbConnection = sqlConnection.getInstance();
		dbConnection.registerCourse(courseID, diverID);
	}
	
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
