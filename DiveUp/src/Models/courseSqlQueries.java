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
import java.util.List;

import Classes.Course;


	public class courseSqlQueries{

		private sqlConnection dbconnection;
		public Connection connection;
		public courseSqlQueries()
		{
			dbconnection=sqlConnection.getInstance();
			connection = dbconnection.conn;
		}
		
		

		public List<Course> getCourses()
		{
			List<Course> res = new ArrayList<>();//creating divers list
			Statement stmt;
			try {
				/* getting all information from diver table */
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Course,CourseType where Course.typeID = CourseType.typeID");
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				Course c;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				/* creating Diver object for each diver in the db table */
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
				    res.add(c);//add diver to the list
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


}
