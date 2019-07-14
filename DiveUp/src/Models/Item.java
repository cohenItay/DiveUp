package Models;

public class Item extends Asset{

	int amount; //Quantity remaining in stock
	double loanPrice;

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getLoanPrice() {
		return loanPrice;
	}

	public void setLoanPrice(double loanPrice) {
		this.loanPrice = loanPrice;
	}
	
}
