package Screens;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Classes.Dive;
import Classes.Diver;


public class sqlConnection {
	
	private static sqlConnection dbConnection;//class instance
	public static Connection conn;//connection instabce
	
	/*Private constructor in order to implement singleton DP*/
	private sqlConnection() {
		Connect();
	}
	
	
	public static sqlConnection getInstance() {
		//if there is no connection to DB
		if (dbConnection == null)
			dbConnection = new sqlConnection(); // create no connection
		return dbConnection;//else return the current connection
	}
	
	/* create connection to the DB */
	public void Connect(){
	    
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\DB\\DiveUpDB.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	    
	}
	/* Run SQL Query */
	public String runQuery(Connection conn,String query){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);//function that runs the query
			
			ResultSetMetaData rsmd = rs.getMetaData(); // wrap the query result metadata
			int columnsNumber = rsmd.getColumnCount(); //get result column number
	
			//Running on the output
			while (rs.next()) {
				
				//Printing the output
			    for(int i = 1; i <= columnsNumber; i++)
			        System.out.print(rsmd.getColumnName(i) +":"+rs.getString(i) + ", ");
			    System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String err = e.getMessage();
			
			//if the query is running a query that dosent return a value like insert\delete
			if (err.contains("query does not return ResultSet"))
			{
				;//Query completed, didn't have to return value
			}
			else
			{
				e.printStackTrace();
			}
		}
	        return "";
	}
            
	/* Adding new Diver to DB */
	public void addDiver(Connection conn,String id, String firstName, String lastName, String licenseID, String email, String phone,String insurance)
	{
		String sql = "INSERT INTO Diver(id,firstName,lastName,licenseID,email,phone,insurance) VALUES(?,?,?,?,?,?,?)";//query string
		 
	        PreparedStatement pstmt;
	        //Insert the parameters to new DB record
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
			    pstmt.setString(2, firstName);
			    pstmt.setString(3, lastName);
			    pstmt.setString(4, licenseID);
			    pstmt.setString(5, email);
			    pstmt.setString(6, phone);
			    pstmt.setString(7, insurance);
			    pstmt.executeUpdate();
			     
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				//If trying to add a diver with exist ID
				if (err.contains("Abort due to constraint violation (UNIQUE constraint failed:"))
				{
					System.out.println("ID must be unique, Failed to add new diver");
				}
				else
				{
					e.printStackTrace();//printing error if happend
				}
			} 
	     
	}
	/* Add new Employee */
	public void addEmployee(Connection conn,String id, String firstName, String lastName, String seniority, String email, String phone)
	{
		String sql = "INSERT INTO Employee(employeeID,firstName,lastName,seniority,email,phone) VALUES(?,?,?,?,?,?)";
		 
	        PreparedStatement pstmt;
			try {
				//Insert data to new DB record
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
			    pstmt.setString(2, firstName);
			    pstmt.setString(3, lastName);
			    pstmt.setString(4, seniority);
			    pstmt.setString(5, email);
			    pstmt.setString(6, phone);
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
	
	/* remove diver from DB */
	public void removeDiver(Connection conn, String id)
	{
		runQuery(conn, "DELETE FROM Diver WHERE id="+id);
	}
	/* remove employee from DB */
	public void removeEmployee(Connection conn, String id)
	{
		runQuery(conn, "DELETE FROM Employee WHERE employeeID="+id);
	}
	/* get divers data and insert it to divers list to return */
	public List<Diver> getDivers()
	{
		List<Diver> res = new ArrayList<>();//creating divers list
		Statement stmt;
		try {
			/* getting all information from diver table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Diver");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Diver d;
			
			/* creating Diver object for each diver in the db table */
			while (rs.next()) {
				d = new Diver(rs.getString("licenseID"));
				d.setId(rs.getString("id"));
				d.setFirstName((rs.getString("firstName")));
				d.setLastName((rs.getString("lastName")));
				d.setEmail(rs.getString("email"));
				d.setPhone(rs.getString("phone"));
				d.setInsurance(rs.getString("insurance"));
			    res.add(d);//add diver to the list
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
	
	
	public List<Dive> getDiveBook(String id)
	{
		List<Dive> res = new ArrayList<>();//creating dives list
		Statement stmt;
		try {
			/* getting all information from dives table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Dive,Locations,Diver where diverID = "+id + " and Dive.locationID = Locations.locationID  and Diver.id = Dive.diverID order by date");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Dive d;
			SimpleDateFormat formatter = new SimpleDateFormat("dd\\MM\\yyyy");
			/* creating Dive object for each dive in the db table */
			while (rs.next()) {
				d = new Dive();
				d.setDiveNum(rs.getInt("diveNum"));
				d.setDiver((rs.getString("firstName")));
				d.setLocation((rs.getString("name")));
				d.setDate(formatter.parse(rs.getString("date")));
				d.setMaxDepth(rs.getInt("maxDepth"));
				d.setStartTime(rs.getString("startTime"));
				d.setEndTime(rs.getString("endTime"));
				d.setAirStart(rs.getInt("airStart"));
				d.setAirEnd(rs.getInt("airEnd"));
				
			    res.add(d);//add dive to the list
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
