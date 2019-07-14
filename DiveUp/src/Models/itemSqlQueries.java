package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



public int getNewItemID()
{
	Statement stmt;

	try {
		/* getting all information from items table */
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Item ORDER BY id  DESC LIMIT 1");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		/* creating Sale object for each item in the db table */
		if (rs.next()) {
			return rs.getInt("id")+1;
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




public void addItem(String name,String desc,double price,double loanPrice,int amount)
{
	
		String sql = "INSERT INTO Item(id,name,desc,salePrice,loanPrice,amount) VALUES(?,?,?,?,?,?)";//query string
		 
	        PreparedStatement pstmt;
	        //Insert the parameters to new DB record
			try {
				int id=getNewItemID();
				if(id == -1)
				{
					id=1;
				}
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1,id);
			    pstmt.setString(2, name);
			    pstmt.setString(3, desc);
			    pstmt.setDouble(4, price);
			    pstmt.setDouble(5, loanPrice);
			    pstmt.setInt(6,amount);
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


public void updateItem(int id,String name,String desc,Double price,Double loanPrice,int amount) {
	String query = "update Item set name = ?, desc = ?, salePrice = ? ,loanPrice = ?, amount = ?  where id = ?";
    PreparedStatement preparedStmt;
    SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
	try {
		
		
		preparedStmt = connection.prepareStatement(query);
	    preparedStmt.setString(1, name);
	    preparedStmt.setString(2, desc);
	    preparedStmt.setDouble(3, price);
	    preparedStmt.setDouble(4, loanPrice);
	    preparedStmt.setInt(5, amount);
	    preparedStmt.setInt(6, id);
	    // execute the java preparedstatement
	    preparedStmt.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

}
