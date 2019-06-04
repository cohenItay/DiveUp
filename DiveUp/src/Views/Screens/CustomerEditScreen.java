package Views.Screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Models.Diver;
import Controllers.DiverController;
import Managers.DiverSqlQueries;
import net.miginfocom.swing.MigLayout;
import Views.DButton;
import Views.DNotification;
import Views.DTextField;
import Views.DTextPane;
import Views.UIConstants;
import java.awt.Font;

public class CustomerEditScreen {

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
	private DiverSqlQueries dbConnection;
	private Diver diver;
	private DTextPane jtp;
	private Document doc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerEditScreen window = new CustomerEditScreen(null);
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
	public CustomerEditScreen(Diver d) {
		this.diver = d;
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
		    JOptionPane.showMessageDialog(null, jtp, titleBar, JOptionPane.ERROR_MESSAGE);
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
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 30));
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("עדכון פרטי לקוח");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/Resources/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][20%,fill][20%:20%,fill][20px:n][][130px:n][80px][80px:n][100px:n]", "[][40px:n][::40px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][::35px][::20px][40px:n]"));
		
		
		
		/* adding fields to the form */
		
		JLabel titleLabel = new JLabel("עריכה");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 5 0");
		idTextField = new DTextField(20);
		idTextField.setEditable(false);
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idTextField, "cell 5 3,grow");
		idTextField.setColumns(10);
		idTextField.setText(diver.getId());
		idTextField.setForeground(Color.LIGHT_GRAY);
		JLabel idLabel = new JLabel("\u05EA\u05E2\u05D5\u05D3\u05EA \u05D6\u05D4\u05D5\u05EA");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(idLabel, "cell 7 3,alignx right");
		
		firstNameTextField = new DTextField(20);
		firstNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameTextField, "cell 5 5,grow");
		firstNameTextField.setColumns(10);
		firstNameTextField.setText(diver.getFirstName());
		JLabel firstNameLabel = new JLabel("\u05E9\u05DD \u05E4\u05E8\u05D8\u05D9");
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(firstNameLabel, "cell 7 5,alignx right");
		
		lastnameTextField = new DTextField(20);
		lastnameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameTextField, "cell 5 7,grow");
		lastnameTextField.setColumns(10);
		lastnameTextField.setText(diver.getLastName());
		
		JLabel lastnameLabel = new JLabel("\u05E9\u05DD \u05DE\u05E9\u05E4\u05D7\u05D4");
		lastnameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(lastnameLabel, "cell 7 7,alignx right");
		
		licenseidTextField = new DTextField(20);
		licenseidTextField.setEditable(false);
		licenseidTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidTextField, "cell 5 9,grow");
		licenseidTextField.setColumns(10);
		licenseidTextField.setText(diver.getLicenseID());
		licenseidTextField.setForeground(Color.LIGHT_GRAY);
		JLabel licenseidLabel = new JLabel("\u05E8\u05D9\u05E9\u05D9\u05D5\u05DF \u05E6\u05DC\u05D9\u05DC\u05D4");
		licenseidLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		licenseidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		licenseidLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(licenseidLabel, "cell 7 9,alignx right");
		
		emailTextField = new DTextField(20);
		emailTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailTextField, "cell 5 11,grow");
		emailTextField.setColumns(10);
		emailTextField.setText(diver.getEmail());
		JLabel emailLabel = new JLabel("\u05DE\u05D9\u05D9\u05DC");
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(emailLabel, "cell 7 11,alignx right");
		
		phoneTextField = new DTextField(20);
		phoneTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneTextField, "cell 5 13,grow");
		phoneTextField.setColumns(10);
		phoneTextField.setText(diver.getPhone());
		
		JLabel phoneLabel = new JLabel("\u05E4\u05DC\u05D0\u05E4\u05D5\u05DF");
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(phoneLabel, "cell 7 13,alignx right");
		
		
		/* when pressing the confirm button, run query to add diver to DB */
		
		isProtected = new JCheckBox("");
		isProtected.setBackground(Color.WHITE);
		isProtected.setForeground(UIConstants.BTN_PRIMARY_FONT_DEFUALT);
		isProtected.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		isProtected.setHorizontalAlignment(SwingConstants.RIGHT);
		if(diver.getInsurance()!=null)
			if(diver.getInsurance().equals("YES") || diver.getInsurance().equals("yes"))
				isProtected.setSelected(true);
			else
				isProtected.setSelected(false);
		else
			isProtected.setSelected(false);
		frame.getContentPane().add(isProtected, "cell 5 15,alignx right,growy");
		
		JLabel insuranceLabel = new JLabel("\u05D1\u05D9\u05D8\u05D5\u05D7");
		insuranceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		insuranceLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(insuranceLabel, "cell 7 15,alignx right");
		DButton confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
		confirmButton.setText("עדכן");
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
                    
                    errorMessage("נא תקן את השדות המסומנים באדום", "פרטים שגויים");
                }
                else {
    				String insurance = "";
    				if(isProtected.isSelected())
    					insurance = "YES";
    				else
    					insurance = "NO";
    				dbConnection.updateDiver(idTextField.getText(), firstNameTextField.getText(), lastnameTextField.getText(),
    						licenseidTextField.getText(), emailTextField.getText(), phoneTextField.getText(),insurance);
    				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
    				

                }

			}
		});
		frame.getContentPane().add(confirmButton, "cell 5 17,grow");
		frame.setVisible(true);
		idTextField.requestFocusInWindow();
		dbConnection = new DiverSqlQueries();
		c = new DiverController();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
		
	}


}
