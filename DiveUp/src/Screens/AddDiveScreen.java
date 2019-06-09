package Screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Classes.Dive;
import Classes.Diver;
import Controllers.DiverController;
import Controllers.DivesController;
import Models.SendEmailTLS;
import Models.diverSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;
import javax.swing.JComboBox;

public class AddDiveScreen {

	private JFrame frame;
	private DTextField maxDepthTextField;
	private DiverController dc;
	private DivesController diveController;
	private DNotification not;
	private diverSqlQueries dbConnection;
	private DTextPane jtp;
	private Document doc;
	private JComboBox diverComboBox;
	private JComboBox locationComboBox;
	private JComboBox startMinutesComboBox;
	private JComboBox startHourComboBox;
	private JComboBox endHourComboBox;
	private JComboBox endMinutesComboBox;
	private DTextField startAirTextField ;
	private Dive selectedDive=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddDiveScreen window = new AddDiveScreen(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public AddDiveScreen(Dive dive)
	{
		selectedDive = dive;
		dc = new DiverController();
		diveController = new DivesController();
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
	
	public void updateDiversList()
	{
		
		List<Diver>diversList = dc.getDivers();
		((JLabel)diverComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
	    for(int i =0 ; i<diversList.size();i++)
	    {
	    	diverComboBox.addItem( "(" + diversList.get(i).getId()+")"+diversList.get(i).getFirstName());
	    }
	}
	
	
	
	public void updateLocations()
	{
		List<String> locations = diveController.getLocations();
		for(int i=0;i<locations.size();i++)
		{
			locationComboBox.addItem(locations.get(i));
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
		frame.setTitle("הוספת צלילה");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][350px:n,fill][20%:20%,fill][20px:n][][130px:n][80px][80px:n][100px:n]", "[][40px:n][::40px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][40px:n][40px:n]"));
		
		
		
		/* adding fields to the form */
		
		JLabel titleLabel = new JLabel("הוספת צלילה");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(UIConstants.SELECTED_BTN);
		frame.getContentPane().add(titleLabel, "cell 5 0");
		
		diverComboBox = new JComboBox();
		frame.getContentPane().add(diverComboBox, "cell 5 3,growx");
		
		JLabel diverLabel = new JLabel("צוללן");
		diverLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		diverLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		diverLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(diverLabel, "cell 7 3,alignx right");
		
		locationComboBox = new JComboBox();
		((JLabel)locationComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(locationComboBox, "cell 5 5,growx");
		JLabel locationLabel = new JLabel("מיקום");
		locationLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		locationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		locationLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(locationLabel, "cell 7 5,alignx right");
		
		maxDepthTextField = new DTextField(20);
		maxDepthTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(maxDepthTextField, "cell 5 7,grow");
		maxDepthTextField.setColumns(10);
		maxDepthTextField.setToolTipText("הכנס רישיון צלילה");
		
		JLabel depthLabel = new JLabel("עומק מקסימלי");
		depthLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		depthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		depthLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(depthLabel, "cell 7 7,alignx right");

		startHourComboBox = new JComboBox();
		startHourComboBox.setFont(new Font("Tahoma",Font.BOLD,16));
		((JLabel)startHourComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(startHourComboBox, "flowx,cell 5 9");
		for(int i=0;i<=23;i++)
		{
			if(i<10)
			{
				startHourComboBox.addItem("0"+String.valueOf(i));
			}
			else
			startHourComboBox.addItem(String.valueOf(i));
		}
		
		startMinutesComboBox = new JComboBox();
		startMinutesComboBox.setFont(new Font("Tahoma",Font.BOLD,16));
		((JLabel)startMinutesComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		frame.getContentPane().add(startMinutesComboBox, "cell 5 9");
		JLabel starHourLabel = new JLabel("שעת התחלה");
		starHourLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		starHourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		starHourLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(starHourLabel, "cell 7 9,alignx right");

		for(int i=0;i<=59;i++)
		{
			if(i<10)
			{
				startMinutesComboBox.addItem("0"+String.valueOf(i));
			}
			else
				startMinutesComboBox.addItem(String.valueOf(i));
		}

		
		endHourComboBox = new JComboBox();
		endHourComboBox.setFont(new Font("Tahoma",Font.BOLD,16));
		((JLabel)endHourComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i=0;i<=23;i++)
		{
			if(i<10)
			{
				endHourComboBox.addItem("0"+String.valueOf(i));
			}
			else
				endHourComboBox.addItem(String.valueOf(i));
		}
		
		
		
		
		
		
		
		frame.getContentPane().add(endHourComboBox, "flowx,cell 5 11");
		
		
		
		

		
		JLabel endHourLabel = new JLabel("שעת סיום");
		endHourLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		endHourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		endHourLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(endHourLabel, "cell 7 11,alignx right");

		endMinutesComboBox = new JComboBox();
		endMinutesComboBox.setFont(new Font("Tahoma",Font.BOLD,16));
		((JLabel)endMinutesComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0;i<=59;i++)
		{
			if(i<10)
			{
				endMinutesComboBox.addItem("0"+String.valueOf(i));
			}
			else
				endMinutesComboBox.addItem(String.valueOf(i));
		}
		frame.getContentPane().add(endMinutesComboBox, "cell 5 11");
		
		
		
		startAirTextField = new DTextField(20);
		startAirTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(startAirTextField, "cell 5 13,grow");
		
		JLabel startAirLabel = new JLabel("אוויר בהתחלה");
		startAirLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		startAirLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(startAirLabel, "cell 7 13,alignx right");
		
		DTextField endAirTextField = new DTextField(20);
		endAirTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(endAirTextField, "cell 5 15,grow");
		
		JLabel endAirLabel = new JLabel("אוויר בסיום");
		endAirLabel.setForeground(new Color(112, 112, 112));
		endAirLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(endAirLabel, "cell 7 15,alignx right");
		DButton confirmButton = new DButton("הוספה",DButton.Mode.PRIMARY);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		    
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
        		Matcher matcher = pattern.matcher(diverComboBox.getSelectedItem().toString());
        		String diverID="";
        		if (matcher.find())
        		{
        		    diverID = matcher.group(1);
        		}
        		DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        		diveController.addDive(diverID,locationComboBox.getSelectedItem().toString(),outputFormatter.format(new Date()),Integer.valueOf(maxDepthTextField.getText()),
					startHourComboBox.getSelectedItem().toString()+":"+startMinutesComboBox.getSelectedItem().toString(),
					endHourComboBox.getSelectedItem().toString()+":"+endMinutesComboBox.getSelectedItem().toString()
					,Integer.valueOf(startAirTextField.getText()),Integer.valueOf(endAirTextField.getText()));
			
        		message("הצלילה התווספה בהצלחה", "צלילה התווספה");
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window

			}
		});
		frame.getContentPane().add(confirmButton, "cell 5 18,grow");
		frame.setVisible(true);
		updateDiversList();
		updateLocations();
		if(selectedDive!=null)
		{
			titleLabel.setText("עדכון צלילה");
			confirmButton.setText("עדכן");
			System.out.println(selectedDive.toString());
			diverComboBox.setSelectedItem("("+dc.getDiverByID(selectedDive.getDiver()).getId()+")"+dc.getDiverByID(selectedDive.getDiver()).getFirstName());
			locationComboBox.setSelectedItem(selectedDive.getLocation());
			maxDepthTextField.setText(String.valueOf(selectedDive.getMaxDepth()));
			startHourComboBox.setSelectedItem(selectedDive.getStartTime().substring(0, 2));
			startMinutesComboBox.setSelectedItem(selectedDive.getStartTime().substring(3, 5));
			endHourComboBox.setSelectedItem(selectedDive.getEndTime().substring(0, 2));
			endMinutesComboBox.setSelectedItem(selectedDive.getEndTime().substring(3, 5));
			startAirTextField.setText(String.valueOf(selectedDive.getAirStart()));
			endAirTextField.setText(String.valueOf(selectedDive.getAirEnd()));
			
		}
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
		
	}
}
