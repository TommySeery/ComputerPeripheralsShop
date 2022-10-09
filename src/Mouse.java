import java.awt.Color;

public class Mouse extends Product{
	private MouseType type;
	private int buttons;
	
	public Mouse(String barcode, String brand, String colour, Connection conn, int stock, double originalCost, double retailPrice, MouseType type, int buttons) { //Constructor for Mouse including the originalCost
		super(barcode,brand,colour,conn,stock,originalCost,retailPrice);
		this.type=type;
		this.buttons=buttons;
	}
	
	public Mouse(String barcode, String brand, String colour, Connection conn, int stock, double retailPrice, MouseType type, int buttons) { //Constructor for Mouse excluding the originalCost
		super(barcode,brand,colour,conn,stock,retailPrice);
		this.type=type;
		this.buttons=buttons;
	}

	public MouseType getType() {
		return type;
	}

	public int getButtons() {
		return buttons;
	}
	
	public String getProduct() {
		return "mouse";
	}

	@Override
	public String toString() {
		String[] s = super.toList(); //Get Product list
		//Replace empty elements
		s[1] = this.getProduct();
		s[2] = type.toString().toLowerCase();
		s[9] = Integer.toString(buttons);
		return String.join(", ", s); //Convert the list to a string with commas (same as Stock.txt) and return it
	}
	
	public String toDisplayString() {
		return super.toDisplayString() + type + " Mouse " + " with " + buttons + " buttons";
	}
}
