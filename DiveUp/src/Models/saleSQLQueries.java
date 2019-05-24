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
import Classes.Sale;
public class saleSQLQueries {

	
	private sqlConnection dbconnection;
	public Connection connection;
	public saleSQLQueries()
	{
		dbconnection=sqlConnection.getInstance();
		connection = dbconnection.conn;
	}
	
	//get sale id Primary key for new sale
	public int getNewSaleID()
	{
		Statement stmt;
		Item i=new Item();
		try {
			/* getting all information from items table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sale ORDER BY saleID  DESC LIMIT 1");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			/* creating Sale object for each item in the db table */
			if (rs.next()) {
				return rs.getInt("saleID")+1;
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
	//add sale to DB
	public void addSale(String diverID,String itemsList,String date,double totalPrice)
	{
		
			String sql = "INSERT INTO Sale(saleID,diverID,itemsList,date,totalPrice) VALUES(?,?,?,?,?)";//query string
			 
		        PreparedStatement pstmt;
		        //Insert the parameters to new DB record
				try {
					int id=getNewSaleID();
					if(id == -1)
					{
						id=1;
					}
					pstmt = connection.prepareStatement(sql);
					pstmt.setInt(1,id);
				    pstmt.setString(2, diverID);
				    pstmt.setString(3, itemsList);
				    pstmt.setString(4, date);
				    pstmt.setDouble(5, totalPrice);
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
	
	//get sales from database
	public List<Sale> getSales()
	{
		List<Sale> res = new ArrayList<>();//creating sales list
		Statement stmt;
		try {
			/* getting all information from sales table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Sale");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Sale s;
			
			/* creating sale object for each sale in the db table */
			while (rs.next()) {
				s = new Sale();
				s.setSaleID(rs.getInt("saleID"));
				s.setDiverID(rs.getString("diverID"));
				s.setItems(rs.getString("itemsList"));
				s.setDate(rs.getString("date"));
				s.setTotalPrice(rs.getDouble("totalPrice"));
				res.add(s);//add sale to the list
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

//get customer sales by id
	public List<Sale> getCustomerSales(String id)
	{
		List<Sale> res = new ArrayList<>();//creating sales list
		Statement stmt;
		try {
			/* getting all information from sales table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Sale where diverID = "+"\"" + id + "\"");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Sale s;
			
			/* creating sale object for each sale in the db table */
			while (rs.next()) {
				s = new Sale();
				s.setSaleID(rs.getInt("saleID"));
				s.setDiverID(rs.getString("diverID"));
				s.setItems(rs.getString("itemsList"));
				s.setDate(rs.getString("date"));
				s.setTotalPrice(rs.getDouble("totalPrice"));
				res.add(s);//add sale to the list
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
