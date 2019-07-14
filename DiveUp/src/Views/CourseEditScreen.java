package Views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Controllers.CoursesController;
import Controllers.DiverController;
import Controllers.EmployeeController;
import Models.Course;
import Models.Diver;
import Models.Employee;
import Models.diverSqlQueries;
import net.miginfocom.swing.MigLayout;
import res.DButton;
import res.DNotification;
import res.DTextField;
import res.DTextPane;
import res.UIConstants;
import javax.swing.JComboBox;


/******** Course edit Screen view ******/
public class CourseEditScreen {

	private JFrame frame;
	private DTextField idTextField;
	private DTextField nameTextField;
	private DTextField maxTextField;
	private DTextField priceTextField;
	private JComboBox employeeComboBox;
	private JComboBox typeComboBox;
	private JCheckBox isProtected;
	private DiverController c;
	private DNotification not;
	private diverSqlQueries dbConnection;
	private Course course;
	private DTextPane jtp;
	private Document doc;
	private EmployeeController ec;
	private CoursesController cc;
	private DateFormat outputFormatter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseEditScreen window = new CourseEditScreen(null);
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
	public CourseEditScreen(Course c) {
		this.course= c;
		initialize();
	}
	//pop a message to the screen
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
	//pop an error message to the screen
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
	//fill employees list from the DB
	public void loadEmployees()
	{
		List<Employee> employeesList = ec.getEmployees();
		for(int i=0;i<employeesList.size();i++)
		{
			employeeComboBox.addItem(employeesList.get(i).getFirstName()+"("+employeesList.get(i).getId()+")");
			if(course.getInstructor().equals(employeesList.get(i).getId()))
				employeeComboBox.setSelectedItem(employeesList.get(i).getFirstName()+"("+course.getInstructor()+")");
		}
		
	}
	//load course types from DB
	public void loadTypes() {
		List<String> typesList= cc.getTypes();
		for(int i=0;i<typesList.size();i++)
		{
			typeComboBox.addItem(typesList.get(i));
			if(course.getType().equals(typesList.get(i)))
				typeComboBox.setSelectedItem(course.getType());
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//create jframe
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 30));
		/* set the size and the location of the frame */
		frame.setBounds(UIConstants.miniScreenx, UIConstants.miniScreeny, UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		frame.getContentPane().setBackground(Color.WHITE);
		//Title and icon add
		frame.setTitle("עדכון פרטי קורס");
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/images/snorkel.PNG"));
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[20%,fill][][20%,fill][20%][20%,fill][20%:20%,grow,fill][20px:n][][130px:n][80px][80px:n][100px:n]", "[][40px:n][::40px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::10px][40px:n][::20px][40px:n][][40px:n][40px:n][40px:n]"));
		
		
		
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
		idTextField.setForeground(Color.LIGHT_GRAY);
		idTextField.setText(String.valueOf(course.getId()));
		
		
		JLabel idLabel = new JLabel("מזהה");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(idLabel, "cell 7 3,alignx right");
		
		nameTextField = new DTextField(20);
		nameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(nameTextField, "cell 5 5,grow");
		nameTextField.setColumns(10);
		nameTextField.setText(course.getName());
	
		JLabel nameLabel = new JLabel("שם קורס");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(nameLabel, "cell 7 5,alignx right");
		
		typeComboBox = new JComboBox();
		((JLabel)typeComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

		frame.getContentPane().add(typeComboBox, "cell 5 7,alignx right");
	
		
		JLabel typeLabel = new JLabel("סוג");
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		typeLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(typeLabel, "cell 7 7,alignx right");
		
		employeeComboBox = new JComboBox();
		((JLabel)employeeComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(employeeComboBox, "cell 5 9,alignx right");
		JLabel employeeLabel = new JLabel("מדריך");
		employeeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		employeeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(employeeLabel, "cell 7 9,alignx right");
		
		maxTextField = new DTextField(20);
		maxTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(maxTextField, "cell 5 11,grow");
		maxTextField.setColumns(10);
		maxTextField.setText(String.valueOf(course.getMaxDivers()));
		
		JLabel maxLabel = new JLabel("כמות מקסימלית");
		maxLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		maxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		maxLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(maxLabel, "cell 7 11,alignx right");
		
		priceTextField = new DTextField(20);
		priceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(priceTextField, "cell 5 13,grow");
		priceTextField.setColumns(10);
		priceTextField.setText(String.valueOf(course.getPrice()));
	
		
		JLabel priceLabel = new JLabel("מחיר");
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(priceLabel, "cell 7 13,alignx right");
		
		
		
		DTextField startTextField = new DTextField(20);
		startTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(startTextField, "cell 5 15,grow");
		outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		startTextField.setText(outputFormatter.format(course.getStartDay()));
		
		
		JLabel startDateLabel = new JLabel("תאריך התחלה");
		startDateLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		startDateLabel.setForeground(UIConstants.BORDER_DARK);
		frame.getContentPane().add(startDateLabel, "cell 7 15,alignx right");
		
		DTextField endTextField = new DTextField(20);
		endTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(endTextField, "cell 5 17,grow");
		endTextField.setText(outputFormatter.format(course.getEndDay()));
		
		
		
		JLabel endDateLabel = new JLabel("תאריך סיום");
		endDateLabel.setForeground(new Color(112, 112, 112));
		endDateLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(endDateLabel, "cell 7 17,alignx right");
		
		DTextField descTextField = new DTextField(20);
		descTextField.setText(course.getDesc());
		descTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(descTextField, "cell 5 19,grow");
		
		JLabel descLabel = new JLabel("תיאור הקורס");
		descLabel.setForeground(new Color(112, 112, 112));
		descLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(descLabel, "cell 7 19,alignx right");
		DButton confirmButton = new DButton("\u05D4\u05E8\u05E9\u05DE\u05D4",DButton.Mode.PRIMARY);
		confirmButton.setText("עדכן");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//input validation
				Map<Integer, String> violations=null;
				try {
					violations = cc.checkCourseAdd(nameTextField.getText(),typeComboBox.getSelectedItem().toString(), employeeComboBox.getSelectedItem().toString()
							, maxTextField.getText(), priceTextField.getText(), outputFormatter.parse(startTextField.getText()), outputFormatter.parse(endTextField.getText()), descTextField.getText());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					errorMessage("תאריך שגוי", "תאריך שגוי");
					e.printStackTrace();
				}

				//if any violation found , update gui and inform the user
				if(violations.size()>0){
	                   

                    for(Integer vcode:violations.keySet()){

                    	if(vcode==cc.invalid_courseName ){
                            nameTextField.setViolatedBorder(true);
                        }
                    	
                    	
                        //if type violated
                        if(vcode==cc.type_empty ){
                            typeComboBox.setBorder(BorderFactory.createLineBorder(Color.red,3));
                        }

                        //if employee violated
                        if(vcode==cc.firstName_empty){
                        	employeeComboBox.setBorder(BorderFactory.createLineBorder(Color.red,3));
                        }
                        //if max violated
                        if(vcode==cc.max_amount_wrong){
                            maxTextField.setViolatedBorder(true);
                        }
                        
                        //if price violated
                        if(vcode==cc.price_wrong){
                            priceTextField.setViolatedBorder(true);
                        }
                        //start date violated
                        if(vcode==cc.invalid_dates){
                            startTextField.setViolatedBorder(true);
                            endTextField.setViolatedBorder(true);
                        }
                        //desc violated
                        if(vcode==cc.desc_empty){
                            descTextField.setViolatedBorder(true);
                        }
                    }
                    errorMessage("אנא תקן את השדות המסומנים באדום", "פרטים שגויים");
                    
                    //input is valid , update course
                } else
					try {
						
						if(cc.getCourseByID(Integer.valueOf(idTextField.getText())).getCurrentAmount()<= Integer.valueOf(maxTextField.getText()))
						{
						try {
							//getting employee id from the GUI
							String employeeID="";
							Pattern pattern = Pattern.compile("\\((.*?)\\)");
							Matcher matcher = pattern.matcher(employeeComboBox.getSelectedItem().toString());
							if (matcher.find())
							{
							    employeeID = matcher.group(1);
							    
							}
							
							//updating the course
							cc.updateCourse(Integer.valueOf(idTextField.getText()), nameTextField.getText(), cc.getTypeID(typeComboBox.getSelectedItem().toString()), employeeID
							, Integer.valueOf(maxTextField.getText()), Double.valueOf(priceTextField.getText()), outputFormatter.parse(startTextField.getText()), outputFormatter.parse(endTextField.getText()), descTextField.getText());
						message("הקורס עודכן בהצלחה", "הקורס עודכן");

						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//close window
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							errorMessage("העדכון נכשל", "עדכון נכשל");
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							errorMessage("העדכון נכשל", "עדכון נכשל");
							e.printStackTrace();
						}
							
}
						else
						{
							errorMessage("כמות נוכחית גדולה מכמות מקסימלית", "שגיאה בעריכה");
						}
						
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		});
		frame.getContentPane().add(confirmButton, "cell 5 21,grow");
		frame.setVisible(true);
		idTextField.requestFocusInWindow();
		dbConnection = new diverSqlQueries();
		ec= new EmployeeController();
		cc = new CoursesController();
		loadEmployees();
		loadTypes();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);// prevent closing all windows when closing this window
		
	}


}
