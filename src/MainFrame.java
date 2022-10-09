import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txId;
	private JTextField txUser;
	private static MainFrame frame = new MainFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txId = new JTextField();
		txId.setBounds(159, 99, 126, 20);
		contentPane.add(txId);
		txId.setColumns(10);
		
		txUser = new JTextField();
		txUser.setBounds(159, 147, 126, 20);
		contentPane.add(txUser);
		txUser.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(62, 102, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(62, 150, 70, 14);
		contentPane.add(lblUsername);
		
		JLabel lblDbErr = new JLabel("");
		lblDbErr.setForeground(Color.RED);
		lblDbErr.setBounds(306, 130, 211, 14);
		lblDbErr.setVisible(false);
		contentPane.add(lblDbErr);
		
		JLabel lblLoginErr = new JLabel("");
		lblLoginErr.setForeground(Color.RED);
		lblLoginErr.setBounds(306, 105, 211, 14);
		contentPane.add(lblLoginErr);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					int id = Integer.parseInt(txId.getText());
					String username = txUser.getText();
					try {
						String[] data = getUserData(id,username);
						if(data==null) { //If while loop in getUserData() didnt return any data (i.e. user doesnt exist or credentials are incorrect)
							lblLoginErr.setText("Invalid Credentials"); 
						}else {
							lblLoginErr.setText("");
							lblDbErr.setText("");
							MainFrame.this.setVisible(false);
							Address addr = new Address(Integer.parseInt(data[3].trim()),data[4].trim(),data[5].trim()); //Creates address object first
							if(data[6].trim().equals("customer")) { //If the user is a customer
								Customer cust = new Customer(Integer.parseInt(data[0]),data[1].trim(),data[2].trim(),addr); //Adds the rest of the customer information, along with their address
								CustomerMenu custMenu = new CustomerMenu(cust); //Create customer menu
								MainFrame.this.setVisible(false); //open it
								custMenu.setVisible(true);
							}else if(data[6].contains("admin")) { //If the user is an admin
								Admin admin = new Admin(Integer.parseInt(data[0]),data[1].trim(),data[2].trim(),addr); //Adds the rest of the customer information, along with their address
								AdminMenu adMenu = new AdminMenu(admin); //Create admin menu
								MainFrame.this.setVisible(false); 
								adMenu.setVisible(true); //Open it
							}
						}
						}catch(IOException ex) {
							lblDbErr.setText("Error Connecting to Database");
						}
					
				}catch(NumberFormatException ex) {
				lblLoginErr.setText("id must be only numbers");
				}
			}
			
		});
		
		btnLogin.setBounds(196, 209, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblWelcome = new JLabel("Welcome to CAS");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblWelcome.setBounds(89, 39, 259, 38);
		contentPane.add(lblWelcome);
	}
	
	public String[] getUserData(int id, String username) throws IOException{
		FileReader fr = new FileReader("UserAccounts.txt");
		BufferedReader br = new BufferedReader(fr);
		String line=null;
		
		while((line=br.readLine())!=null) {
			String[] data = line.split(",");
			if(Integer.toString(id).equals(data[0])&&(username.equals(data[1].trim()))) {
				return data;
			}
		}
		br.close();
		return null;
		
	}
}

