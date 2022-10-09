import java.awt.Color;

public class Keyboard extends Product{
	private KeyboardLayout layout;
	private KeyboardType type;
	
	public Keyboard(String barcode, String brand, String colour, Connection conn, int stock, double originalCost, double retailPrice, KeyboardType type, KeyboardLayout layout) { //Constructor for Keyboard including the originalCost
		super(barcode,brand,colour,conn,stock,originalCost,retailPrice);
		this.layout=layout;
		this.type=type;
	}
	
	public Keyboard(String barcode, String brand, String colour, Connection conn, int stock, double retailPrice, KeyboardType type, KeyboardLayout layout) { //Constructor for Keyboard excluding the originalCost
		super(barcode,brand,colour,conn,stock,retailPrice);
		this.layout=layout;
		this.type=type;
	}

	public KeyboardLayout getLayout() {
		return layout;
	}
	
	public KeyboardType getType() {
		return type;
	}
	
	public String toDisplayString() {
		return super.toDisplayString() + type + " Keyboard, " + layout.toString().toUpperCase() + " layout";
	}
	
	public String getProduct() {
		return "keyboard";
	}
	
	@Override
	public String toString() {
		String[] s = super.toList(); //Get Product list
		//Replace empty elements
		s[1] = this.getProduct();
		s[2] = type.toString().toLowerCase();
		s[9] = layout.toString().toUpperCase();
		return String.join(", ", s); //Convert the list to a string with commas (same as Stock.txt) and return it
	}
	
}
