package Controllers;

import java.util.List;

import Models.Course;
import Managers.CourseSqlQueries;

public class CoursesController implements Controller {

	
	//Register new course
	public boolean registerNewCourse(int courseID,String diverID)
	{
		//opening sqlQuries instance
		CourseSqlQueries dbConnection = new CourseSqlQueries();
		
		//if course is not full
		if(dbConnection.getCurrentAmount(courseID) < dbConnection.getMaxAmount(courseID))
		{
			//register the diver to the course
			dbConnection.registerCourse(courseID, diverID);
			//succeed
			return true;
		}
		else
		{
			//course is full
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
	
	public List<Course> getCourses(){
		CourseSqlQueries dbConnection = new CourseSqlQueries();//connection to the DB
		return dbConnection.getCourses();//Getting divers list from the DB
	}
	
	public List<Course> getCoursesByID(String id){
		CourseSqlQueries dbConnection = new CourseSqlQueries();//connection to the DB
		return dbConnection.getCoursesByID(id);//Getting divers list from the DB
	}
	

	public String getCourseName(int courseID)
	{
		CourseSqlQueries dbConnection = new CourseSqlQueries();
		return dbConnection.getCourseName(courseID);
	}
	
	public String getCourseStartDay(int courseID)
	{
		CourseSqlQueries dbConnection = new CourseSqlQueries();
		return dbConnection.getCourseStartDay(courseID);
	}
}
