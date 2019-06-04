package Managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Course;


public class CourseSqlQueries extends BaseSqlQuery{


	public CourseSqlQueries()
	{
		super();
	}


	//get courses list
	public List<Course> getCourses()
	{
		List<Course> res = new ArrayList<>();//creating courses list
		Statement stmt;
		try {
			/* getting all information from courses table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select  * from Course,CourseType where Course.typeID = CourseType.typeID");

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



}
