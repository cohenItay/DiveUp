package Controllers;

import javax.swing.JTable;

import Models.saleSQLQueries;

public class SaleController implements Controller {

	private saleSQLQueries dbConnection;
	
	public SaleController()
	{
		dbConnection = new saleSQLQueries();
	}
	
	public double priceCalculate(JTable table)
	{
		double totalPrice = 0;
		for(int i =0;i<table.getRowCount();i++)
		{
			totalPrice += (double)table.getValueAt(i, 0);
		}
		return totalPrice;
	}
	
	public String getAllItems(JTable table)
	{
		String items="";
		for(int i =0;i<table.getRowCount();i++)
		{
			if(i==table.getRowCount()-1)
				items = items.concat((String)table.getValueAt(i, 2)) ;
			else
				items = items.concat((String)table.getValueAt(i, 2)+","); 
		}
		return items;
	}
	
	public void addSale(String diverID,String itemsList,String date,double totalPrice)
	{
		dbConnection.addSale(diverID, itemsList, date, totalPrice);
	}
	
}
