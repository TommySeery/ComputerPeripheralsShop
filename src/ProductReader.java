import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProductReader {
	private String dir;
	
	public ProductReader(String dir) {
		this.dir=dir;
	}
	
	public BufferedReader getBufferedReader() {
		try {
		FileReader fr = new FileReader(dir);
		BufferedReader br = new BufferedReader(fr);
		return br;
		}catch(IOException ex) {
			
		}
		return null;
	}
	
	public ArrayList<Product> readCustomer(){ //Returns a Products list contains only the products that are available to the customer, without the original cost
		ArrayList<Product> prods = new ArrayList<>(); 
		try {
			BufferedReader br = getBufferedReader();
			String line=null;
			Product p;
			while((line=br.readLine())!=null) { //Each time while statement is run, set String line to the next line in txt file, and run the while, while this line is not null
				String data[] = line.split(",");
				switch(data[1].trim()) { //This is where either "keyboard" or "mouse" is
				case "mouse": 
					p = new Mouse(data[0],data[3].trim(),data[4].trim(),Connection.valueOf(data[5].trim().toUpperCase()),Integer.parseInt(data[6].trim()),Double.parseDouble(data[8].trim()),MouseType.valueOf(data[2].trim().toUpperCase()),Integer.parseInt(data[9].trim()));
					if(p.getStock()>0) {
						prods.add(p);
					}
					break;
				case "keyboard":
					p = new Keyboard(data[0],data[3].trim(),data[4].trim(),Connection.valueOf(data[5].trim().toUpperCase()),Integer.parseInt(data[6].trim()),Double.parseDouble(data[8].trim()),KeyboardType.valueOf(data[2].trim().toUpperCase()),KeyboardLayout.valueOf(data[9].trim()));
					if(p.getStock()>0) {
						prods.add(p);
					}
					break;
				}
			}
		}catch(IOException ex) {
			
		}
		return prods;
	}
	
	public ArrayList<Product> readAdmin(){ //Returns a Products list containing all products with the original cost
		ArrayList<Product> prods = new ArrayList<>(); 
		try {
			BufferedReader br = getBufferedReader();
			String line=null;
			Product p;
			while((line=br.readLine())!=null) { //Each time while statement is run, set String line to the next line in txt file, and run the while, while this line is not null
				String data[] = line.split(",");
				switch(data[1].trim()) { //This is where either "keyboard" or "mouse" is
				case "mouse":  //If the product is a mouse
					p = new Mouse(data[0],data[3].trim(),data[4].trim(),Connection.valueOf(data[5].trim().toUpperCase()),Integer.parseInt(data[6].trim()),Double.parseDouble(data[7].trim()),Double.parseDouble(data[8].trim()),MouseType.valueOf(data[2].trim().toUpperCase()),Integer.parseInt(data[9].trim())); //Create mouse object with original cost
					prods.add(p); //add to array
					break;
				case "keyboard": //If the product is a keyboard
					p = new Keyboard(data[0],data[3].trim(),data[4].trim(),Connection.valueOf(data[5].trim().toUpperCase()),Integer.parseInt(data[6].trim()),Double.parseDouble(data[7].trim()),Double.parseDouble(data[8].trim()),KeyboardType.valueOf(data[2].trim().toUpperCase()),KeyboardLayout.valueOf(data[9].trim())); //Create keyboard object with originalCost
					prods.add(p); //add to array
					break;
				}
			}
		}catch(IOException ex) {
			
		}
		return prods;
	}
}
