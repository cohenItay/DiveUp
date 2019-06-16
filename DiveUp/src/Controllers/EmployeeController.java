package Controllers;

import java.sql.Connection;
import java.util.List;

import Classes.Diver;
import Classes.Employee;
import Models.diverSqlQueries;
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
	
	
	public Employee getEmployeeByID(String id)
	{
		employeeSqlQueries  dbConnection = new employeeSqlQueries();//connection to the DB
		return dbConnection.getEmployeeByID(id);//Getting divers list from the DB

	}
	public String addEmployee(String id, String firstName, String lastName, String email, String phone,Double salary,String seniority)
	{
		employeeSqlQueries dbConnection = new employeeSqlQueries();
		return dbConnection.addEmployee(id, firstName, lastName, email, phone, salary,seniority);
	}
	public void updateEmployee(String id, String firstName, String lastName, String email, String phone,Double salary,String seniority)
	{
		employeeSqlQueries dbConnection = new employeeSqlQueries();
		dbConnection.updateEmployee(id, firstName, lastName, email, phone,salary,seniority);
	}
}
