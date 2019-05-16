package Screens;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Diver;


public class sqlConnection {
	
	private static sqlConnection dbConnection;
	public static Connection conn;
	
	private sqlConnection() {
		Connect();
	}
	
	public static sqlConnection getInstance() {
		if (dbConnection == null)
			dbConnection = new sqlConnection();
		return dbConnection;
	}
	
	
	public void Connect(){
	    
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\DB\\DiveUpDB.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	    
	}
	
	public String runQuery(Connection conn,String query){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
	
			while (rs.next()) {
				
			    for(int i = 1; i <= columnsNumber; i++)
			        System.out.print(rsmd.getColumnName(i) +":"+rs.getString(i) + ", ");
			    System.out.println();
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
            
	public void addDiver(Connection conn,String id, String firstName, String lastName, String licenseID, String email, String phone)
	{
		String sql = "INSERT INTO Diver(id,firstName,lastName,licenseID,email,phone) VALUES(?,?,?,?,?,?)";
		 
	        PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
			    pstmt.setString(2, firstName);
			    pstmt.setString(3, lastName);
			    pstmt.setString(4, licenseID);
			    pstmt.setString(5, email);
			    pstmt.setString(6, phone);
			    pstmt.executeUpdate();
			     
			} catch (SQLException e) {
				// TODO Auto-generated catch block
	//			e.printStackTrace();
				String err = e.getMessage();
				if (err.contains("Abort due to constraint violation (UNIQUE constraint failed:"))
				{
					System.out.println("ID must be unique, Failed to add new diver");
				}
				else
				{
					e.printStackTrace();
				}
			} 
	     
	}
	
	public void addEmployee(Connection conn,String id, String firstName, String lastName, String seniority, String email, String phone)
	{
		String sql = "INSERT INTO Employee(employeeID,firstName,lastName,seniority,email,phone) VALUES(?,?,?,?,?,?)";
		 
	        PreparedStatement pstmt;
			try {
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
	//			e.printStackTrace();
				String err = e.getMessage();
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
	
	
	public void removeDiver(Connection conn, String id)
	{
		runQuery(conn, "DELETE FROM Diver WHERE id="+id);
	}
	
	public void removeEmployee(Connection conn, String id)
	{
		runQuery(conn, "DELETE FROM Employee WHERE employeeID="+id);
	}
	
	public List<Diver> getDivers()
	{
		List<Diver> res = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Diver");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Diver d;
			
			while (rs.next()) {
				d = new Diver(rs.getString(4));
				d.setId(rs.getString(1));
				d.setFirstName((rs.getString(2)));
				d.setLastName((rs.getString(3)));
				d.setEmail(rs.getString(5));
				d.setPhone(rs.getString(6));
			    res.add(d);
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
}
