import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ProductWriter{
	private String dir;
	private ArrayList<Product> prods;
	private Product prod;
	
	public ProductWriter(String dir, ArrayList<Product> prods){ //Constructor for writing multiple products to text file
		this.dir=dir;
		this.prods=prods;
	}
	
	public ProductWriter(String dir, Product prod) { //Constructor for appending one product to text file
		this.dir=dir;
		this.prod=prod;
	}
	
	public BufferedWriter getBufferedWriter(boolean append) {
		try {
		FileWriter fw = new FileWriter(dir,append);
		BufferedWriter bw = new BufferedWriter(fw);
		return bw;
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public void write() {
		BufferedWriter bw = getBufferedWriter(false);
		try {
		for(Product p: prods) {
			bw.write(p.toString()+"\n");
		}
			bw.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void append() {
		String line = "";
		if(prod instanceof Keyboard) {
			line+=((Keyboard)prod).toString();
		}else if(prod instanceof Mouse) {
			line+=((Mouse)prod).toString();
		}
		BufferedWriter bw = getBufferedWriter(true);
		try {
			bw.append(line+"\n");
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
