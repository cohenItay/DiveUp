package Views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Controllers.Controller;
import Controllers.DiverController;
import Models.diverSqlQueries;
import Models.sqlConnection;
import Utilities.SendEmailTLS;

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

	public DTextField idTextField;
	public DTextField firstNameTextField;
	public DTextField lastnameTextField;
	public DTextField licenseidTextField;
	public DTextField emailTextField;
	public DTextField phoneTextField;
	public JCheckBox isProtected;
	public DButton confirmButton;
	public JFrame frame;
	
	private DiverController c;
	private DNotification not;
	private DTextPane jtp;
	private Document doc;
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
	public void initialize() {
		frame = new JFrame();
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("הרשמת לקוח");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][20%,fill][20%:20%,fill][20px:n][][130px:n][80px][80px:n][100px:n]", "[][40px:n][::40px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][::35px][::20px][40px:n]"));
		
		
		
		/* adding fields to the form */
		
		JLabel titleLabel = new JLabel("הרשמה");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 5 0");
		idTextField = new DTextField(20);
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idTextField, "cell 5 3,grow");
		idTextField.setColumns(10);
		idTextField.setToolTipText("הכנס ת.ז בעלת 9 ספרות");
		
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
		
		licenseidTextField = new DTextField(20);
		licenseidTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidTextField, "cell 5 9,grow");
		licenseidTextField.setColumns(10);
		licenseidTextField.setToolTipText("הכנס רישיון צלילה");
		
		JLabel licenseidLabel = new JLabel("\u05E8\u05D9\u05E9\u05D9\u05D5\u05DF \u05E6\u05DC\u05D9\u05DC\u05D4");
		licenseidLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		licenseidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		licenseidLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(licenseidLabel, "cell 7 9,alignx right");
		
		emailTextField = new DTextField(20);
		emailTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailTextField, "cell 5 11,grow");
		emailTextField.setColumns(10);
		emailTextField.setToolTipText("הכנס כתובת מייל");
		JLabel emailLabel = new JLabel("\u05DE\u05D9\u05D9\u05DC");
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(emailLabel, "cell 7 11,alignx right");
		
		phoneTextField = new DTextField(20);
		phoneTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneTextField, "cell 5 13,grow");
		phoneTextField.setColumns(10);
		phoneTextField.setToolTipText("הכנס מספר פלאפון");
		
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
		frame.getContentPane().add(isProtected, "cell 5 15,alignx right,growy");
		
		JLabel insuranceLabel = new JLabel("\u05D1\u05D9\u05D8\u05D5\u05D7");
		insuranceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		insuranceLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(insuranceLabel, "cell 7 15,alignx right");
		confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
		frame.getContentPane().add(confirmButton, "cell 5 17,grow");
		frame.setVisible(true);
		idTextField.requestFocusInWindow();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
	}
	
	

}
