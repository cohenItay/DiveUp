package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Diver;
import Classes.Item;

public class diverSqlQueries{

	private sqlConnection dbconnection;
	public Connection connection;
	public diverSqlQueries()
	{
		dbconnection=sqlConnection.getInstance();
		connection = dbconnection.conn;
	}
	
	
	//Add diver to DB
	public String addDiver(Connection conn,String id, String firstName, String lastName, String licenseID, String email, String phone,boolean insurance)
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
			    if(insurance)
			    	pstmt.setString(7, "YES");
			    else
			    	pstmt.setString(7, "NO");
			    pstmt.executeUpdate();
			   
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String err = e.getMessage();
				//If trying to add a diver with exist ID
				if (err.contains("Abort due to constraint violation (UNIQUE constraint failed:"))
				{
					return "DUP";
				}
				else
				{
					
					e.printStackTrace();//printing error if happend
					return "err";
				}
			} 
	     return "OK";
	}
	
	/* remove diver from DB */
	public void removeDiver(Connection conn, String id)
	{
		dbconnection.runQuery(conn, "DELETE FROM Diver WHERE id="+id);
	}
	
	
	
	//Get all divers from the DB
	public List<Diver> getDivers()
	{
		List<Diver> res = new ArrayList<>();//creating divers list
		Statement stmt;
		try {
			/* getting all information from diver table */
			stmt = connection.createStatement();
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
	
	
	public Diver getDiverByID(String id)
	{
	
		Statement stmt;
		Diver d=null;
		try {
			
			/* getting all information from Diver table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Diver where id ="+id);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			/* creating Item object for each item in the db table */
			if (rs.next()) {
				d=new Diver(rs.getString("licenseID"));
				d.setId(id);
				d.setFirstName(rs.getString("firstName"));
				d.setLastName(rs.getString("lastName"));
				d.setEmail(rs.getString("email"));
				d.setPhone(rs.getString("phone"));
				d.setInsurance(rs.getString("insurance"));
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
	return d;
	}
	
	public void updateDiver(String diverID,String firstName,String lastName,String licenseID,String email,String phone,String insurance) {
		String query = "update Diver set firstName = ?, lastName = ?, licenseID = ? ,email = ?,phone = ? , insurance = ?  where id = ?";
	    PreparedStatement preparedStmt;
		try {;
			preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString(1, firstName);
		    preparedStmt.setString(2, lastName);
		    preparedStmt.setString(3, licenseID);
		    preparedStmt.setString(4, email);
		    preparedStmt.setString(5, phone);
		    preparedStmt.setString(6, insurance);
		    preparedStmt.setString(7, diverID);
		    // execute the java preparedstatement
		    preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
