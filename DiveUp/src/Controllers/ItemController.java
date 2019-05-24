package Controllers;

import Models.itemSqlQueries;

public class ItemController implements Controller {

	
	//Add new item to DB
	public void addNewItem()
	{
		
		itemSqlQueries dbConnection = new itemSqlQueries();
		
	}
	
	//validate item fields
	public boolean validateItem(int courseID, String diverID)
	{
		return true;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}

