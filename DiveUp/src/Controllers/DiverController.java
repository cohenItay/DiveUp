package Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import res.DNotification;

public class DiverController implements Controller {
	Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
	private Notifier    notifier;
	
	
	public String checkNameValidity(String name) {

		char[] charArray = name.toCharArray();
		for (char c : charArray) {
			if (!(c <= 0x05ea && c >= 0x05d0) && !(name.matches("^[a-zA-Z]+$"))) {
				return "Name should contain only letters";
			}
		}
		if (name == "")
			return "Name cannot be empty";
		return "VALID";

	}

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

	public String checkEmailValidity(String mail) {

		if (mail.matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			return "VALID";
		else
			return "Illegal mail address";
	}

	public String checkIDValidity(String id) {
		if (id.matches("^\\d{9}$"))
			return "VALID";
		else
			return "ID should be 9 digits";
	}

	public String checkPhoneValidity(String id) {
		if (id.matches("^\\d{10}$"))
			return "VALID";
		else
			return "Phone should be 10 digits";
	}

	public Map<Integer,String> checkFullRegistrationForm(String id, String firstName, String lastName, String licenseID,String mail, String phone) {
		
		Map<Integer,String> violations=new HashMap<>();


        //check if source name empty
        if(id==null || id.equals("")){
            violations.put(id_empty,"id is empty");
        }
        
        if(firstName==null || firstName.equals("")){
            violations.put(firstName_empty,"First name is empty");
        }
        
        if(lastName==null || lastName.equals("")){
            violations.put(lastName_empty,"Lastname is empty");
        }
            
        if(licenseID==null || licenseID.equals("")){
            violations.put(licenseID_empty,"LicenseID is empty");
        }
        
        if(mail==null || mail.equals("")){
            violations.put(email_empty,"mail is empty");
        }
        
        if(phone==null || phone.equals("")){
            violations.put(phone_empty,"phone is empty");
        }
            
		return violations;
	}

	
	
	 public void showViolationNotification(Map<Integer,String> violations){

	        StringBuilder violationsStr=new StringBuilder("");

	        for(String violation :violations.values()){
	            if(!violationsStr.toString().equals("")){
	                violationsStr.append(", ");
	            }
	            violationsStr.append(violation);
	        }
//	        NotifierImpl notfier=new NotifierImpl();
	        notifier.show(new DNotification("Error saving source data",violationsStr.toString(),DNotification.Type.ERROR,true));
	    }
	 
	 public void cleanViolationNotification(){
	        notifier.clean();
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
