import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import java.text.NumberFormat;

public class CustomerMenu extends JFrame {

	public JPanel contentPane = new JPanel();
	private DefaultTableModel dtmProducts;
	private DefaultListModel dlmProducts = new DefaultListModel();
	private JTable tblProducts = new JTable();
	private JList list;
	private TableRowSorter trs;
	private JPanel tbCheckout = new JPanel();
	static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JLabel lblShowTotal = new JLabel("£0.00");
	private JLabel lblShowTotal2 = new JLabel("£0.00");
	private JLabel[] lblErrors = {new JLabel(""),new JLabel(""),new JLabel("")};
	private ProductReader pr = new ProductReader("Stock.txt");
	
	private ArrayList<Product> products;
	
	public void reset(Customer cust) {
		tabbedPane.remove(2);
		clearBasket(cust);
		dlmProducts.removeAllElements();
	}

	/**
	 * Create the frame.
	 */
	public CustomerMenu(Customer cust) {
		products = pr.readCustomer();
		sortProducts();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane.setBounds(0, 0, 879, 432);
		contentPane.add(tabbedPane);
		
		dtmProducts = new DefaultTableModel();
		dtmProducts.setColumnIdentifiers(new Object[] {"id", "product", "brand", "colour", "connection", "stock", "retail", "type", "layout", "buttons"}); //Set the columns for the container of the JTable
		
		setTable();
		
		lblErrors[0].setForeground(Color.RED);
		lblErrors[0].setBounds(750, 208, 108, 58);
		
		lblErrors[2].setBounds(402, 40, 117, 53);
		lblErrors[2].setForeground(Color.RED);
		
		lblErrors[1].setBounds(741, 307, 117, 53);
		lblErrors[1].setForeground(Color.RED);
		
		JPanel tbViewProducts = new JPanel();
		tabbedPane.addTab("View Products", null, tbViewProducts, null);
		tbViewProducts.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 646, 382);
		tbViewProducts.add(scrollPane);
		tbViewProducts.add(lblErrors[0]);
		
		lblShowTotal.setBounds(818, 142, 46, 14);
		tbViewProducts.add(lblShowTotal);
		
		scrollPane.setViewportView(tblProducts);
		tblProducts.setModel(dtmProducts);
		
		JButton btnAdd = new JButton("Add to Basket");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblErrors[0].setText("");
					String barcode = (String)tblProducts.getValueAt(tblProducts.getSelectedRow() , 0); //Gets the row that the user clicked on after pressed 'Add to Basket'
					int i = 0; //Stores the position of the object that matches the barcode above
					Product p=null;
					for(Product prod : products) {
							if(prod.getBarcode().equals(barcode)) {
								p=prod;
								break;
							}else {
								//If the barcodes dont match, increment the arraylist pointer
								i++;
						}
					}
					if(cust.addItem(p)) {
						dlmProducts.addElement(p.toDisplayString()); //Adds element to JList container
						dtmProducts.setValueAt(p.getStock(), i, 5); //Updates row i, column 5 to the stock value of Product p
						lblErrors[0].setText("");
						lblErrors[1].setText("");
						lblShowTotal.setText("£"+cust.getDisplayTotal()); //Update totals
						lblShowTotal2.setText("£"+cust.getDisplayTotal());
					}else {
						lblErrors[0].setText("Out of Stock");
					}
				}catch(ArrayIndexOutOfBoundsException ex) {
					lblErrors[0].setText("<html>Please Select<br>an Item.</html>"); //If the user didnt click anything, so p stays as null
				}
			}
		});
		btnAdd.setBounds(756, 174, 108, 23);
		tbViewProducts.add(btnAdd);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(666, 12, 46, 14);
		tbViewProducts.add(lblBrand);
		
		JLabel lblButtons = new JLabel("<html>No of Mouse<br>Buttons</html>");
		lblButtons.setBounds(666, 47, 116, 28);
		tbViewProducts.add(lblButtons);
		
		JTextField txButtons = new JTextField();
		txButtons.setBounds(833, 48, 31, 20);
		tbViewProducts.add(txButtons);
		txButtons.setColumns(10);
		
		JTextField txBrand = new JTextField();
		txBrand.setBounds(756, 9, 108, 20);
		tbViewProducts.add(txBrand);
		txBrand.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txBrand.getText().length()>0 && txButtons.getText().length()>0) { //If both filters are selected
					addFilter(txBrand.getText(),2,txButtons.getText(),9); //Combine both filters and rows into one compound filter
				}else {
					if(txBrand.getText().length()>0) { //If the brand filter is selected
						addFilter1(txBrand.getText(),2); //Create a filter for the value of drop down, along with row that youre filtering
					}else if(txButtons.getText().length()>0) { //If the no of buttons filter is selected
						addFilter2(txButtons.getText(),9); //Create a filter for the value of buttons, along with row that youre filtering
					}else { //If neither of the filters are selected
						removeFilters();
				}
				
				}
				
				
				
			}
		});
		btnSearch.setBounds(775, 79, 89, 23);
		tbViewProducts.add(btnSearch);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame main = new MainFrame(); //Creates main frame object
				CustomerMenu.this.setVisible(false); //Customer menu invisible
				tabbedPane.removeAll(); //Remove all tabs to prevent them from being duplicated if the user relogs in
				main.setVisible(true); //Main menu visible
			}
		});
		btnLogout.setBounds(756, 359, 108, 23);
		tbViewProducts.add(btnLogout);
		
		JLabel lblTotal = new JLabel("Total Cost:");
		lblTotal.setBounds(666, 142, 73, 14);
		tbViewProducts.add(lblTotal);
		
		JPanel tbViewBasket = new JPanel();
		tabbedPane.addTab("View Basket", null, tbViewBasket, null);
		tbViewBasket.setLayout(null);
		
		JLabel lblTotal2 = new JLabel("Total Cost:");
		lblTotal2.setBounds(698, 235, 80, 14);
		tbViewBasket.add(lblTotal2);
		
		lblShowTotal2.setBounds(818, 235, 46, 14);
		tbViewBasket.add(lblShowTotal2);
		tbViewBasket.add(lblErrors[1]);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 678, 382);
		tbViewBasket.add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		
		list.setModel(dlmProducts);
		
		JButton btnClear = new JButton("Clear Basket");
		btnClear.setBounds(747, 11, 117, 23);
		tbViewBasket.add(btnClear);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cust.getItems().size()>0) { //Checks if user has added anything to basket
					tabbedPane.addTab("Checkout", null, tbCheckout, null);
					tbCheckout.setLayout(null);
					displayPaymentMethod(cust);
					lblErrors[0].setText("");
					lblErrors[1].setText("");
				}else {
					lblErrors[1].setText("<html>Please add something to <br>your basket first.<html>"); //Otherwise, throws error
				}
			}
		});
		btnCheckout.setBounds(747, 276, 117, 23);
		tbViewBasket.add(btnCheckout);
		
		JButton btnLogout2 = new JButton("Logout");
		btnLogout2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame main = new MainFrame();
				CustomerMenu.this.setVisible(false);
				tabbedPane.removeAll();
				main.setVisible(true);
			}
		});
		btnLogout2.setBounds(747, 360, 117, 23);
		tbViewBasket.add(btnLogout2);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearBasket(cust);
			}
		});
		}
	

	
	public void addFilter(String v1, int col1, String v2, int col2) { //Both filters applied
		DefaultTableModel dtmProducts = (DefaultTableModel)tblProducts.getModel(); //Creates temporary model
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmProducts); //Creates table sorter
		tblProducts.setRowSorter(sorter); //Sets tables sorter
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(3);
        filters.add(RowFilter.regexFilter("(?i).*"+v1+".*",col1)); //This regex pattern only needs to contain part of the string not the whole thing
        filters.add(RowFilter.regexFilter("(?:^|\\W)"+v2+"(?:$|\\W)",col2)); //This is the regex for no of buttons has to be the exact no, so uses a more specific regex
        RowFilter<Object,Object> filterList = RowFilter.andFilter(filters); //Creates filter list
        sorter.setRowFilter(filterList); //Applies filter list to sorter, which was applied to table
	}
	
	public void addFilter1(String v, int col) { //Only 1 filter applied. 
		DefaultTableModel dtmProducts = (DefaultTableModel)tblProducts.getModel();
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmProducts);
        sorter.setRowFilter(RowFilter.regexFilter("(?i).*"+v+".*", col)); //This regex pattern only needs to contain part of the string not the whole thing
        tblProducts.setRowSorter(sorter); //Applies sorter to table
	}
	
	public void addFilter2(String v, int col) { //Only 1 filter applied
		DefaultTableModel dtmProducts = (DefaultTableModel)tblProducts.getModel();
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmProducts);
        sorter.setRowFilter(RowFilter.regexFilter("(?:^|\\W)"+v+"(?:$|\\W)",col)); //This is the regex for no of buttons has to be the exact no, so uses a more specific regex
        tblProducts.setRowSorter(sorter); //Applies sorter to table
	}
	
	public void removeFilters() { //Neither filter (i.e. Both boxes have no value in them)
		//Removes all filters and sets the dtm back to original
		DefaultTableModel dtmProducts = (DefaultTableModel)tblProducts.getModel();
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmProducts); //Creates empty sorter
		tblProducts.setRowSorter(sorter); //Applies empty sorter to table
	}

	public void displayPaymentMethod(Customer cust) {
		clearPanel(tbCheckout); //Clears previous elements on screen before rendering these
		JLabel lblPaymentMethod = new JLabel("Select your payment method");
		lblPaymentMethod.setBounds(50, 30, 200, 14);
		tbCheckout.add(lblPaymentMethod);
		
		JRadioButton rbPayPal = new JRadioButton("PayPal");
		rbPayPal.setBounds(50, 50, 109, 23);
		tbCheckout.add(rbPayPal);
		JRadioButton rbCard = new JRadioButton("Credit Card");
		rbCard.setBounds(50, 70, 109, 23);
		tbCheckout.add(rbCard);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbPayPal);
		bg.add(rbCard);
		
		JButton btnSelect = new JButton("Select");
		
		btnSelect.setBounds(50, 100, 89, 23);
		tbCheckout.add(btnSelect);
		
		
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checks which radio button was pressed and goes to whichever function
				if(rbPayPal.isSelected()) {
					displayPayPal(cust);
				}else if(rbCard.isSelected()) {
					displayCard(cust);
				}
			}
		});

	}
	
	public void displayPayPal(Customer cust) {
		clearPanel(tbCheckout);
		JLabel lblPaymentMethod = new JLabel("Enter your email");
		lblPaymentMethod.setBounds(50, 30, 200, 14);
		tbCheckout.add(lblPaymentMethod);
		
		JTextField txEmail = new JTextField();
		txEmail.setBounds(50, 50, 200, 23);
		tbCheckout.add(txEmail);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(50, 100, 89, 23);
		tbCheckout.add(btnSelect);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(150, 100, 89, 23);
		tbCheckout.add(btnBack);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPaymentMethod(cust);
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; //regex pattern for valid email
				Pattern pat = Pattern.compile(regex); //Applies regex to pattern object
				Matcher match = pat.matcher(txEmail.getText()); //Create matcher to check whether string matches the regex
				boolean isMatch = match.find(); //Boolean which returns true if email is valid
				if(isMatch) { //email validation
					processPayment(cust,txEmail.getText());
				}else {
					tbCheckout.add(lblErrors[2]); //Add it back to panel since panel was cleared above
					lblErrors[2].setText("Invalid email");
				}
			}
		});
	}
	
	public void displayCard(Customer cust) {
		clearPanel(tbCheckout);
		JLabel lblCardNo = new JLabel("Enter your 6 digit card no.");
		lblCardNo.setBounds(50, 30, 200, 14);
		tbCheckout.add(lblCardNo);
		
		JTextField txCardNo = new JTextField();
		txCardNo.setBounds(210, 30, 109, 23);
		tbCheckout.add(txCardNo);
		
		JLabel lblSecCode = new JLabel("Enter your security code");
		lblSecCode.setBounds(50, 80, 200, 14);
		tbCheckout.add(lblSecCode);
		
		JTextField txSecCode = new JTextField();
		txSecCode.setBounds(210, 80, 109, 23);
		tbCheckout.add(txSecCode);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(50, 140, 89, 23);
		tbCheckout.add(btnSelect);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(150, 140, 89, 23);
		tbCheckout.add(btnBack);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPaymentMethod(cust);
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isInt(txCardNo.getText()) && isInt(txSecCode.getText()) && txCardNo.getText().length()==6 && txSecCode.getText().length()==3) { //Ensure that theyre both ints and of correct length
					processPayment(cust,txCardNo.getText(),txSecCode.getText());
				}else {
					tbCheckout.add(lblErrors[2]); //Add it back to panel since panel was cleared above
					lblErrors[2].setText("Invalid input(s)");
				}
			}
		});
	}
	
	public boolean isInt(String val) {
		try {
			Integer.parseInt(val); //Attempt to convert to int
			return true; //If no exceptions occur, it must already be an int
		}catch(NumberFormatException ex) {
			//If a NumberFormatException occurs, the data type was not an int
			return false;
		}
	}
	
	public void processPayment(Customer cust, String email) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "GB")); //Number format for pounds
		double amount = 0;
		for(Product p: cust.getItems()) {
			amount+=p.getRetailPrice(); //Sums up a total for each product in the users basket
		}
		if(amount>0) { //If total is greater than 0, then continue with checkout
			String text = nf.format(amount) + " paid using PayPal, and the delivery address is "+cust.getAddress();
			displayConfirmation(cust, text);
		}else { //otherwise, error
			tbCheckout.add(lblErrors[2]);
			lblErrors[2].setText("<html>You need to add<bR>a product to your<br>basket first<html>");
		}
		
	}
	
	public void processPayment(Customer cust, String cardNo, String secCode) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "GB")); 
		double amount = 0;
		for(Product p: cust.getItems()) {
			amount+=p.getRetailPrice();
		}
		if(amount>0) { //If the total is greater than 0, then continue with checkout
			String text = nf.format(amount) + " paid using Credit Card, and the delivery address is "+cust.getAddress();
			displayConfirmation(cust, text);
		}else { //otherwise, error
			tbCheckout.add(lblErrors[2]);
			lblErrors[2].setText("<html>You need to add<bR>a product to your<br>basket first<html>");
		}
		
	}
	
	public void displayConfirmation(Customer cust, String text) {
		int response = JOptionPane.showConfirmDialog(new JFrame(), text, "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);  //Create an option pane to confirm update
		if(response == JOptionPane.DEFAULT_OPTION || response== JOptionPane.OK_OPTION) { //When closed, do the following below operations
			updateProduct(cust);
			products = pr.readCustomer(); //Reread the text file for updated customer files
			sortProducts(); //Sort those products
			reset(cust); //Reset all appropriate values
		}
	}
	
	public void clearBasket(Customer cust) {
		cust.clearBasket();
		dlmProducts.removeAllElements();
		setTable();
		lblErrors[0].setText("");
		lblShowTotal.setText("£0.00");
		lblShowTotal2.setText("£0.00");
	}
	
	public void clearPanel(JPanel panel) {
		//setVisible(flase) followed by setVisible(true) acts as a 'refresh' for the panel
		panel.setVisible(false); //Hides panel content
		panel.removeAll(); //Removes everything
		panel.setVisible(true); //Shows updated panel content
	}
	
	public void setTable() {
		dtmProducts.setRowCount(0);
		for(Product p : products) {
			Object row[];
			if(p.getStock()>0) {
				if(p instanceof Keyboard) { //If product is keyboard
					Keyboard k = (Keyboard)p; //cast to keyboard
					row = new Object[] {k.getBarcode(),k.getProduct(),k.getBrand(),k.getColour(),k.getConnectivity().toString(),k.getStock(),k.getRetailPriceDisplay(),k.getType().toString(),k.getLayout().toString().toUpperCase(),null}; //create a row with values in appropriate columns for keyboard, using null for underfined values for that column
					dtmProducts.addRow(row); //Add row to dtm
				}if(p instanceof Mouse) { //If product is mouse
					Mouse m = (Mouse)p; //cast to mouse
					row = new Object[] {m.getBarcode(),m.getProduct(),m.getBrand(),m.getColour(),m.getConnectivity().toString(),m.getStock(),m.getRetailPriceDisplay(),m.getType().toString(),null,m.getButtons()}; //create a row with values in appropriate columns for mouse, using null for underfined values for that column
					dtmProducts.addRow(row); //add row to dtm
				}
			}		
		}
	}
	
	public void sortProducts() {
		RetailPriceCompare pc = new RetailPriceCompare();
		Collections.sort(products,pc);
	}
	
	
	public void updateProduct(Customer cust) {
		ArrayList<Product> allProds = pr.readAdmin(); //Read products with original cost
		ArrayList<Product> custProds = cust.getItems(); //Get customers basket
		for(Product ap : allProds) {
			for(Product cp: custProds) {
				if(ap.getBarcode().equals(cp.getBarcode())) {
					ap.setStock(cp.getStock()); //updates the stock wherever the barcodes match
				}
			}
		}
		ProductWriter pw = new ProductWriter("Stock.txt",allProds);
		pw.write();	
	}
}



