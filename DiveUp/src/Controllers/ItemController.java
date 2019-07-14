package Controllers;

import java.util.List;

import Models.Item;
import Models.itemSqlQueries;

public class ItemController implements Controller {

	private itemSqlQueries dbConnection;//instance to run item queries
	
	//connection to items DB queries
	public ItemController()
	{
		dbConnection = new itemSqlQueries();
	}
	//Add new item to DB
	public void addNewItem(String name , String desc , double price, double loanPrice,int amount)
	{
		
		dbConnection.addItem(name, desc, price, loanPrice, amount);
	}
	
	//validate item fields
	public boolean validateItem(int courseID, String diverID)
	{
		return true;
	}
	
	// adding amount of item , when sending negative amount , it will decrease amount(will use for buying items)
	public void updateAmount(int itemID,int amount)
	{
		dbConnection.updateAmount(itemID,amount); 
	}
	
	//get item current amount
	public int getItemAmount(int itemID)
	{
		return dbConnection.getCurrentAmount(itemID);
	}
	
	//get item id by name
	public int getID(String name)
	{
		return dbConnection.getID(name);
	}
	public List<Item> getItems()
	{
		itemSqlQueries it = new itemSqlQueries();
		return it.getItems();
	}
	
	public int getNewItemID()
	{
		return dbConnection.getNewItemID();
	}
	public void updateItem(int id,String name,String desc,Double price,Double loanPrice,int amount)
	{
		dbConnection.updateItem(id, name, desc, price, loanPrice, amount);
	}

}

