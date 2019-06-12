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
import Classes.Employee;

public class employeeSqlQueries {

	private sqlConnection dbconnection;
	public Connection connection;
	public employeeSqlQueries()
	{
		dbconnection=sqlConnection.getInstance();
		connection = dbconnection.conn;
	}
	
	
	public List<Employee> getEmployees()
	{
		List<Employee> res = new ArrayList<>();//creating employees list
		Statement stmt;
		try {
			/* getting all information from employee table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Employee");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Employee e;
			
			/* creating Diver object for each diver in the db table */
			while (rs.next()) {
				e = new Employee(rs.getString("seniority"),rs.getDouble("salary"));
				e.setId(rs.getString("employeeID"));
				e.setFirstName((rs.getString("firstName")));
				e.setLastName((rs.getString("lastName")));
				e.setEmail(rs.getString("email"));
				e.setPhone(rs.getString("phone"));
			    res.add(e);//add diver to the list
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
	
	
	
	public Employee getEmployeeByID(String id)
	{
		
		Statement stmt;
		Employee e=null;
		try {
			/* getting all information from Employee table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Employee where employeeID ="+"\"" + id + "\"");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			/* creating Item object for each item in the db table */
			if (rs.next()) {
				e=new Employee(rs.getString("seniority"),rs.getDouble("salary"));
				e.setId(id);
				e.setFirstName(rs.getString("firstName"));
				e.setLastName(rs.getString("lastName"));
				e.setEmail(rs.getString("email"));
				e.setPhone(rs.getString("phone"));
				
			}
		} catch (SQLException er) {
			// TODO Auto-generated catch block
			String err = er.getMessage();
			if (err.contains("query does not return ResultSet"))
			{
				;//Query completed, didn't have to return value
			}
			else
			{
				er.printStackTrace();
			}
		
		}
	return e;
	}

	
	
	
	
	public String addEmployee(String id, String firstName, String lastName, String email, String phone,Double salary)
	{
		String sql = "INSERT INTO Employee(employeeID,firstName,lastName,seniority,email,phone,salary) VALUES(?,?,?,?,?,?,?)";
		 
	        PreparedStatement pstmt;
			try {
				//Insert data to new DB record
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, id);
			    pstmt.setString(2, firstName);
			    pstmt.setString(3, lastName);
			    pstmt.setString(4, "מתחיל");
			    pstmt.setString(5, email);
			    pstmt.setString(6, phone);
			    pstmt.setDouble(7, salary);
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
					e.printStackTrace();
					return "err";
				}
			} 
	     return "";
	}
	
	
	/* remove employee from DB */
	public void removeEmployee(Connection conn, String id)
	{
		sqlConnection.getInstance().runQuery(conn, "DELETE FROM Employee WHERE employeeID="+id);
	}
	
	
	
	
}
