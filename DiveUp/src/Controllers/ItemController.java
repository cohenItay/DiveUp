package Controllers;

import Models.courseSqlQueries;
import Models.itemSqlQueries;

public class ItemController implements Controller {

	public void addNewItem()
	{
		
		itemSqlQueries dbConnection = new itemSqlQueries();
		
	}
	
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

