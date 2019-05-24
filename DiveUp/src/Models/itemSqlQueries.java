package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Classes.Course;
import Classes.Item;

public class itemSqlQueries {

	private sqlConnection dbconnection;
	public Connection connection;
	public itemSqlQueries()
	{
		dbconnection=sqlConnection.getInstance();
		connection = dbconnection.conn;
	}
	

	
	//Get all items from the DB
	public List<Item> getItems()
	{
		List<Item> res = new ArrayList<>();//creating items list
		Statement stmt;
		try {
			/* getting all information from items table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Item");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			Item i;
			/* creating Item object for each item in the db table */
			while (rs.next()) {
				i = new Item();
				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setDesc(rs.getString("desc"));
				i.setPrice(rs.getDouble("salePrice"));
				i.setLoanPrice(rs.getDouble("loanPrice"));
				i.setAmount(rs.getInt("amount"));
				res.add(i);//add item to the list
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
