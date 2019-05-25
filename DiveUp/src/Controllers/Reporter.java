package Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Classes.Employee;
import Classes.Sale;
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
	            rowhead.createCell(3).setCellValue("ת.ז צוללן");
	            rowhead.createCell(4).setCellValue("מזהה");
	            
	            
	            /*Fill all the table from the sales list */
	            double sum=0;
	            int i=1;
	            for(i=0;i<salesList.size();i++)
	            {
	            	HSSFRow row = sheet.createRow((short)i+1);
		            row.createCell(4).setCellValue(salesList.get(i).getSaleID());
		            sheet.autoSizeColumn(4);
		            row.createCell(3).setCellValue(salesList.get(i).getDiverID());
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
	            HSSFSheet sheet = workbook.createSheet("SalesSheet");   // create sheet in notebook
	            	
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
	
	
	
	
	
	}

