package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Controllers.DivesController;
import Controllers.EmployeeController;
import Controllers.SaleController;
import Models.Dive;
import Models.Diver;
import Models.Employee;
import Models.Sale;
import res.UIConstants;

public class Reporter {
	

private SaleController sController;// creating sales controller
private EmployeeController eController;

public Reporter()
{
	sController = new SaleController();//initiate sales controller
	eController = new EmployeeController();//initiate employee controller
}

public SaleController getSaleController()
{
	return sController;
}

public EmployeeController getEmployeeController()
{
	return eController;
}


//function to pop error message on screen
public static void errorMessage(String infoMessage, String titleBar)
{
    JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
}


	public void createSalesReport(List<Sale> salesList,String customer)
	{
	     try {
	    	 	Date today = new Date();//getting current date
	    	 	DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_(HH_mm)");//create time format
	    	 	String date = dateFormat.format(today);//parse the date to be in the above format
	            String filename = UIConstants.salesReportsPath+date+"_"+customer+"_SalesReport"+".xls" ;//define file path
	            HSSFWorkbook workbook = new HSSFWorkbook(); //create new excel workbook
	            HSSFSheet sheet = workbook.createSheet("SalesSheet");   // create sheet in notebook
	               
	            
	            HSSFRow rowhead = sheet.createRow((short)0); // create first row
	            /*Fill Header Columns*/
	            rowhead.createCell(0).setCellValue("סכום");
	            rowhead.createCell(1).setCellValue("תאריך");
	            rowhead.createCell(2).setCellValue("רשימת פריטים");
	            rowhead.createCell(3).setCellValue("שם");
	            rowhead.createCell(4).setCellValue("ת.ז צוללן");
	            rowhead.createCell(5).setCellValue("מזהה");
	            
	            
	            /*Fill all the table from the sales list */
	            double sum=0;
	            int i=1;
	            for(i=0;i<salesList.size();i++)
	            {
	            	
	            	Pattern pattern = Pattern.compile("\\((.*?)\\)");
					String diverName = salesList.get(i).getDiverID();
	        		Matcher matcher = pattern.matcher(diverName);
	        		String diverID="";
	        		if (matcher.find())
	        		{
	        			diverID = matcher.group(1);
	        		    diverName = diverName.replace("("+matcher.group(1)+")","");
	        		}
					
	            	
	            	
	            	HSSFRow row = sheet.createRow((short)i+1);
	            	row.createCell(5).setCellValue(salesList.get(i).getSaleID());
		            sheet.autoSizeColumn(5);
	            	row.createCell(4).setCellValue(diverID);
		            sheet.autoSizeColumn(4);
		            row.createCell(3).setCellValue(diverName);
		            sheet.autoSizeColumn(3);
		            row.createCell(2).setCellValue(salesList.get(i).getItems());
		            sheet.autoSizeColumn(2);
		            row.createCell(1).setCellValue(salesList.get(i).getDate());
		            sheet.autoSizeColumn(1);
		            row.createCell(0).setCellValue(salesList.get(i).getTotalPrice());
		            sheet.autoSizeColumn(0);
		            sum = sum + salesList.get(i).getTotalPrice();
	            }
	            
	            /*Add sum of all sales to the excel file*/
	            HSSFRow row = sheet.createRow((short)i+2);
	            row.createCell(3).setCellValue(sum);
	            sheet.autoSizeColumn(3);
	            row.createCell(4).setCellValue("רווח כולל");
	            sheet.autoSizeColumn(4);
	    
	            /* saving and closing the file */
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            
	            //Asking user if he wants to open the file
	            int answer = JOptionPane.showConfirmDialog(null, "האם תרצה לצפות בדוח", "דוח המכירות נוצר", JOptionPane.YES_NO_OPTION);
	            if(answer == JOptionPane.YES_OPTION)
	            {
	            	try {
	            		Desktop.getDesktop().open(new File(filename)); // open the excel file	
	            	}
	            	catch ( Exception ex2 ) {
	    	        	errorMessage(ex2.getLocalizedMessage(), "נכשל בפתיחת הדוח");
	    	        }   	
	            }

	            

	        } catch ( Exception ex ) {
	        	errorMessage(ex.getLocalizedMessage(), "נכשל ביצירת הדוח");
	        }
	    }
	
	
	
	
	
	public void createEmployeesReport(List<Employee> employeesList,String employee)
	{
	     try {
	    	 	Date today = new Date();//getting current date
	    	 	DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_(HH_mm)");//create time format
	    	 	String date = dateFormat.format(today);//parse the date to be in the above format
	            String filename = UIConstants.employeesReportsPath+date+"_"+employee+"_EmployeesReport"+".xls" ;//define file path
	            HSSFWorkbook workbook = new HSSFWorkbook(); //create new excel workbook
	            HSSFSheet sheet = workbook.createSheet("EmployeesSheet");   // create sheet in notebook
	            	
	            HSSFRow rowhead = sheet.createRow((short)0); // create first row
	            /*Fill Header Columns*/
	            rowhead.createCell(0).setCellValue("משכורת");
	            rowhead.createCell(1).setCellValue("טלפון");
	            rowhead.createCell(2).setCellValue("מייל");
	            rowhead.createCell(3).setCellValue("ותק");
	            rowhead.createCell(4).setCellValue("שם משפחה");
	            rowhead.createCell(5).setCellValue("שם");
	            rowhead.createCell(6).setCellValue("ת.ז");
	            
	            
	            /*Fill all the table from the employees list */
	            double sum=0;
	            int i=1;
	            for(i=0;i<employeesList.size();i++)
	            {
	            	HSSFRow row = sheet.createRow((short)i+1);
	            	row.createCell(6).setCellValue(employeesList.get(i).getId());
		            sheet.autoSizeColumn(6);
		            row.createCell(5).setCellValue(employeesList.get(i).getFirstName());
		            sheet.autoSizeColumn(5);
		            row.createCell(4).setCellValue(employeesList.get(i).getLastName());
		            sheet.autoSizeColumn(4);
		            row.createCell(3).setCellValue(employeesList.get(i).getSeniority());
		            sheet.autoSizeColumn(3);
		            row.createCell(2).setCellValue(employeesList.get(i).getEmail());
		            sheet.autoSizeColumn(2);
		            row.createCell(1).setCellValue(employeesList.get(i).getPhone());
		            sheet.autoSizeColumn(1);
		            row.createCell(0).setCellValue(employeesList.get(i).getSalary());
		            sheet.autoSizeColumn(0);
		            sum = sum + employeesList.get(i).getSalary();
	            }
	            
	            /*Add sum of all sales to the excel file*/
	            HSSFRow row = sheet.createRow((short)i+2);
	            row.createCell(0).setCellValue(sum);
	            sheet.autoSizeColumn(0);
	            row.createCell(1).setCellValue("סהכ משכורות");
	            sheet.autoSizeColumn(1);
	    
	            /* saving and closing the file */
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            
	            //Asking user if he wants to open the file
	            int answer = JOptionPane.showConfirmDialog(null, "האם תרצה לצפות בדוח", "דוח המכירות נוצר", JOptionPane.YES_NO_OPTION);
	            if(answer == JOptionPane.YES_OPTION)
	            {
	            	try {
	            		Desktop.getDesktop().open(new File(filename)); // open the excel file	
	            	}
	            	catch ( Exception ex2 ) {
	    	        	errorMessage(ex2.getLocalizedMessage(), "נכשל בפתיחת הדוח");
	    	        }   	
	            }

	            

	        } catch ( Exception ex ) {
	        	errorMessage(ex.getLocalizedMessage(), "נכשל ביצירת הדוח");
	        }
	    }
	

	
	
	

	public void createRefreshReport(List<Diver> DiversList,String diver)
	{
	     try {
	    	 
	    	 	//sending mail to the customers to inform them that they need a refresh dive
	    	 	for(int i=0;i<DiversList.size();i++)
	    	 	{
	    	 		SendEmailTLS m = new SendEmailTLS(DiversList.get(i).getEmail(),"תזכורת לצלילת רענון", "הנך נדרש לבצע צלילת ריענון בחודש הקרוב");    	 
	    	 		    	 
	    	 	}
	    	 
	    	 	Date today = new Date();//getting current date
	    	 	DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_(HH_mm)");//create time format
	    	 	String date = dateFormat.format(today);//parse the date to be in the above format
	            String filename = UIConstants.refReportsPath+date+"_"+diver+"_RefReport"+".xls" ;//define file path
	            HSSFWorkbook workbook = new HSSFWorkbook(); //create new excel workbook
	            HSSFSheet sheet = workbook.createSheet("EmployeesSheet");   // create sheet in notebook
	            	
	            HSSFRow rowhead = sheet.createRow((short)0); // create first row
	            /*Fill Header Columns*/
	            
	            rowhead.createCell(0).setCellValue("צלילה אחרונה");
	            rowhead.createCell(1).setCellValue("טלפון");
	            rowhead.createCell(2).setCellValue("מייל");
	            rowhead.createCell(3).setCellValue("רישיון");
	            rowhead.createCell(4).setCellValue("שם משפחה");
	            rowhead.createCell(5).setCellValue("שם");
	            rowhead.createCell(6).setCellValue("ת.ז");
	            
	            
	            /*Fill all the table from the Divers list */
	            double sum=0;
	            int i=1;
	            for(i=0;i<DiversList.size();i++)
	            {
	            	HSSFRow row = sheet.createRow((short)i+1);
	            	row.createCell(6).setCellValue(DiversList.get(i).getId());
		            sheet.autoSizeColumn(6);
		            row.createCell(5).setCellValue(DiversList.get(i).getFirstName());
		            sheet.autoSizeColumn(5);
		            row.createCell(4).setCellValue(DiversList.get(i).getLastName());
		            sheet.autoSizeColumn(4);
		            row.createCell(3).setCellValue(DiversList.get(i).getLicenseID());
		            sheet.autoSizeColumn(3);
		            row.createCell(2).setCellValue(DiversList.get(i).getEmail());
		            sheet.autoSizeColumn(2);
		            row.createCell(1).setCellValue(DiversList.get(i).getPhone());
		            sheet.autoSizeColumn(1);
		            
		            DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		            List<Dive> diveList = new DivesController().getDivesBook(DiversList.get(i).getId());
		            row.createCell(0).setCellValue(outputFormatter.format(diveList.get(diveList.size()-1).getDate()));
		            sheet.autoSizeColumn(0);
		            
		            
	            }
	            
	    
	            /* saving and closing the file */
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            
	            //Asking user if he wants to open the file
	            int answer = JOptionPane.showConfirmDialog(null, "האם תרצה לצפות בדוח", "דוח המכירות נוצר", JOptionPane.YES_NO_OPTION);
	            if(answer == JOptionPane.YES_OPTION)
	            {
	            	try {
	            		Desktop.getDesktop().open(new File(filename)); // open the excel file	
	            	}
	            	catch ( Exception ex2 ) {
	    	        	errorMessage(ex2.getLocalizedMessage(), "נכשל בפתיחת הדוח");
	    	        }   	
	            }

	            

	        } catch ( Exception ex ) {
	        	errorMessage(ex.getLocalizedMessage(), "נכשל ביצירת הדוח");
	        }
	    }

	
	
	
	}

