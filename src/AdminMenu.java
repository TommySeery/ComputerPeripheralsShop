import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class AdminMenu extends JFrame{

	private JPanel contentPane;
	private JTextField txBarcode = new JTextField();;
	private JTextField txBrand = new JTextField();
	private JPanel pnAdditionalInfo = new JPanel();
	private JPanel tbAddProduct = new JPanel();
	private JTextField txOriginalCost;
	private JTextField txRetailPrice;
	private JComboBox cmbKeyboardType = new JComboBox();
	private JComboBox cmbKeyboardLayout = new JComboBox();
	private JComboBox cmbMouseType = new JComboBox();
	private JSpinner spnButtons = new JSpinner();
	private JComboBox cmbColour = new JComboBox();
	private JComboBox cmbConnectivity = new JComboBox();
	private JSpinner spnStock = new JSpinner();
	private DefaultTableModel dtmProducts = new DefaultTableModel();
	private ArrayList<Product> products;
	private JTable tblProducts = new JTable();
	private ProductReader pr = new ProductReader("Stock.txt");
	private ButtonGroup bg = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	
	public void reset(Product p) {
		clearPanel(pnAdditionalInfo);
		updateProducts(p);
		bg.clearSelection();
		txBarcode.setText("");
		txBrand.setText("");
		txOriginalCost.setText("");
		txRetailPrice.setText("");
		spnStock.setValue(0);
		cmbKeyboardType.setSelectedItem("");
		cmbKeyboardLayout.setSelectedItem("");
		cmbMouseType.setSelectedItem("");
		cmbColour.setSelectedItem("");
		cmbConnectivity.setSelectedItem("");
		spnButtons.setValue(0);
	}
	
	public AdminMenu(Admin admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		products = pr.readAdmin();
		RetailPriceCompare pc = new RetailPriceCompare();
		Collections.sort(products,pc);
		
		dtmProducts.setColumnIdentifiers(new Object[] {"id", "product", "brand", "colour", "connection", "stock", "original", "retail", "type", "layout", "buttons"}); //Create the columns for the table, which now include original cost as well
		setTable(); //Set up the table
		
		cmbKeyboardType.addItem("");
		cmbKeyboardType.addItem("Standard");
		cmbKeyboardType.addItem("Flexible");
		cmbKeyboardType.addItem("Gaming");
		
		cmbKeyboardLayout.addItem("");
		cmbKeyboardLayout.addItem("UK");
		cmbKeyboardLayout.addItem("US");
		
		cmbMouseType.addItem("");
		cmbMouseType.addItem("Standard");
		cmbMouseType.addItem("Gaming");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 669, 534);
		contentPane.add(tabbedPane);
		
		JPanel tbViewProducts = new JPanel();
		tabbedPane.addTab("View Products", null, tbViewProducts, null);
		tbViewProducts.setLayout(null);
		
		
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame main = new MainFrame();
				AdminMenu.this.setVisible(false);
				tabbedPane.removeAll();
				main.setVisible(true);
			}
		});
		btnLogout.setBounds(10, 472, 89, 23);
		tbViewProducts.add(btnLogout);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 644, 450);
		tbViewProducts.add(scrollPane);
		
		scrollPane.setViewportView(tblProducts);
		
		tblProducts.setModel(dtmProducts);
		
		tabbedPane.addTab("Add Product", null, tbAddProduct, null);
		
		JRadioButton rbKeyboard = new JRadioButton("Keyboard");
		rbKeyboard.setBounds(25, 46, 109, 23);
		rbKeyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renderKeyboard(); //Add keyboard information to the additional info panel
			}
		});
		tbAddProduct.setLayout(null);
		tbAddProduct.add(rbKeyboard);

		
		JRadioButton rbMouse = new JRadioButton("Mouse");
		rbMouse.setBounds(25, 72, 109, 23);
		rbMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renderMouse(); //Add mouse informatiuon to the additional info panel
			}
		});
		tbAddProduct.add(rbMouse);
		
		bg.add(rbKeyboard);
		bg.add(rbMouse);
		
		JLabel lblProductType = new JLabel("Select Product Type");
		lblProductType.setBounds(29, 25, 105, 14);
		tbAddProduct.add(lblProductType);
		
		txBarcode.setBounds(530, 22, 120, 20);
		tbAddProduct.add(txBarcode);
		txBarcode.setColumns(10);
		
		JLabel lblBarcode = new JLabel("Barcode");
		lblBarcode.setBounds(419, 25, 81, 14);
		tbAddProduct.add(lblBarcode);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(419, 76, 81, 14);
		tbAddProduct.add(lblBrand);
		
		JLabel lblColour = new JLabel("Colour");
		lblColour.setBounds(419, 129, 81, 14);
		tbAddProduct.add(lblColour);
		
		JLabel lblConnectivity = new JLabel("Connectivity");
		lblConnectivity.setBounds(419, 175, 101, 14);
		tbAddProduct.add(lblConnectivity);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(419, 227, 81, 14);
		tbAddProduct.add(lblStock);
		
		JLabel lblOriginalCost = new JLabel("Original Cost");
		lblOriginalCost.setBounds(419, 275, 120, 14);
		tbAddProduct.add(lblOriginalCost);
		
		JLabel lblRetailPrice = new JLabel("Retail Price");
		lblRetailPrice.setBounds(419, 322, 120, 14);
		tbAddProduct.add(lblRetailPrice);
		
		txBrand.setBounds(531, 73, 119, 20);
		tbAddProduct.add(txBrand);
		txBrand.setColumns(10);
		
		cmbColour.setBounds(529, 125, 121, 22);
		cmbColour.addItem("");
		cmbColour.addItem("red");
		cmbColour.addItem("orange");
		cmbColour.addItem("yellow");
		cmbColour.addItem("green");
		cmbColour.addItem("blue");
		cmbColour.addItem("purple");
		cmbColour.addItem("black");
		cmbColour.addItem("white");
		tbAddProduct.add(cmbColour);
		
		cmbConnectivity.setBounds(530, 171, 120, 22);
		cmbConnectivity.addItem("");
		cmbConnectivity.addItem("wired");
		cmbConnectivity.addItem("wireless");
		tbAddProduct.add(cmbConnectivity);
		
		spnStock.setBounds(604, 224, 46, 20);
		tbAddProduct.add(spnStock);
		
		JLabel lblPound1 = new JLabel("\u00A3");
		lblPound1.setBounds(593, 275, 12, 14);
		tbAddProduct.add(lblPound1);
		
		JLabel lblPound2 = new JLabel("\u00A3");
		lblPound2.setBounds(593, 322, 12, 14);
		tbAddProduct.add(lblPound2);
		pnAdditionalInfo.setBounds(419, 350, 231, 116);
		tbAddProduct.add(pnAdditionalInfo);
		
		JLabel lblError = new JLabel("");
		lblError.setBounds(25, 102, 218, 87);
		lblError.setForeground(Color.RED);
		pnAdditionalInfo.setLayout(null);
		tbAddProduct.add(lblError);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(571, 477, 89, 23);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product p;
				try {
				if(txBarcode.getText().length()==6 && isInt(txBarcode.getText()) && (int)spnStock.getValue()>=0 && Double.parseDouble(txOriginalCost.getText())>0 && Double.parseDouble(txRetailPrice.getText())>0){ //Check that the input data is valid
				if(rbKeyboard.isSelected()) { //If keyboard was the radio buton selected
					p = new Keyboard(txBarcode.getText(),txBrand.getText(),cmbColour.getSelectedItem().toString(),Connection.valueOf(cmbConnectivity.getSelectedItem().toString().toUpperCase()),Integer.parseInt(spnStock.getValue().toString()),Double.parseDouble(txOriginalCost.getText()),Double.parseDouble(txRetailPrice.getText()), KeyboardType.valueOf(cmbKeyboardType.getSelectedItem().toString().toUpperCase()), KeyboardLayout.valueOf(cmbKeyboardLayout.getSelectedItem().toString().toUpperCase())); //Create keyboard object with original cost
					if(!productExists(p)) { //Provided that the products barcode hasnt already been added to the stock
						lblError.setText("");
						addProduct(p); //Add the product to the stock
					}else {
						lblError.setText("<html>Product already<br>exists in the<br>database</html>");
					}
				}else if(rbMouse.isSelected()) { //If mouse was the radio button selected
					if((int)spnButtons.getValue()>0) {
						p = new Mouse(txBarcode.getText(),txBrand.getText(),cmbColour.getSelectedItem().toString(),Connection.valueOf(cmbConnectivity.getSelectedItem().toString().toUpperCase()),Integer.parseInt(spnStock.getValue().toString()),Double.parseDouble(txOriginalCost.getText()),Double.parseDouble(txRetailPrice.getText()), MouseType.valueOf(cmbMouseType.getSelectedItem().toString().toUpperCase()), Integer.parseInt(spnButtons.getValue().toString())); //Create mouse object with original cost
						if(!productExists(p)) {
							lblError.setText("");
							addProduct(p);
						}else {
							lblError.setText("<html>Product already<br>exists in the<br>database</html>");
						}
					}else {
						lblError.setText("<html>Please ensure you<br>have enterted the correct info</html>");
					}
				}else { //If neither radio button was selected
					lblError.setText("<html>Please ensure you<br>have selected either mouse<br>or keyboard</html>");
				}
				}else {
					lblError.setText("<html>Please ensure you<br>have enterted the correct info</html>");
				}
				}catch(Exception ex) {
					lblError.setText("<html>Please ensure you<br>have enterted the correct info</html>");
				}
			}
		});
		tbAddProduct.add(btnSubmit);
		
		txOriginalCost = new JTextField();
		txOriginalCost.setBounds(603, 272, 47, 20);
		tbAddProduct.add(txOriginalCost);
		txOriginalCost.setColumns(10);
		
		txRetailPrice = new JTextField();
		txRetailPrice.setBounds(603, 319, 47, 20);
		tbAddProduct.add(txRetailPrice);
		txRetailPrice.setColumns(10);
		
		JButton btnLogout2 = new JButton("Logout");
		btnLogout2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame main = new MainFrame();
				AdminMenu.this.setVisible(false);
				tabbedPane.removeAll();
				main.setVisible(true);
			}
		});
		btnLogout2.setBounds(10, 477, 89, 23);
		tbAddProduct.add(btnLogout2);
	}
	
	public boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException ex) {
			return false;
		}
	}
	
	public void addProduct(Product p){
		ProductWriter pw = new ProductWriter("Stock.txt",p); //Create product writer for the one product to add
		pw.append(); //Apend to text file
		int response = JOptionPane.showConfirmDialog(new JFrame(), "Product was added Successfully!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);  //Confirmation tab
		if(response == JOptionPane.DEFAULT_OPTION || response== JOptionPane.OK_OPTION) { //If users presses CLOSE or OK
			reset(p);
		}
	}

	public boolean productExists(Product p) {
		for(Product prod: products) {
			if(prod.getBarcode().equals(p.getBarcode())) {
				//If the barcodes match, return true
				return true;
			}
		}
		//Returns false if none of the barcodes match
		return false;
	}
	
	public void renderKeyboard() {
		clearPanel(pnAdditionalInfo);
		
		JLabel lblType = new JLabel("Keyboard Type");
		lblType.setBounds(0, 10, 100, 14);
		pnAdditionalInfo.add(lblType);
		
		cmbKeyboardType.setBounds(110, 10, 121, 22);
		pnAdditionalInfo.add(cmbKeyboardType);
		
		JLabel lblLayout = new JLabel("Keyboard Layout");
		lblLayout.setBounds(0, 70, 100, 14);
		pnAdditionalInfo.add(lblLayout);
		
		cmbKeyboardLayout.setBounds(110, 70, 121, 22);
		pnAdditionalInfo.add(cmbKeyboardLayout);
	}
	
	public void renderMouse() {
		clearPanel(pnAdditionalInfo);
		
		JLabel lblType = new JLabel("Mouse Type");
		lblType.setBounds(0, 20, 100, 14);
		pnAdditionalInfo.add(lblType);
		
		cmbMouseType.setBounds(110, 20, 121, 22);
		pnAdditionalInfo.add(cmbMouseType);
		
		JLabel lblButton = new JLabel("No of Buttons");
		lblButton.setBounds(0, 70, 100, 14);
		pnAdditionalInfo.add(lblButton);
		
		spnButtons.setBounds(185, 70, 46, 20);
		pnAdditionalInfo.add(spnButtons);
	}
	
	public void clearPanel(JPanel panel) {
		//setVisible(false) and setVisible(true) act as a 'refresh' for the display
		panel.setVisible(false);
		panel.removeAll();
		panel.setVisible(true);
	}
	
	public void updateProducts(Product p) {
		products.add(p); //Add product
		RetailPriceCompare pc = new RetailPriceCompare();
		Collections.sort(products,pc); //Sort products list
		setTable(); //Recreate the table with the new arraylist
	}
	
	public void setTable() { 
		dtmProducts.setRowCount(0); //Removes old table elements
		
		//Add new sorted elements to table
		for(Product p : products) {
			Object row[];
				if(p instanceof Keyboard) { //If product is keyboard
					Keyboard k = (Keyboard)p; //Cast to Keyboard
					row = new Object[] {k.getBarcode(),k.getProduct(),k.getBrand(),k.getColour(),k.getConnectivity().toString(),k.getStock(),k.getOriginalCostDisplay(),k.getRetailPriceDisplay(),k.getType().toString(),k.getLayout().toString().toUpperCase(),null}; //Add keyboard data to row
					dtmProducts.addRow(row);
				}if(p instanceof Mouse) { //If product is mouse
					Mouse m = (Mouse)p; //cast to Mouse
					row = new Object[] {m.getBarcode(),m.getProduct(),m.getBrand(),m.getColour(),m.getConnectivity().toString(),m.getStock(),m.getOriginalCostDisplay(),m.getRetailPriceDisplay(),m.getType().toString(),null,m.getButtons()}; //Add mouse data to row
					dtmProducts.addRow(row);
				}
			}		
	}
}

	
