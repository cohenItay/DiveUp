package Controllers;

import Models.itemSqlQueries;

public class ItemController implements Controller {

	private itemSqlQueries dbConnection;
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
	
	public void updateAmount(int itemID,int amount)
	{
		dbConnection.updateAmount(itemID,amount); 
	}
	public int getItemAmount(int itemID)
	{
		return dbConnection.getCurrentAmount(itemID);
	}
	
	public int getID(String name)
	{
		return dbConnection.getID(name);
	}

}

