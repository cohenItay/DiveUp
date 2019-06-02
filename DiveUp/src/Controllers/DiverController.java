package Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import Classes.Diver;
import Models.diverSqlQueries;
import Models.sqlConnection;
import res.DNotification;

public class DiverController implements Controller {

	
	//getting divers list from DB
	public List<Diver> getDivers(){
		diverSqlQueries  dbConnection = new diverSqlQueries();//connection to the DB
		return dbConnection.getDivers();//Getting divers list from the DB
	}
	
	//Checking firstname validity
	public String checkNameValidity(String name) {

		char[] charArray = name.toCharArray();
		for (char c : charArray) { //checks each letter of the name
			if (!(c <= 0x05ea && c >= 0x05d0) && !(name.matches("^[a-zA-Z]+$"))) { //checks that the name contains only letters in hebrew or english
				return "Name should contain only letters";
			}
		}
		if (name == "") //if no name was entered
			return "Name cannot be empty";
		return "VALID";

	}

	
	//checking lastname validity
	public String checkLastNameValidity(String lastName) {

		char[] charArray = lastName.toCharArray();
		for (char c : charArray) {
			if (!(c <= 0x05ea && c >= 0x05d0) && !(lastName.matches("^[a-zA-Z]+$"))) {
				return "Lastname should contain only letters";
			}
		}
		if (lastName == "")
			return "Lastname cannot be empty";
		return "VALID";

	}

	
	//checking mail validity
	public String checkEmailValidity(String mail) {

		if (mail.matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			return "VALID";
		else
			return "Illegal mail address";
	}

	//checking id validity
	public String checkIDValidity(String id) {
		if (id.matches("^\\d{9}$")) //check that the ID has 9 digits
			return "VALID";
		else
			return "ID should be 9 digits";
	}

	//checking phone validty
	public String checkPhoneValidity(String id) {
		if (id.matches("^\\d{10}$")) //check that the phone number has 10 digits
			return "VALID";
		else
			return "Phone should be 10 digits";
	}

	//check all fields validity 
	public Map<Integer,String> checkFullRegistrationForm(String id, String firstName, String lastName, String licenseID,String mail, String phone) {
		
		Map<Integer,String> violations=new HashMap<>();


        //check if valid id
        if(id==null || id.equals("")){
            violations.put(id_empty,"id is empty");
            
        }
        if(!checkIDValidity(id).equals("VALID"))
		{
			violations.put(invalid_id, "Invalid ID");
		}
        
        
        //Check if firstname is valid
        if(firstName==null || firstName.equals("")){
            violations.put(firstName_empty,"First name is empty");
        }
        
        
        if(!checkNameValidity(firstName).equals("VALID"))
		{
			violations.put(invalid_firstname, "Invalid Firstname");
		}
        
        
        
        // check if lastename is valid
        
        if(lastName==null || lastName.equals("")){
            violations.put(lastName_empty,"Lastname is empty");
        }
        
        
        if(!checkLastNameValidity(lastName).equals("VALID"))
		{
			violations.put(invalid_lastname, "Invalid lastname");
		}
        
            
        //check if valid license id
        if(licenseID==null || licenseID.equals("")){
            violations.put(licenseID_empty,"LicenseID is empty");
        }
        
        if(!checkIDValidity(licenseID).equals("VALID"))
		{
			violations.put(invalid_licenseID, "Invalid licenseID");
		}
        
        
        // check if mail is valid
        if(mail==null || mail.equals("")){
            violations.put(email_empty,"mail is empty");
        }
        
        if(!checkEmailValidity(mail).equals("VALID"))
		{
			violations.put(invalid_email, "Invalid email");
		}
        
        
        //check if phone is valid
        if(phone==null || phone.equals("")){
            violations.put(phone_empty,"phone is empty");
        }
        
        if(!checkPhoneValidity(phone).equals("VALID"))
		{
			violations.put(invalid_phone, "Invalid phone");
		}
            
		return violations;
	}

	

	public Diver getDiverByID(String id)
	{
		diverSqlQueries  dbConnection = new diverSqlQueries();//connection to the DB
		return dbConnection.getDiverByID(id);//Getting divers list from the DB

	}
	
	
	public void updateDiver(String diverID,String firstName,String lastName,String licenseID,String email,String phone,String insurance) {
		
		diverSqlQueries dbConnection = new diverSqlQueries();
		dbConnection.updateDiver(diverID, firstName, lastName, licenseID, email, phone, insurance);
	}
	
	
}
