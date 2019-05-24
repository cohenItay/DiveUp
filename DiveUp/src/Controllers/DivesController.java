package Controllers;

import java.util.List;

import Classes.Dive;
import Models.sqlConnection;

public class DivesController implements Controller{

	//Getting diver diveBook
	public List<Dive> getDivesBook(String id){
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getDiveBook(id);
	}
	
	
	

	
	

}
