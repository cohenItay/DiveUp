import java.util.Date;
import java.util.List;

public class Sale {

	int saleID;
	String diverID;
	List<Item> items;
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
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
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
