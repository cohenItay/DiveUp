package Controllers;

import java.util.List;

import Models.Employee;
import Managers.EmployeeSqlQueries;

public class EmployeeController implements Controller {

private EmployeeSqlQueries dbConnection;//instance to run item queries
	
	//connection to items DB queries
	public EmployeeController()
	{
		dbConnection = new EmployeeSqlQueries();
	}
	
	public List<Employee> getEmployees(){
		EmployeeSqlQueries dbConnection = new EmployeeSqlQueries();//connection to the DB
		return dbConnection.getEmployees();//Getting divers list from the DB
	}
	
	
	public Employee getEmployeeByID(String id)
	{
		EmployeeSqlQueries dbConnection = new EmployeeSqlQueries();//connection to the DB
		return dbConnection.getEmployeeByID(id);//Getting divers list from the DB

	}
}
