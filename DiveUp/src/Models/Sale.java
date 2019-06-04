package Models;

public class Sale {

	int saleID;
	String diverID;
	String items;//the list of products that are for sell
	String date; //Date of purchase
	double totalPrice; //the transaction total price  
	
	
	public int getSaleID() {
		return saleID;
	}
	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}
	public String getDiverID() {
		return diverID;
	}
	public void setDiverID(String diverID) {
		this.diverID = diverID;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	
}
