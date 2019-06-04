package Controllers;

import java.util.List;

import Models.Dive;
import Managers.SqlConnection;

public class DivesController implements Controller{

	//Getting diver diveBook
	public List<Dive> getDivesBook(String id){
		SqlConnection dbConnection = SqlConnection.getInstance();
		return dbConnection.getDiveBook(id);
	}
	
	
	

	
	

}
