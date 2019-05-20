package Screens;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Models.sqlConnection;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class DiverRegistrationScreen {

	private JFrame frame;
	private JTextField idTextField;
	private JTextField firstNameTextField;
	private JTextField lastnameTextField;
	private JTextField licenseidTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	private JTextField insuranceTextField;

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
		idTextField = new JTextField();
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idTextField, "cell 7 1 3 1,growx");
		idTextField.setColumns(10);
		
		JLabel idLabel = new JLabel("\u05EA\u05E2\u05D5\u05D3\u05EA \u05D6\u05D4\u05D5\u05EA");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idLabel, "cell 10 1");
		
		firstNameTextField = new JTextField();
		firstNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameTextField, "cell 7 2 3 1,growx");
		firstNameTextField.setColumns(10);
		
		JLabel firstNameLabel = new JLabel("\u05E9\u05DD \u05E4\u05E8\u05D8\u05D9");
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(firstNameLabel, "cell 10 2");
		
		lastnameTextField = new JTextField();
		lastnameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameTextField, "cell 7 3 3 1,growx");
		lastnameTextField.setColumns(10);
		
		JLabel lastnameLabel = new JLabel("\u05E9\u05DD \u05DE\u05E9\u05E4\u05D7\u05D4");
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(lastnameLabel, "cell 10 3");
		
		licenseidTextField = new JTextField();
		licenseidTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidTextField, "cell 7 4 3 1,growx");
		licenseidTextField.setColumns(10);
		
		JLabel licenseidLabel = new JLabel("\u05E8\u05D9\u05E9\u05D9\u05D5\u05DF \u05E6\u05DC\u05D9\u05DC\u05D4");
		licenseidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(licenseidLabel, "cell 10 4");
		
		emailTextField = new JTextField();
		emailTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailTextField, "cell 7 5 3 1,growx");
		emailTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("\u05DE\u05D9\u05D9\u05DC");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailLabel, "cell 10 5");
		
		phoneTextField = new JTextField();
		phoneTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneTextField, "cell 7 6 3 1,growx");
		phoneTextField.setColumns(10);
		
		JLabel phoneLabel = new JLabel("\u05E4\u05DC\u05D0\u05E4\u05D5\u05DF");
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(phoneLabel, "cell 10 6");
		
		
		/* when pressing the confirm button, run query to add diver to DB */
		JButton confirmButton = new JButton("\u05D4\u05E8\u05E9\u05DE\u05D4");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sqlConnection dbConnection = sqlConnection.getInstance();
				dbConnection.addDiver(dbConnection.conn, idTextField.getText(), firstNameTextField.getText(), lastnameTextField.getText(),
						licenseidTextField.getText(), emailTextField.getText(), phoneTextField.getText(),insuranceTextField.getText());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
				

			}
		});
		
		insuranceTextField = new JTextField();
		frame.getContentPane().add(insuranceTextField, "cell 7 7 3 1,growx");
		insuranceTextField.setColumns(10);
		
		JLabel insuranceLabel = new JLabel("\u05D1\u05D9\u05D8\u05D5\u05D7");
		frame.getContentPane().add(insuranceLabel, "cell 10 7");
		frame.getContentPane().add(confirmButton, "cell 7 8 3 1,growx");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
	}

}
