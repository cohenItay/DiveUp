package Controllers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
	
	public Course getCourseByID(int id) throws ParseException
	{
		courseSqlQueries  dbConnection = new courseSqlQueries();//connection to the DB
		return dbConnection.getCourseByID(id);//Getting divers list from the DB

	}
	
	public List<String> getTypes()
	{
		courseSqlQueries dbConnection = new courseSqlQueries();
		return dbConnection.getTypes();
	}

	public Map<Integer,String> checkCourseAdd(String name,String type,String employee,String maxAmount,String price,Date startDate,Date endDate,String desc) {
		
		Map<Integer,String> violations=new HashMap<>();


       
		if(name==null || name.equals("")){
            violations.put(invalid_courseName,"name is empty");
            
        }
		
		
        if(type==null || type.equals("")){
            violations.put(type_empty,"type is empty");
            
        }
       
         

        if(employee==null || employee.equals("")){
            violations.put(firstName_empty,"First name is empty");
        }
        
        
        try {
        	
        
        if(Integer.valueOf(maxAmount) <=0 || maxAmount.isEmpty()){
            violations.put(max_amount_wrong,"Wrong maxamount");
        }
        }catch (Exception e) {
			// TODO Auto-generated catch block
        	violations.put(max_amount_wrong,"Wrong maxamount");
		
		}
        
            
        try {
        
        	if(Integer.valueOf(price)<=0 || price.isEmpty()){
                violations.put(price_wrong,"price is wrong");
            }
        }catch (Exception e) {
			// TODO Auto-generated catch block
        	violations.put(price_wrong,"Wrong price");
		
		}
        
        
        
        
        long diff = endDate.getTime() - startDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if(days < 0)
		{
			violations.put(invalid_dates, "Invalid dates");
		}
        
        
        // check if mail is valid
        if(desc==null || desc.isEmpty()){
            violations.put(desc_empty,"desc is empty");
        }
        
            
		return violations;
	}
	
	public boolean addCourse(String name,String type,String employee,String maxAmount,String price,Date startDate,Date endDate,String desc)
	{
		courseSqlQueries dbConnection = new courseSqlQueries();
		dbConnection.addCourse(name,type,employee,maxAmount,price,startDate,endDate,desc);
		return true;
	}


}
