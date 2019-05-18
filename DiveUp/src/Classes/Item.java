package Classes;

public class Item extends Asset{

	int amount; //Quantity remaining in stock
	

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
