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
//		EdepotItems.insertEdepotItem(1,"firstitem", stmt);
//		EdepotItems.insertEdepotItem(2,"seconditem", stmt);
//		EdepotItems.printall(stmt);
//		EdepotItems.getNamebyStockNO(2, stmt);
//		EdepotItems.getNamebyStockNO(1, stmt);
//		EdepotItems.removebystockno(2,stmt);
//		EdepotItems.printall(stmt);
		
//		EmartCustomers.dropEmartCustomer(stmt);
//		createTable(EmartCustomers.create_table_sql,stmt);
//		EmartCustomers.insertEmartCustomer(200, "Brenda", stmt);
//		EmartCustomers.insertEmartCustomer(10, "Ramon", stmt);
//		EmartCustomers.insertEmartCustomer(50, "Alex", stmt);
//		EmartCustomers.removeByCustomerID(10, stmt);
//		EmartCustomers.printall(stmt);
//		EmartCustomers.updateStatus(10, "Gold", stmt);
//		EmartCustomers.printall(stmt);
//		EmartCustomers.customerStatus(10,stmt);

//		EmartItems.dropEmartItems(stmt);
//		createTable(EmartItems.create_table_sql,stmt);

//		EmartCart.getStatusDiscount(10,stmt);
//		int a =EmartCart.calculateGrantCartTotal(EmartCart.cartTotalWithoutTaxOrShipping(stmt), EmartCart.getStatusDiscount(stmt), EmartCart.getShippingPcnt(stmt));
//		System.out.println("Grand Total: "+ a);
//		EmartCart.dropEmartCart(stmt);
		
//		System.out.println(EmartAccessories.create_table_sql);
//		createTable(EmartAccessories.create_table_sql, stmt);
//		EmartAccessories.insertAccessory(10, 20, stmt);
//		EmartAccessories.deleteAccessory(10, 30, stmt);
//		EmartAccessories.printAccessory(10,stmt);
		
//		System.out.println(EmartPreviousOrders.create_table_sql);
//		createTable(EmartPreviousOrders.create_table_sql,stmt);
//		EmartPreviousOrders.dropEmartPreviousOrders(stmt);
//		EmartPreviousOrders.insertPreviousOrder(101, 10, 20, 3, "1/1/14", 50, stmt);
		EmartPreviousOrders.printall(stmt);
		conn.close();
	}
	
	public static void resetDB(Statement stmt){
		EmartCart.dropEmartCart(stmt);
		EdepotItems.dropEdepotItem(stmt);
		EmartCustomers.dropEmartCustomer(stmt);
		createTable(EdepotItems.create_table_sql, stmt);
		createTable(EmartCustomers.create_table_sql,stmt);	
		createTable(EmartCart.create_table_sql,stmt);
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
