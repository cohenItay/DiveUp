package Controllers;

import java.util.List;

import Classes.Dive;
import Models.sqlConnection;

public class DivesController implements Controller{

	public List<Dive> getDivesBook(String id){
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getDiveBook(id);
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
