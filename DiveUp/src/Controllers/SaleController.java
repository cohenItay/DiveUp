package Controllers;

import java.util.List;

import javax.swing.JTable;
import Models.Sale;
import Managers.SaleSqlQueries;

public class SaleController implements Controller {

	private SaleSqlQueries dbConnection;//define sales queries instance
	
	public SaleController()
	{
		dbConnection = new SaleSqlQueries();//initiate sales queries instance
	}
	
	//calculate sale price by items in cart
	public double priceCalculate(JTable table)
	{
		double totalPrice = 0;
		for(int i =0;i<table.getRowCount();i++)
		{
			totalPrice += (double)table.getValueAt(i, 0);
		}
		return totalPrice;
	}
	
	
	//get all items name from the cart
	public String getAllItems(JTable table)
	{
		String items="";
		for(int i =0;i<table.getRowCount();i++)
		{
			if(i==table.getRowCount()-1)
				items = items.concat((String)table.getValueAt(i, 2)+"(כמות:"+(String)table.getValueAt(i, 1)+")") ;
			else
				items = items.concat((String)table.getValueAt(i, 2)+"(כמות:"+(String)table.getValueAt(i, 1)+")"+","); 
		}
		return items;
	}
	
	//add new sale to the DB
	public void addSale(String diverID,String itemsList,String date,double totalPrice)
	{
		dbConnection.addSale(diverID, itemsList, date, totalPrice);
	}
	
	//get all sales from the DB
	public List<Sale> getSales()
	{
		SaleSqlQueries dbConnection = new SaleSqlQueries();//connection to the DB
		return dbConnection.getSales();//Getting divers list from the DB
	}
	
	//get all sales by customer id
	public List<Sale> getCustomerSales(String id)
	{
		SaleSqlQueries dbConnection = new SaleSqlQueries();//connection to the DB
		return dbConnection.getCustomerSales(id);//Getting divers list from the DB
	}
	
}
