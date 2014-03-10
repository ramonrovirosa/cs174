import java.io.IOException;
import java.sql.*;


public class Main {
	static Connection conn;
	static String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	static String strUsername = "ramonrovirosa";
	static String strPassword = "4935854";
	public static void main(String[] args) throws SQLException, IOException {
		// 1. Load the Oracle JDBC driver for this program
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		}
		catch ( Exception e){ 
			e.printStackTrace(); 
		}		
		// 2. Test functions for each query
		print_all();
	}
	public static void print_all() throws SQLException, IOException{
		// Connect to the database

		conn = DriverManager.getConnection(strConn,strUsername,strPassword);

		// Create a Statement
		Statement stmt = conn.createStatement();

		//create EdepotItems table
//		EdepotItems.dropEdepotItem(stmt);
//		createTable(EdepotItems.create_table_sql, stmt);
//		EdepotItems.insertEdepotItem(stockno, name, stmt)
//		EdepotItems.insertEdepotItem(2,"seconditem", stmt);
//		EdepotItems.printall(stmt);
//		EdepotItems.getNamebyStockNO(2, stmt);
//		EdepotItems.getNamebyStockNO(1, stmt);
//		EdepotItems.removebystockno(2,stmt);
//		EdepotItems.printall(stmt);
		
//		EmartCustomers.dropEmartCustomer(stmt);
//		createTable(EmartCustomers.create_table_sql,stmt);
//		EmartCustomers.insertEmartCustomer("Rhagrid", "Rubeus Hagrid", "Rhagrid", "rhagrid@cs","123 MyStreet, Goleta apt A, Ca", 0, stmt);
//		EmartCustomers.insertEmartCustomer("Mhooch", "Madam Hooch", "Mhooch", "mhooch@cs","123 MyStreet, Goleta apt B, Ca", 0, stmt);
//		EmartCustomers.insertEmartCustomer("Amoody", "Alastor Moody", "Amoody", "amoody@cs","123 MyStreet, Goleta apt C, Ca", 0, stmt);
//		EmartCustomers.insertEmartCustomer("Pquirrell", "Professor Quirrell", "Pquirrell", "pquirrell@cs","123 MyStreet, Goleta apt D, Ca", 0, stmt);
//		EmartCustomers.insertEmartCustomer("Sblack", "Alastor Moody", "Sblack", "sblack@cs","123 MyStreet, Goleta apt E, Ca", 1, stmt);
//		EmartCustomers.insertEmartCustomer("Ddiggle", "Dedalus Diggle", "Ddiggle", "ddiggle@cs","123 MyStreet, Goleta apt F, Ca", 0, stmt);
//		EmartCustomers.removeByCustomerID(10, stmt);
//		EmartCustomers.printall(stmt);
//		EmartCustomers.updateStatus("Rhagrid", "Gold", stmt);
//		EmartCustomers.updateStatus("Mhooch", "Silver", stmt);
//		EmartCustomers.updateStatus("Sblack", "Green", stmt);
//		EmartCustomers.updateStatus("Ddiggle","Green", stmt);
//		EmartCustomers.printall(stmt);
//		EmartCustomers.customerStatus(10,stmt);

//		EmartItems.dropEmartItems(stmt);
//		createTable(EmartItems.create_table_sql,stmt);
//		EmartItems.insertEmartItem("101", "Laptop", "HP", "6111", "Processer speed: 3.33Ghz, Ram size: 512 Mb, Hard disk size: 100Gb, Display Size: 17\"", 12, 1630, 1, 2, 10, "A9", stmt);
//		EmartItems.printall(stmt);
//		EmartItems.searchEmartItem("101", stmt);
//		EmartItems.updateItemQuantityAdd("101", 5, stmt);
//		EmartItems.printall(stmt);
//		EmartItems.updateItemQuantitySubtract("101", 5, stmt);
//		EmartItems.printall(stmt);
//		EmartItems.updateQuantity("101", 4, stmt);
		EmartItems.updatePrice("101", 1630, stmt);
		EmartItems.printall(stmt);
		
//		EmartCart.getStatusDiscount(10,stmt);
//		int a =EmartCart.calculateGrantCartTotal(EmartCart.cartTotalWithoutTaxOrShipping(stmt), EmartCart.getStatusDiscount(stmt), EmartCart.getShippingPcnt(stmt));
//		System.out.println("Grand Total: "+ a);
//		EmartCart.dropEmartCart(stmt);
		
		//System.out.println(EmartAccessories.create_table_sql);
//		createTable(EmartAccessories.create_table_sql, stmt);
//		EmartAccessories.insertAccessory(10, 20, stmt);
//		EmartAccessories.deleteAccessory(10, 30, stmt);
		//EmartAccessories.printAccessory(10,stmt);
//		ConsoleUI.initialPrompt(stmt);
		
//		EmartItems.dropEmartItems(stmt);
		
//		System.out.println(EmartPreviousOrders.create_table_sql);
//		createTable(EmartPreviousOrders.create_table_sql,stmt);
//		EmartPreviousOrders.dropEmartPreviousOrders(stmt);
//		EmartPreviousOrders.insertPreviousOrder(101, 10, 40, 100, "2014-01-12", 500, stmt);
//		EmartPreviousOrders.printall(stmt);
//		EmartPreviousOrders.findPreviousOrdersByDate("2015-01-12", "2010-01-12",stmt);
//		EmartPreviousOrders.deletePreviousOrders(101, 20, stmt);
		
//		resetDB(stmt);
		
		conn.close();
	}
	
	public static void resetDB(Statement stmt){
		EmartCart.dropEmartCart(stmt);
		EmartPreviousOrders.dropEmartPreviousOrders(stmt);
		EmartCustomers.dropEmartCustomer(stmt);
	}
	
	public static void createTable(String sql, Statement stmt){
		try{
		stmt.executeUpdate(sql);
		System.out.println("Created table in given database...");
	   }catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}     
}


