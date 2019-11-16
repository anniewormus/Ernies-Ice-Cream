import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.swing.*;
import java.util.ArrayList;
public class UEOrders implements ActionListener{

	JLabel firstName;
	JLabel lastName;
	JLabel icecream;
	JLabel toppings;
	JLabel cuporcone;
	static JComboBox flavors;
	static JComboBox tops;
	JButton submit;
	JButton showTable;
	JRadioButton cup;
	JRadioButton cone;
	ButtonGroup bg;
	static JTextField text1;
	static JTextField text2;
	static String opts; 
	static double totalPrice;
	String date;
	
	
	
	String[] ICflavors = {"*choose flavor*", "chocolate", "vanilla", "chocolate chip mint", "rocky road", "strawberry"};
	String[] ICtoppings = {"*choose topping*", "sprinkles", "chocolate syrup", "whipped cream", "pineapple", "bacon bits", "no topping"};
	double[] ICflavPrice = {0, 2.50, 2.50, 3.00, 3.50, 3.00};
	double[] ICtopPrice = {0, 0.25, 0.50, 0.10, 0.50, 1.00, 0.00};
	
	ArrayList<OrderInfo> list = new ArrayList<>();
	JFrame jfrm2;
	JTable jtable;
	String[] headings = { "First Name", "Last Name", "Ice Cream", "Toppings", "Cup or Cone", "Price", "Date"};
	JScrollPane scrollPane;
	boolean tableCreated = false;
	DBConnectionManagerSingleton db;
	Statement state;
	
	public UEOrders() throws Exception{
		db = DBConnectionManagerSingleton.getInstance();
		state = db.getStatement();
		showDatabase();
		//grid layout
		JFrame jfrm = new JFrame("Uncle Ernie's Ice Cream");
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new GridLayout(8, 2));
		jfrm.setSize(500, 250);
		
		jfrm2 = new JFrame("Uncle Ernie's Ice Cream Orders");
		jfrm2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm2.setLayout(new FlowLayout());
		jfrm2.setSize(600, 400);
		updateTable();

		//first and last name input
		firstName = new JLabel("First Name: ");
		lastName = new JLabel("Last Name: ");
		text1 = new JTextField();
		text2 = new JTextField();
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		
		showTable = new JButton("Show Table");
		showTable.addActionListener(this);
		
		icecream = new JLabel("Ice Cream:");

		flavors = new JComboBox(ICflavors);
		flavors.addActionListener(this);

		//topping combo box
		toppings = new JLabel("Toppings: ");
		tops = new JComboBox(ICtoppings);
		tops.addActionListener(this);

		//cup or cone radio buttons
		cuporcone = new JLabel("Cup or Cone: ");
		cup = new JRadioButton("Cup");
		cone = new JRadioButton("Cone");
		bg = new ButtonGroup();
		bg.add(cup);
		bg.add(cone);
		cup.addActionListener(this);
		cone.addActionListener(this);

		//adds stuff to frame
		jfrm.add(firstName);
		jfrm.add(text1);
		jfrm.add(lastName);
		jfrm.add(text2);
		jfrm.add(icecream);
		jfrm.add(flavors);
		jfrm.add(toppings);
		jfrm.add(tops);
		jfrm.add(cuporcone);
		jfrm.add(cup);
		jfrm.add(new JLabel());
		jfrm.add(cone);
		jfrm.add(new JLabel());
		jfrm.add(submit);
		jfrm.add(showTable);

		icecream.setHorizontalAlignment(SwingConstants.RIGHT);

		jfrm.setVisible(true);
		
		
		
	}

	public void actionPerformed(ActionEvent ae) {
		totalPrice = 0.00;
		for(int i = 1; i <= 6; i++){
			if(flavors.getSelectedIndex() == i){
				totalPrice += ICflavPrice[i];
			}
			if(tops.getSelectedIndex() == i){
				totalPrice += ICtopPrice[i];
			}
		}
		opts = "";
		if (cup.isSelected()) {
			opts = "cup";
		} else if (cone.isSelected()){
			opts = "cone";
		}
		String str = ae.getActionCommand();
		if( str.equals("Submit")){
			if( text1.getText().equals("") || text2.getText().equals("")){
				JOptionPane.showMessageDialog(null, "please fill in your name");
			}else  if(flavors.getSelectedIndex() == 0 || tops.getSelectedIndex() == 0){
				JOptionPane.showMessageDialog(null, "please choose your ice cream order");
			}else if (!cup.isSelected() && !cone.isSelected()){
				JOptionPane.showMessageDialog(null, "please choose a cup or cone");
			}else{
				JOptionPane.showMessageDialog(null, "Hi " + text1.getText() + "! \nYour order: " + flavors.getSelectedItem() 
				+ " with " + tops.getSelectedItem() + " in a " + opts + "\nPrice: " + String.format("$%.2f", totalPrice));
					
				OrderInfo ord1 = new OrderInfo(text1.getText(), text2.getText(), flavors.getSelectedItem().toString(), tops.getSelectedItem().toString(),
							opts, totalPrice);
					list.add(ord1);
					updateTable();
					try {
						databaseStuff(ord1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					text1.setText("");
					text2.setText("");
				}
			}else if(str.equals("Show Table")){
				jfrm2.setVisible(true);
				
		}
	}
	public void updateTable() {

		System.out.println("list size is " + list.size());
		
		if (tableCreated) {
			jfrm2.remove(scrollPane);
		}
		tableCreated = true;
		
		//insert other values from database to table
		
		// Create the 2D Object array to store in the table
		Object[][] obj = new Object[list.size()][7];
		for (int i = 0; i < list.size(); i++) {
			obj[i][0] = list.get(i).getFirstName();
			obj[i][1] = list.get(i).getLastName();
			obj[i][2] = list.get(i).getIcecream();
			obj[i][3] = list.get(i).getToppings();
			obj[i][4] = list.get(i).getCupOrCone();
			obj[i][5] = list.get(i).getPrice();
			obj[i][6] = list.get(i).getDate();
		}
		// Create the table
		jtable = new JTable(obj, headings) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane = new JScrollPane(jtable);

		jtable.getTableHeader().setReorderingAllowed(false);
		jtable.setPreferredScrollableViewportSize(new Dimension(500, 300));

		// Add thje Scrolling Pane back to the JFrame
		jfrm2.add(scrollPane);

		// Refresh the Frame if it is already visible.
		if (jfrm2.isVisible()) {
			jfrm2.setVisible(true);

		}
	}

	public void databaseStuff(OrderInfo o) throws Exception{
		
		String updateTable = "insert into customer_orders values('" + o.getFirstName()+ "', '" + o.getLastName() + "', '" + o.getIcecream() + "', '" + o.getToppings() + "', '" + o.getCupOrCone() + "', " + o.getPrice() + ", '" + o.getDate() + "')";
		System.out.println(updateTable);
		state.executeUpdate(updateTable);
		
		
//		while(print.next()){
//			String fname = print.getString("first_name");
//			String lname = print.getString("last_name");
//			String flav = print.getString("flavor");
//			String tops = print.getString("toppings");
//			String cuporcone = print.getString("cuporcone");
//			String price = print.getString("price");
//			String date = print.getString("order_date");
//			System.out.println(fname+" "  + lname + " " + flav +" "+ tops +" "+ cuporcone +" "+ price +" "+ date);
//		}
	}
	public void showDatabase() throws Exception{

		String showTable = "select * from customer_orders";
		ResultSet print = state.executeQuery(showTable);
		
		System.out.println("called show database ");
		
		while(print.next()){
			String fname = print.getString("first_name");
			String lname = print.getString("last_name");
			String flav = print.getString("flavor");
			String tops = print.getString("toppings");
			String cuporcone = print.getString("cuporcone");
			double price = print.getDouble("price");
			String date = print.getString("order_date");
			OrderInfo ord2 = new OrderInfo(fname, lname, flav, tops, cuporcone, price);
			list.add(ord2);
	}
	
	}
	public static void main(String[] args) throws Exception{
		DBConnectionManagerSingleton db = DBConnectionManagerSingleton.getInstance();
		Statement state = db.getStatement();
		//database stuff
		//if table is not created then create it
		//also find a way to bring up past values from table
//
//				String customerOrdersTable = ("create table customer_orders(first_name varchar(50), last_name varchar(50), flavor varchar(30), toppings varchar(30), cuporcone varchar(10), price decimal, order_date varchar(50))");
//				state.executeUpdate(customerOrdersTable);
//				String dropTabl = "drop table customer_orders";
//				state.executeUpdate(dropTabl);		
				

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new UEOrders();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}