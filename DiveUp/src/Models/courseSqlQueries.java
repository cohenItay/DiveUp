package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Course;
import Classes.Diver;
import Classes.Item;


	public class courseSqlQueries{

		private sqlConnection dbconnection;//create sql connection instance
		public Connection connection;//create connection instance
		public courseSqlQueries()
		{
			dbconnection=sqlConnection.getInstance();//initiate sql connection
			connection = dbconnection.conn;//get connection from sql connection
		}
		
		
		//get courses list
		public List<Course> getCourses()
		{
			List<Course> res = new ArrayList<>();//creating courses list
			Statement stmt;
			try {
				/* getting all information from courses table */
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select  * from Course,CourseType where Course.typeID = CourseType.typeID ORDER BY startDate");
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				Course c;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				/* creating Item object for each item in the db table */
				while (rs.next()) {
					c = new Course();
					c.setID(rs.getInt("courseID"));
					c.setName(rs.getString("courseType"));
					c.setInstructor(rs.getString("employeeID"));
					c.setCurrentAmount(rs.getInt("currentAmount"));
					c.setMaxDivers(rs.getInt("maxDivers"));
					c.setPrice(rs.getDouble("price"));
					c.setStartDay(formatter.parse(rs.getString("startDate")));
					c.setEndDay(formatter.parse(rs.getString("endDate")));
					c.setDesc(rs.getString("desc"));
				    res.add(c);//add item to the list
				}
			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				if (err.contains("query does not return ResultSet"))
				{
					;//Query completed, didn't have to return value
				}
				else
				{
					e.printStackTrace();
				}
			
			}
		return res;
		}
		
		//get current registered divers amount for a specific course by id
		public int getCurrentAmount(int id)
		{
			
			Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select currentAmount from Course where courseID = "+id);
				return rs.getInt("currentAmount");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return -1;
			
			}	
		}
		
		//get maximum available divers for course by id
		public int getMaxAmount(int id)
		{
			
			Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select maxDivers from Course where courseID = "+id);
				return rs.getInt("maxDivers");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return -1;
			
			}	
		}
		
		//get coursename by id
		public String getCourseName(int courseID)
		{
			Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select desc from Course where courseID = "+courseID);
				return rs.getString("desc");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return "";
			
			}
		
		}
		
		public String getCourseStartDay(int courseID)
		{
			Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select startDate from Course where courseID = "+courseID);
				return rs.getString("startDate");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return "";
			
			}
		
		}
		
		//update course registered amount
		public void updateAmount(int courseID,int currentAmount)
		{
			Statement stmt;
			try {
				String query = "update Course set currentAmount = ? where courseID = "+courseID;
			      PreparedStatement preparedStmt = connection.prepareStatement(query);
			      preparedStmt.setInt   (1, currentAmount+1);
			      preparedStmt.executeUpdate();

			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
					
					
				}
		
		}

		
		//add customer registration to course to DB
		public void registerCourse(int courseID, String diverID)
		{
			String sql = "INSERT INTO RegisteredCourses(courseID,diverID,courseName) VALUES(?,?,?)";
			 
	        PreparedStatement pstmt;
			try {
				//Insert data to new DB record
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, courseID);
			    pstmt.setString(2, diverID);
			    String courseName = getCourseName(courseID);
			    pstmt.setString(3, courseName);
			    int currentAmount = getCurrentAmount(courseID);
			    updateAmount(courseID, currentAmount);
			    
			    pstmt.executeUpdate();
			     
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				//If trying to add a diver with exist ID
				if (err.contains("Abort due to constraint violation (UNIQUE constraint failed:"))
				{
					System.out.println("ID must be unique, Failed to add new employee");
				}
				else
				{
					e.printStackTrace();
				}
			} 

		}

		public List<Course> getCoursesByID(String id)
		{
			List<Course> res = new ArrayList<>();//creating courses list
			Statement stmt;
			try {
				/* getting all information from courses table */
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Course,RegisteredCourses where Course.courseID = RegisteredCourses.courseID and RegisteredCourses.diverID =  "+id);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				Course c;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				/* creating Item object for each item in the db table */
				while (rs.next()) {
					c = new Course();
					c.setID(rs.getInt("courseID"));
					c.setName(rs.getString("courseName"));
					c.setInstructor(rs.getString("employeeID"));
					c.setCurrentAmount(rs.getInt("currentAmount"));
					c.setMaxDivers(rs.getInt("maxDivers"));
					c.setPrice(rs.getDouble("price"));
					c.setStartDay(formatter.parse(rs.getString("startDate")));
					c.setEndDay(formatter.parse(rs.getString("endDate")));
					c.setDesc(rs.getString("desc"));
				    res.add(c);//add item to the list
				}
			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				if (err.contains("query does not return ResultSet"))
				{
					;//Query completed, didn't have to return value
				}
				else
				{
					e.printStackTrace();
				}
			
			}
		return res;
		}
		


		public Course getCourseByID(int id) throws ParseException
		{
		
			Statement stmt;
			Course c=null;
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				/* getting all information from Diver table */
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Course where courseID = "+id);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				/* creating course object for each item in the db table */
				if (rs.next()) {
					c=new Course();
					c.setId(id);
					c.setType(rs.getString("typeID"));
					c.setInstructor(rs.getString("employeeID"));
					c.setCurrentAmount(rs.getInt("currentAmount"));
					c.setMaxDivers(rs.getInt("maxDivers"));
					c.setPrice(rs.getDouble("price"));
					c.setStartDay(formatter.parse(rs.getString("startDate")));
					c.setEndDay(formatter.parse(rs.getString("endDate")));
					c.setDesc(rs.getString("desc"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				if (err.contains("query does not return ResultSet"))
				{
					;//Query completed, didn't have to return value
				}
				else
				{
					e.printStackTrace();
				}
			
			}
			return c;
		}
		

		public List<String> getTypes()
		{
			
				List<String> res = new ArrayList<>();//creating types list
				Statement stmt;
				try {
					/* getting all information from courseType table */
					stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("select  * from CourseType");
					
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();
					/* creating Item object for each item in the db table */
					while (rs.next()) {
						
					    res.add(rs.getString("courseType"));//add item to the list
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					String err = e.getMessage();
					if (err.contains("query does not return ResultSet"))
					{
						;//Query completed, didn't have to return value
					}
					else
					{
						e.printStackTrace();
					}
				
				}
			return res;
			}
			
		
		
		public int getNewCourseID()
		{
			Statement stmt;
			Course c=new Course();
			try {
				/* getting all information from items table */
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Course ORDER BY courseID  DESC LIMIT 1");
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				
				if (rs.next()) {
					return rs.getInt("courseID")+1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				if (err.contains("query does not return ResultSet"))
				{
					;//Query completed, didn't have to return value
				}
				else
				{
					e.printStackTrace();
				}
			
			}
		return -1;

		}
		public int getTypeID(String typeName)
		{
			Statement stmt;
			try
			{
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * from CourseType where courseType ="+"\"" + typeName + "\"");
				if(rs.next())
				{
					return rs.getInt("typeID");
				}
			}
			catch(Exception e)
			{
			 e.printStackTrace();
			}
			return -1;
		}
		

		
		public boolean addCourse(String type,String employee,String maxAmount,String price,Date startDate,Date endDate,String desc)
		{
			String sql = "INSERT INTO Course(courseID,typeID,employeeID,currentAmount,maxDivers,price,startDate,endDate,desc) VALUES(?,?,?,?,?,?,?,?,?)";//query string
			 
	        PreparedStatement pstmt;
	        //Insert the parameters to new DB record
			try {
				
				pstmt = connection.prepareStatement(sql);
				int id = getNewCourseID();
				if(id == -1)
				{
					id = 1;
				}
				pstmt.setInt(1,id );
				
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
        		Matcher matcher = pattern.matcher(employee);
        		if (matcher.find())
        		{
        		    employee = matcher.group(1);
        		}
        		int typeID = getTypeID(type);
			    pstmt.setInt(2,typeID);
			    pstmt.setString(3, employee);
			    pstmt.setInt(4, 0);
			    pstmt.setInt(5, Integer.valueOf(maxAmount));
			    pstmt.setDouble(6, Double.valueOf(price));
			    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    pstmt.setString(7,formatter.format(startDate));
			    pstmt.setString(8,formatter.format(endDate));
			    pstmt.setString(9, desc);
			    pstmt.executeUpdate();
				
			   
			} catch (SQLException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();//printing error if happend
					return false;
				
			} 
	     return true;
		}
}
