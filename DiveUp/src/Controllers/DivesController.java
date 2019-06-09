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
	
	public List<Dive> getAllDives()
	{
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getAllDives();
	}
	
	public List<String> getLocations()
	{
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getLocations();
	}
	
	public void addDive(String diverID,String location,String date,int maxDepth,String startHour,String endHour,int startAir,int endAir)
	{
		sqlConnection dbConnection = sqlConnection.getInstance();
		dbConnection.addDive(diverID, location, date, maxDepth, startHour, endHour, startAir, endAir);
	}

	
	public int getLocationID(String name) {
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getLocationID(name);
	}
	
	public String getLocationName(int id) {
		sqlConnection dbConnection = sqlConnection.getInstance();
		return dbConnection.getLocationName(id);
	}
	
	

}
