package Classes;
import java.util.Date;
import java.util.List;

public class LoanSales {

	int saleID;
	String diverID;
	List<LoanItem> items;
	Date date;
	double totalPrice;
	
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
	public List<LoanItem> getItems() {
		return items;
	}
	public void setItems(List<LoanItem> items) {
		this.items = items;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
