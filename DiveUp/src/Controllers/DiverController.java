package Controllers;

import java.util.regex.Pattern;

public class DiverController implements Controller{
	Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

	
	public String checkNameValidity(String name)
	{
		
		char[] charArray = name.toCharArray();
        for (char c : charArray) {
            System.out.println(c);
        	if (!((c <= 0x05ea && c >= 0x05d0) && !(name.matches("^[a-zA-Z]+$")))) {
            	return "Name should contain only letters";
            }
        }
        if(name =="")
        	return "Name cannot be empty";
        return "VALID";
			
	}
	
	public String checkLastNameValidity(String lastName)
	{
		
		char[] charArray = lastName.toCharArray();
        for (char c : charArray) {
        	if (!((c <= 0x05ea && c >= 0x05d0) && !(lastName.matches("^[a-zA-Z]+$"))))  {
            	return "Lastname should contain only letters";
            }
        }
        if(lastName == "")
        	return "Lastname cannot be empty";
        return "VALID";
			
	}
	
	
	public String checkEmailValidity(String mail)
	{
		
		if(mail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			return "VALID";
		else
			return "Illegal mail address";
	}
	
	public String checkIDValidity(String id)
	{
		if(id.matches("^\\d{9}$"))
			return "VALID";
		else
			return "ID should be 9 digits";
	}
}
