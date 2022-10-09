import java.awt.Color;
import java.text.NumberFormat;

public abstract class Product{
	protected String barcode; //String type since it could start with a 0, or many 0's
	protected String brand;
	protected String colour;
	protected Connection conn;
	protected int stock;
	protected double originalCost;
	protected double retailPrice;
	
	public Product(String barcode, String brand, String colour, Connection conn, int stock, double originalCost, double retailPrice) { //Constructor for Product including the originalCost
		this(barcode,brand,colour,conn,stock,retailPrice);
		this.originalCost=originalCost;
	}
	
	public Product(String barcode, String brand, String colour, Connection conn, int stock, double retailPrice) { //Constructor for Product including the originalCost
		this.barcode=barcode;
		this.brand=brand;
		this.colour=colour;
		this.conn=conn;
		this.stock=stock;
		this.retailPrice=retailPrice;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public String getBrand() {
		return brand;
	}

	public String getColour() {
		return colour;
	}


	public Connection getConnectivity() {
		return conn;
	}


	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock=stock;
	}

	public void decStock() {
		this.stock--;
	}
	
	public void incStock() {
		this.stock++;
	}

	public double getOriginalCost() {
		return originalCost;
	}

	public double getRetailPrice() {
		return retailPrice;
	}
	
	public String getOriginalCostDisplay() {
		return "£"+priceOf(originalCost);
	}
	
	public String getRetailPriceDisplay() {
		return "£"+priceOf(retailPrice);
	}
	
	public String priceOf(double cost) { //Converts double to price format
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(cost).substring(1);
	}
	
	public String[] toList() { 
		String[] s = {barcode.trim(),"","",brand.trim(),colour.toString().toLowerCase(),conn.toString().toLowerCase(),Integer.toString(stock),priceOf(originalCost),priceOf(retailPrice),""}; //Empty elements are where the keyboard/mouse attributes will go
		return s;
	}
	
	public String toDisplayString() {
		return "("+barcode+") "+ brand + " " +colour + " "+ conn+ " ";
	}
	
}
