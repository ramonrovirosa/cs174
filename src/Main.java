import java.io.IOException;
import java.sql.*;


public class Main {
	static Connection conn;
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
		String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String strUsername = "ramonrovirosa";
		String strPassword = "4935854";
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
		
//		ConsoleUI.initialPrompt(stmt);
//		EmartCustomers.printall(stmt);
//		EmartItems.insertEmartItem(20, "Desk", 3, 40, stmt);
//		EmartItems.insertEmartItem(10, "Book", 100, 40, stmt);
//		EmartItems.insertEmartItem(30, "Blanket", 40, 40, stmt);
//		EmartItems.insertEmartItem(40, "Pizza", 13, 40, stmt);
//		EmartItems.updatePrice(20, 4000, stmt);
//		EmartItems.printall(stmt);
//		EmartItems.removeByStockNo(10, stmt);
//		EmartItems.printall(stmt);
		
//		DiscAndShipPrcnt.dropDiscAndShipPrcnt(stmt);
//		createTable(DiscAndShipPrcnt.create_table_sql,stmt);
//		DiscAndShipPrcnt.insertDefaults(stmt);
//		DiscAndShipPrcnt.updatePercentage("Gold",10,stmt);
		
//		System.out.println(EmartCart.create_table_sql);
//		createTable(EmartCart.create_table_sql,stmt);
//		EmartCart.insertItemInCart(101, 20, 10, "Desk", 5, stmt);
//		EmartCart.insertItemInCart(101, 30, 10, "Blanket", 10, stmt);
//		EmartCart.insertItemInCart(105, 20, 50, "Desk", 5, stmt);
//		EmartCart.insertItemInCart(105, 30, 50, "Blanket", 10, stmt);
//		EmartCart.printall(stmt);
//		EmartCart.insertItemInCart(101, 20, 10, "Desk", 5, stmt);
//		EmartCart.insertItemInCart(101, 30, 10, "Blanket", 10, stmt);
//		EmartCart.deleteItemFromCart(20, 50, stmt);
//		EmartCart.decrementQuantity(30, 50, 5, stmt);
//		EmartCart.printall(stmt);
//		EmartCart.cartTotalWithoutTaxOrShipping(10,stmt);
//		EmartCart.customerStatus(10,stmt);
//		EmartCart.getShippingPcnt(stmt);
		EmartCart.getStatusDiscount(10,stmt);
//		int a =EmartCart.calculateGrantCartTotal(EmartCart.cartTotalWithoutTaxOrShipping(stmt), EmartCart.getStatusDiscount(stmt), EmartCart.getShippingPcnt(stmt));
//		System.out.println("Grand Total: "+ a);
//		EmartCart.dropEmartCart(stmt);
		conn.close();
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
