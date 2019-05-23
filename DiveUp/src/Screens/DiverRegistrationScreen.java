package Screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.UIConstants;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Controllers.Controller;
import Controllers.DiverController;
import Models.diverSqlQueries;
import Models.sqlConnection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiverRegistrationScreen {

	private JFrame frame;
	private DTextField idTextField;
	private DTextField firstNameTextField;
	private DTextField lastnameTextField;
	private DTextField licenseidTextField;
	private DTextField emailTextField;
	private DTextField phoneTextField;
	private JCheckBox isProtected;
	private DiverController c;
	private DNotification not;
	private diverSqlQueries dbConnection;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creating new registration window
					DiverRegistrationScreen window = new DiverRegistrationScreen();
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
	public DiverRegistrationScreen() {
		c = new DiverController();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		/* set the size and the location of the frame */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		frame.setSize(width/2, height/2);
		frame.setLocation(screenSize.width/2-frame.getSize().width/2, screenSize.height/2-frame.getSize().height/2);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("Diver Registration");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][117.00][grow][-60.00,grow][-116.00,grow,fill][grow][]", "[][fill][fill][][][][][34.00][][grow]"));
		
		
		
		/* adding fields to the form */
		idTextField = new DTextField(20);
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idTextField, "cell 7 1 3 1,growx");
		idTextField.setColumns(10);
		idTextField.setToolTipText("הכנס ת.ז בעלת 9 ספרות");
		
		JLabel idLabel = new JLabel("\u05EA\u05E2\u05D5\u05D3\u05EA \u05D6\u05D4\u05D5\u05EA");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idLabel, "cell 10 1");
		
		firstNameTextField = new DTextField(20);
		firstNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameTextField, "cell 7 2 3 1,growx");
		firstNameTextField.setColumns(10);
		firstNameTextField.setToolTipText("הכנס שם פרטי");
		JLabel firstNameLabel = new JLabel("\u05E9\u05DD \u05E4\u05E8\u05D8\u05D9");
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameLabel, "cell 10 2");
		
		lastnameTextField = new DTextField(20);
		lastnameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameTextField, "cell 7 3 3 1,growx");
		lastnameTextField.setColumns(10);
		lastnameTextField.setToolTipText("הכנס שם משפחה");
		
		JLabel lastnameLabel = new JLabel("\u05E9\u05DD \u05DE\u05E9\u05E4\u05D7\u05D4");
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameLabel, "cell 10 3");
		
		licenseidTextField = new DTextField(20);
		licenseidTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidTextField, "cell 7 4 3 1,growx");
		licenseidTextField.setColumns(10);
		licenseidTextField.setToolTipText("הכנס רישיון צלילה");
		
		JLabel licenseidLabel = new JLabel("\u05E8\u05D9\u05E9\u05D9\u05D5\u05DF \u05E6\u05DC\u05D9\u05DC\u05D4");
		licenseidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidLabel, "cell 10 4");
		
		emailTextField = new DTextField(20);
		emailTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailTextField, "cell 7 5 3 1,growx");
		emailTextField.setColumns(10);
		emailTextField.setToolTipText("הכנס כתובת מייל");
		JLabel emailLabel = new JLabel("\u05DE\u05D9\u05D9\u05DC");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailLabel, "cell 10 5");
		
		phoneTextField = new DTextField(20);
		phoneTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneTextField, "cell 7 6 3 1,growx");
		phoneTextField.setColumns(10);
		phoneTextField.setToolTipText("הכנס מסםר פלאפון");
		
		JLabel phoneLabel = new JLabel("\u05E4\u05DC\u05D0\u05E4\u05D5\u05DF");
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneLabel, "cell 10 6");
		
		
		/* when pressing the confirm button, run query to add diver to DB */
		DButton confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			confirmButton.setBackground(UIConstants.BTN_INLINE_HOVER_DEFUALT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				confirmButton.setBackground(UIConstants.SELECTED_BTN);
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Map<Integer,String> violations = c.checkFullRegistrationForm(idTextField.getText(), 
						firstNameTextField.getText(), lastnameTextField.getText(),licenseidTextField.getText(),
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
                        if(vcode==c.licenseID_empty || vcode==c.invalid_licenseID){
                            licenseidTextField.setViolatedBorder(true);
                        }
                        //email violated
                        if(vcode==c.email_empty || vcode==c.invalid_email){
                            emailTextField.setViolatedBorder(true);
                        }
                        //phone violated
                        if(vcode==c.phone_empty || vcode==c.invalid_phone){
                            phoneTextField.setViolatedBorder(true);
                        }
                    }
                    
                    JOptionPane.showMessageDialog(null, "נא תקן את השדות המסומנים באדום", "בעיה בהרשמה " + "פרטים שגויים", JOptionPane.ERROR_MESSAGE);
                }
                else {
    				
    				dbConnection.addDiver(dbConnection.connection, idTextField.getText(), firstNameTextField.getText(), lastnameTextField.getText(),
    						licenseidTextField.getText(), emailTextField.getText(), phoneTextField.getText(),isProtected.isSelected());
    				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
    				

                }

			}
		});
		
		isProtected = new JCheckBox("");
		isProtected.setBackground(UIConstants.SELECTED_BTN);
		isProtected.setForeground(UIConstants.BTN_PRIMARY_FONT_DEFUALT);
		isProtected.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		isProtected.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(isProtected, "cell 9 7,alignx right");
		
		JLabel insuranceLabel = new JLabel("\u05D1\u05D9\u05D8\u05D5\u05D7");
		frame.getContentPane().add(insuranceLabel, "cell 10 7");
		frame.getContentPane().add(confirmButton, "cell 7 8 3 1,growx");
		frame.setVisible(true);
		idTextField.requestFocusInWindow();
		dbConnection = new diverSqlQueries();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
	}

}
