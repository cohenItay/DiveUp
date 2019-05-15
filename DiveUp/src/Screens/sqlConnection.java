package Screens;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlConnection {
	
	private static sqlConnection instance;
	public static Connection conn;
	
	private sqlConnection() {}
	
	public static sqlConnection getInstance() {
		if (instance == null)
			instance = new sqlConnection();
		return instance;
	}
	
	
	public Connection Connect(){
	    
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\DB\\DiveUpDB.db");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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
	
}
