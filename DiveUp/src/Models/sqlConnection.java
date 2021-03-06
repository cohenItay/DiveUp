package Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class sqlConnection {
	
	private static sqlConnection dbConnection;//class instance
	public static Connection conn;//connection instance
	
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
			if(!rs.next())
				return "err";
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
				return "err";
			}
			else
			{
				e.printStackTrace();
				return "err";
			}
		}
	        return "";
	}
            
	/* Adding new Diver to DB */
		/* Add new Employee */
	public void addEmployee(Connection conn,String id, String firstName, String lastName, String email, String phone,Double salary)
	{
		String sql = "INSERT INTO Employee(employeeID,firstName,lastName,seniority,email,phone,salary) VALUES(?,?,?,?,?,?,?)";
		 
	        PreparedStatement pstmt;
			try {
				//Insert data to new DB record
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
			    pstmt.setString(2, firstName);
			    pstmt.setString(3, lastName);
			    pstmt.setString(4, "מתחיל");
			    pstmt.setString(5, email);
			    pstmt.setString(6, phone);
			    pstmt.setDouble(6, salary);
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
	
	
	/* remove employee from DB */
	public void removeEmployee(Connection conn, String id)
	{
		runQuery(conn, "DELETE FROM Employee WHERE employeeID="+id);
	}
	/* get divers data and insert it to divers list to return */
	
	
	public List<Dive> getDiveBook(String id)
	{
		List<Dive> res = new ArrayList<>();//creating dives list
		Statement stmt;
		try {
			/* getting all information from dives table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Dive,Locations,Diver where diverID = "+"\"" + id + "\" " +"and Dive.locationID = Locations.locationID  and Diver.id = Dive.diverID order by date");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Dive d;
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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
	
	public List<Dive> getAllDives()
	{
		List<Dive> res = new ArrayList<>();//creating dives list
		Statement stmt;
		try {
			/* getting all information from dives table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Dive,Locations,Diver where Dive.locationID = Locations.locationID  and Diver.id = Dive.diverID order by date");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Dive d;
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			/* creating Dive object for each dive in the db table */
			while (rs.next()) {
				d = new Dive();
				d.setDiveNum(rs.getInt("diveNum"));
				d.setDiver((rs.getString("diverID")));
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

	public List<String> getLocations()
	{
		List<String> res = new ArrayList<>();
		Statement stmt;
		try {
			/* getting all information from dives table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Locations");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String location="";
			SimpleDateFormat formatter = new SimpleDateFormat("dd\\MM\\yyyy");
			/* creating Dive object for each dive in the db table */
			while (rs.next()) {
				location = rs.getString("name");
				
			    res.add(location);//add dive to the list
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
		
	
	
	public int getNewDiveID()
	{
		Statement stmt;
		try {
			/* getting all information from items table */
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Dive ORDER BY diveNum  DESC LIMIT 1");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			if (rs.next()) {
				return rs.getInt("diveNum")+1;
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
	
	public int getLocationID(String name)
	{
		Statement stmt;
		try {
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select locationID from Locations where name = "+"\"" + name + "\"");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			int locationID=1;
			SimpleDateFormat formatter = new SimpleDateFormat("dd\\MM\\yyyy");
			/* creating Dive object for each dive in the db table */
			if (rs.next()) {
				locationID = rs.getInt("locationID");
				
			    return locationID;//add dive to the list
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
	
	
	
	public String getLocationName(int id)
	{
		Statement stmt;
		try {
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select name from Locations where locationID = "+id);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String locationName="";
			/* creating Dive object for each dive in the db table */
			if (rs.next()) {
				locationName = rs.getString("name");
				
			    return locationName;//add dive to the list
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
		return "";
	}
	
	
	
	
	
	public void addDive(String diverID,String location,String date,int maxDepth,String startHour,String endHour,int startAir,int endAir)
	{
		
		String sql = "INSERT INTO Dive(diveNum,diverID,locationID,date,maxDepth,startTime,endTime,airStart,airEnd) VALUES(?,?,?,?,?,?,?,?,?)";//query string
		 
        PreparedStatement pstmt;
        //Insert the parameters to new DB record
		try {
			int id=getNewDiveID();
			if(id == -1)
			{
				id=1;
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
		    pstmt.setString(2, diverID);
		    pstmt.setInt(3,getLocationID(location));
		    pstmt.setString(4, date);
		    pstmt.setDouble(5, maxDepth);
		    pstmt.setString(6,startHour);
		    pstmt.setString(7,endHour);
		    pstmt.setInt(8,startAir);
		    pstmt.setInt(9,endAir);
		    pstmt.executeUpdate();
		     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String err = e.getMessage();
			//If trying to add a sale with exist ID
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
	
	
	
	public void updateDive(int diveID,String diverID,String location,String date,double maxDepth,String startHour,String endHour,int startAir,int endAir)
	{
		
		DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		String query = "update Dive set diverID = ?, locationID = ?, date = ? ,maxDepth = ?,startTime = ? , endTime = ?, airStart = ?, airEnd = ?  where diveNum = ?";
	    PreparedStatement preparedStmt;
		try {;
			preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setString(1, diverID);
		    preparedStmt.setInt(2, getLocationID(location));
		    preparedStmt.setString(3, date);
		    preparedStmt.setDouble(4, maxDepth);
		    preparedStmt.setString(5, startHour);
		    preparedStmt.setString(6, endHour);
		    preparedStmt.setInt(7, startAir);
		    preparedStmt.setInt(8, endAir);
		    preparedStmt.setInt(9, diveID);
		    // execute the java preparedstatement
		    preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
