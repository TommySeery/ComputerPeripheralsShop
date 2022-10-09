import java.text.NumberFormat;
import java.util.ArrayList;

public class Customer extends User{
	private ArrayList<Product> basket = new ArrayList<>();
	private String email;
	private double total=0;
	
	//Both must be string in case they start with a '0'
	private String cardNo;
	private String secCode;
	
	public Customer(int id, String username, String name, Address addr) {
		super(id,username,name,addr);
	}
	
	public boolean addItem(Product p) {
		if(p.getStock()>0) {
			basket.add(p);
			this.total+=p.getRetailPrice();
			p.decStock();
			return true;
		}
		return false;
	}
	
	public String getDisplayTotal() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(this.total).substring(1);
	}
	
	public Product peekItem() {
		return basket.get(basket.size()-1);
	}
	
	public ArrayList<Product> getItems(){
		return basket;
	}
	
	public void clearBasket() {
		//For each product in the users basket, increment the stock since the user is not buying it
		for(Product p: this.getItems()) {
			p.incStock();
		}
		basket = new ArrayList<>();
		this.total=0;
	}
	
	public boolean productAdded(Product p) {
		for(Product product : basket) {
			if(product.getBarcode()==p.getBarcode()) {
				return true;
			}
		}
		return false;
	}
}
