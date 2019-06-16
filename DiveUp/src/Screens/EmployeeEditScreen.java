package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Classes.Employee;
import Controllers.DiverController;
import Controllers.EmployeeController;
import Models.SendEmailTLS;
import Models.diverSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;

public class EmployeeEditScreen {

	private JFrame frame;
	private Employee e;
	private DTextField idTextField;
	private DTextField firstNameTextField;
	private DTextField lastnameTextField;
	private DTextField emailTextField;
	private DTextField phoneTextField;
	private DiverController c;
	private EmployeeController ec;
	private DNotification not;
	private diverSqlQueries dbConnection;
	private DTextPane jtp;
	private Document doc;
	private JComboBox seniorityComboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeEditScreen window = new EmployeeEditScreen(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeEditScreen(Employee e) {
		this.e = e;
		ec= new EmployeeController();
		c= new DiverController();
		initialize();
	}

	public void message(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
		
	    doc = jtp.getStyledDocument();
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp, titleBar, JOptionPane.INFORMATION_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	public void errorMessage(String infoMessage, String titleBar)
    {
		jtp = new DTextPane();
	    doc = jtp.getDocument();
	    SimpleAttributeSet right = new SimpleAttributeSet();
	    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
	    ((StyledDocument) doc).setParagraphAttributes(0, doc.getLength(), right, false);
	    try {
			doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
		    JOptionPane.showMessageDialog(null, jtp,titleBar, JOptionPane.ERROR_MESSAGE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("עריכת  עובד");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][20%,fill][20%:20%,grow,fill][20px:n][][130px:n][80px][80px:n][100px:n]", "[][40px:n][::40px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][::35px][::20px][40px:n]"));
		
		
		
		/* adding fields to the form */
		
		JLabel titleLabel = new JLabel("עריכת עובד");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 5 0");
		idTextField = new DTextField(20);
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idTextField, "cell 5 3,grow");
		idTextField.setColumns(10);
		idTextField.setEditable(false);
		idTextField.setEnabled(false);
		JLabel idLabel = new JLabel("\u05EA\u05E2\u05D5\u05D3\u05EA \u05D6\u05D4\u05D5\u05EA");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(idLabel, "cell 7 3,alignx right");
		
		firstNameTextField = new DTextField(20);
		firstNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameTextField, "cell 5 5,grow");
		firstNameTextField.setColumns(10);
		firstNameTextField.setToolTipText("הכנס שם פרטי");
		JLabel firstNameLabel = new JLabel("\u05E9\u05DD \u05E4\u05E8\u05D8\u05D9");
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(firstNameLabel, "cell 7 5,alignx right");
		
		lastnameTextField = new DTextField(20);
		lastnameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameTextField, "cell 5 7,grow");
		lastnameTextField.setColumns(10);
		lastnameTextField.setToolTipText("הכנס שם משפחה");
		
		JLabel lastnameLabel = new JLabel("\u05E9\u05DD \u05DE\u05E9\u05E4\u05D7\u05D4");
		lastnameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(lastnameLabel, "cell 7 7,alignx right");
		
		emailTextField = new DTextField(20);
		emailTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailTextField, "cell 5 9,grow");
		emailTextField.setColumns(10);
		emailTextField.setToolTipText("הכנס כתובת מייל");
		JLabel emailLabel = new JLabel("\u05DE\u05D9\u05D9\u05DC");
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(emailLabel, "cell 7 9,alignx right");
		
		phoneTextField = new DTextField(20);
		phoneTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneTextField, "cell 5 11,grow");
		phoneTextField.setColumns(10);
		phoneTextField.setToolTipText("הכנס מסםר פלאפון");
		
		JLabel phoneLabel = new JLabel("\u05E4\u05DC\u05D0\u05E4\u05D5\u05DF");
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(phoneLabel, "cell 7 11,alignx right");
		
		DTextField salaryTextField = new DTextField(20);
		salaryTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(salaryTextField, "cell 5 13,growx");
		
		JLabel salaryLabel = new JLabel("משכורת");
		salaryLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		salaryLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(salaryLabel, "cell 7 13,alignx right");
		DButton confirmButton = new DButton("עדכן",DButton.Mode.PRIMARY);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Map<Integer,String> violations = c.checkFullRegistrationForm(idTextField.getText(), 
						firstNameTextField.getText(), lastnameTextField.getText(),idTextField.getText(),
						emailTextField.getText(),phoneTextField.getText());


                if(violations.size()>0){
//                    c.showViolationNotification(violations);

                    for(Integer vcode:violations.keySet()){

                        //if id violated
                        if(vcode==c.id_empty || vcode==c.invalid_id){
                            idTextField.setViolatedBorder(true);
                        }

                        //if firstname violated
                        if(vcode==c.firstName_empty || vcode==c.invalid_firstname){
                            firstNameTextField.setViolatedBorder(true);
                        }
                        //if lastname violated
                        if(vcode==c.lastName_empty || vcode==c.invalid_lastname){
                            lastnameTextField.setViolatedBorder(true);
                        }
                        
                        //if licenseID violated
                        
                        //email violated
                        if(vcode==c.email_empty || vcode==c.invalid_email){
                            emailTextField.setViolatedBorder(true);
                        }
                        //phone violated
                        if(vcode==c.phone_empty || vcode==c.invalid_phone){
                            phoneTextField.setViolatedBorder(true);
                        }
                        try {
                        	Double salary = Double.valueOf(salaryTextField.getText());
                        }
                        catch(Exception e){
                        	salaryTextField.setViolatedBorder(true);
                        }
                    }
                    errorMessage("נא תקן את השדות המסומנים באדום", "פרטים שגויים");
                    
                }
                else {
    				
    				ec.updateEmployee(idTextField.getText(), firstNameTextField.getText(), lastnameTextField.getText(),
    						 emailTextField.getText(), phoneTextField.getText(),Double.valueOf(salaryTextField.getText()),seniorityComboBox.getSelectedItem().toString());
    				
    					message("פרטי העובד עודכנו בהצלחה", "הפעולה התבצעה");
        				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
        				
    				}
    				

                

			}
		});
		
		seniorityComboBox = new JComboBox();
		seniorityComboBox.addItem("רגיל");
		seniorityComboBox.addItem("מדריף");
		seniorityComboBox.addItem("מנהל משמרת");
		seniorityComboBox.addItem("מנהל");
		((JLabel)seniorityComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		frame.getContentPane().add(seniorityComboBox, "cell 5 15,growx");
		
		JLabel seniorityLabel = new JLabel("ותק");
		seniorityLabel.setForeground(new Color(112, 112, 112));
		seniorityLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(seniorityLabel, "cell 7 15,alignx right");
		frame.getContentPane().add(confirmButton, "cell 5 17,grow");
		
		idTextField.requestFocusInWindow();
		dbConnection = new diverSqlQueries();
		
		idTextField.setText(e.getId());
		firstNameTextField.setText(e.getFirstName());
		lastnameTextField.setText(e.getLastName());
		emailTextField.setText(e.getEmail());
		phoneTextField.setText(e.getPhone());
		salaryTextField.setText(String.valueOf(e.getSalary()));
		seniorityComboBox.setSelectedItem(e.getSeniority());
		
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
		frame.setVisible(true);
}
}
