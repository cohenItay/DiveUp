package Managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Models.Item;

public class ItemSqlQueries extends BaseSqlQuery {

	public ItemSqlQueries()
	{
		super();
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
	
	//get all item information by id
	public Item getItemByID(int id)
	{
	
		Statement stmt;
		Item i=new Item();
		try {
			/* getting all information from items table */
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Item where Item.id = "+id);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			/* creating Item object for each item in the db table */
			if (rs.next()) {
				i = new Item();
				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setDesc(rs.getString("desc"));
				i.setPrice(rs.getDouble("salePrice"));
				i.setLoanPrice(rs.getDouble("loanPrice"));
				i.setAmount(rs.getInt("amount"));
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
	return i;
	}
	
//get item current amount
public int getCurrentAmount(int itemID)
{
	Statement stmt;
	try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select amount from Item where id = "+itemID);
		return rs.getInt("amount");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		return -1;
	
	}
}

//get item id by name
public int getID(String name)
{
	Statement stmt;
	try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select id from Item where name = "+"\"" + name + "\"");
		return rs.getInt("id");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		return -1;
	
	}
}

//update item amount
public void updateAmount(int itemID, int amount) {
	String query = "update Item set amount = ? where id = ?";
    PreparedStatement preparedStmt;
	try {
		preparedStmt = connection.prepareStatement(query);
	    preparedStmt.setInt(1, getCurrentAmount(itemID)-amount);
	    preparedStmt.setInt(2, itemID);
	    // execute the java preparedstatement
	    preparedStmt.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


}
