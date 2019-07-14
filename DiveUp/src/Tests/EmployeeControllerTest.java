package Tests;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.EmployeeController;
import Models.employeeSqlQueries;




public class EmployeeControllerTest extends BaseTest {

	static EmployeeController junit;
	//emp=new employeeSqlQueries();
	@BeforeClass
	public static void setUpBeforeClass()
	{
		junit = new EmployeeController();
	}
	
	@Test
	public void testAddEmploye() {
		
		employeeSqlQueries esql = new employeeSqlQueries();
		esql.removeEmployee(esql.connection, notExistingEmployeeID);
		
		String res = junit.addEmployee(
				notExistingEmployeeID,
				firstName,
				lastName,
				validMail,
				validPhone,
				validSalary,
				validSeniority); 
		assertEquals("", res); // empty string is success
		
		esql.removeEmployee(esql.connection, notExistingEmployeeID);
		
		res = junit.addEmployee(
				existingEmployeeID,
				firstName,
				lastName,
				validMail,
				validPhone,
				validSalary,
				validSeniority); 
		assertEquals("DUP", res);

		esql.removeEmployee(esql.connection, longerThen9NumsEmployeeID);

		res = junit.addEmployee(
				longerThen9NumsEmployeeID,
				firstName,
				lastName,
				validMail,
				validPhone,
				validSalary,
				validSeniority); 
		
		assertEquals("err", res);
		
		
		
	}
	
	@Test
	public void testCheckSalaryValidity() {
		String res = junit.addEmployee(
				existingEmployeeID,
				firstName,
				lastName,
				validMail,
				validPhone,
				notvalidSalary,
				validSeniority); //res= "DUP"/"err"/""
		
		assertEquals("DUP", res);
	
	}
	
	
	
	

}
