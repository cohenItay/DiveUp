package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import Models.Diver;
import Models.DiverRegistrationModel;
import Models.diverSqlQueries;
import Models.sqlConnection;
import Utilities.SendEmailTLS;
import Views.DiverRegistrationScreen;
import res.DNotification;

public class DiverController implements Controller {

	private diverSqlQueries dbConnection = new diverSqlQueries();
	private DiverRegistrationScreen screen = null;
	private DiverRegistrationModel model = new DiverRegistrationModel();
	
	public void start() {
		this.screen = new DiverRegistrationScreen();
		screen.initialize();
		screen.confirmButton.addActionListener(new OnActionImplementation());
	}

	//getting divers list from DB
	public List<Diver> getDivers(){
		return dbConnection.getDivers();//Getting divers list from the DB
	}

	//Checking firstname validity
	public String checkNameValidity(String name) {

		char[] charArray = name.toCharArray();
		for (char c : charArray) { //checks each letter of the name
			if (!(c <= 0x05ea && c >= 0x05d0) && !(name.matches("^[a-zA-Z]+$"))) { //checks that the name contains only letters in hebrew or english
				return "NO";
			}
		}
		if (name == "") //if no name was entered
			return "NO";
		return "VALID";

	}


	//checking lastname validity
	public String checkLastNameValidity(String lastName) {

		char[] charArray = lastName.toCharArray();
		for (char c : charArray) {
			if (!(c <= 0x05ea && c >= 0x05d0) && !(lastName.matches("^[a-zA-Z]+$"))) {
				return "NO";
			}
		}
		if (lastName == "")
			return "NO";
		return "VALID";

	}


	//checking mail validity
	public String checkEmailValidity(String mail) {

		if (mail.matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			return "VALID";
		else
			return "NO";
	}

	//checking id validity
	public String checkIDValidity(String id) {
		if (id.matches("^\\d{9}$")) //check that the ID has 9 digits
			return "VALID";
		else
			return "NO";
	}

	//checking phone validty
	public String checkPhoneValidity(String id) {
		if (id.matches("^\\d{10}$")) //check that the phone number has 10 digits
			return "VALID";
		else
			return "NO";
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
		dbConnection.updateDiver(diverID, firstName, lastName, licenseID, email, phone, insurance);
	}

	private class OnActionImplementation implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			Map<Integer,String> violations = checkFullRegistrationForm(
					screen.idTextField.getText(), 
					screen.firstNameTextField.getText(), 
					screen.lastnameTextField.getText(),
					screen.licenseidTextField.getText(),
					screen.emailTextField.getText(),
					screen.phoneTextField.getText()
					);


			if(violations.size()>0){
				//                c.showViolationNotification(violations);

				for(Integer vcode:violations.keySet()){

					//if id violated
					if(vcode==id_empty || vcode==invalid_id){
						screen.idTextField.setViolatedBorder(true);
					}

					//if firstname violated
					if(vcode==firstName_empty || vcode==invalid_firstname){
						screen.firstNameTextField.setViolatedBorder(true);
					}
					//if lastname violated
					if(vcode==lastName_empty || vcode==invalid_lastname){
						screen.lastnameTextField.setViolatedBorder(true);
					}

					//if licenseID violated
					if(vcode==licenseID_empty || vcode==invalid_licenseID){
						screen.licenseidTextField.setViolatedBorder(true);
					}
					//email violated
					if(vcode==email_empty || vcode==invalid_email){
						screen.emailTextField.setViolatedBorder(true);
					}
					//phone violated
					if(vcode==phone_empty || vcode==invalid_phone){
						screen.phoneTextField.setViolatedBorder(true);
					}
				}
				screen.errorMessage("אנא תקן את השדות המסומנים באדום", "פרטים שגויים");

			}
			else {
				model = new DiverRegistrationModel(
						screen.firstNameTextField.getText(), 
						screen.lastnameTextField.getText(),
						screen.idTextField.getText(), 
						screen.licenseidTextField.getText(), 
						screen.emailTextField.getText(),
						screen.phoneTextField.getText(),
						screen.isProtected.isSelected()
				);
				String print = "";
				String printSecondary = "";
				String status = addDiverToDB();
				if (status.contentEquals("DUP")) {
					print = "קיים כבר משתמש";
					printSecondary = "ההרשמה נכשלה";	
				}else if (status.contentEquals("OK")) {
					print = "הלקוח נרשם בהצלחה";
					printSecondary = "הרשמה התבצעה";	
					SendEmailTLS se = new SendEmailTLS(
							model.getEmail(), 
							"ברוך הבא למועדון הצלילה DiveUp",
							model.getFirstName()+" "+model.getLastName()+"\nתודה שהצטרפת למועדון הצלילה מספר אחת בארץ"
					);
				} else {
					print = "קיים כבר משתמש";
					printSecondary = "ההרשמה נכשלה";	
				} 
				screen.message(print, printSecondary);
				screen.frame.dispatchEvent(new WindowEvent(screen.frame, WindowEvent.WINDOW_CLOSING));//close window
			}
		}	
	}


	public String addDiverToDB() {
		return dbConnection.addDiver(
				dbConnection.connection,
				model.getSsn(), 
				model.getFirstName(), 
				model.getLastName(),
				model.getDiverID(), 
				model.getEmail(),
				model.getPhone(),
				model.isHasInsurance()
		);
	}
}
