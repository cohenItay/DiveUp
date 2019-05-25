package Controllers;

import java.util.List;

import Classes.Diver;
import Classes.Employee;
import Models.employeeSqlQueries;
import Models.itemSqlQueries;

public class EmployeeController implements Controller {

private employeeSqlQueries dbConnection;//instance to run item queries
	
	//connection to items DB queries
	public EmployeeController()
	{
		dbConnection = new employeeSqlQueries();
	}
	
	public List<Employee> getEmployees(){
		employeeSqlQueries  dbConnection = new employeeSqlQueries();//connection to the DB
		return dbConnection.getEmployees();//Getting divers list from the DB
	}
}
