package Controllers;

import java.util.List;

import Classes.Course;
import Classes.Diver;
import Models.courseSqlQueries;
import Models.diverSqlQueries;
import Models.sqlConnection;

public class CoursesController implements Controller {

	
	//Register new course
	public boolean registerNewCourse(int courseID,String diverID)
	{
		//opening sqlQuries instance
		courseSqlQueries dbConnection = new courseSqlQueries();
		
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
		courseSqlQueries  dbConnection = new courseSqlQueries();//connection to the DB
		return dbConnection.getCourses();//Getting divers list from the DB
	}
	
	public List<Course> getCoursesByID(String id){
		courseSqlQueries  dbConnection = new courseSqlQueries();//connection to the DB
		return dbConnection.getCoursesByID(id);//Getting divers list from the DB
	}
	

	public String getCourseName(int courseID)
	{
		courseSqlQueries dbConnection = new courseSqlQueries();
		return dbConnection.getCourseName(courseID);
	}
	
	public String getCourseStartDay(int courseID)
	{
		courseSqlQueries dbConnection = new courseSqlQueries();
		return dbConnection.getCourseStartDay(courseID);
	}
	
	public Course getCourseByID(int id)
	{
		courseSqlQueries  dbConnection = new courseSqlQueries();//connection to the DB
		return dbConnection.getCourseByID(id);//Getting divers list from the DB

	}

}
