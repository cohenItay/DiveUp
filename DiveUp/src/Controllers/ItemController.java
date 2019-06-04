package Controllers;

import Managers.itemSqlQueries;

public class ItemController implements Controller {

	private itemSqlQueries dbConnection;//instance to run item queries
	
	//connection to items DB queries
	public ItemController()
	{
		dbConnection = new itemSqlQueries();
	}
	//Add new item to DB
	public void addNewItem()
	{
		
		
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

}

