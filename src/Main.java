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
		
//		resetDB(stmt);
		//EmartPreviousOrders.dropEmartPreviousOrders(stmt);
		//createTable(EmartPreviousOrders.create_table_sql,stmt);
		//createTable(EmartAccessories.create_table_sql, stmt);
//		EmartItems.printallformatted(stmt);
//		ConsoleUI.initialPrompt(stmt);
		
//		EdepotItems.dropEdepotItem(stmt);
//		createTable(EdepotItems.create_table_sql,stmt);
//		EdepotItems.insertEdepotItem("101", "HP",       "6111", 1,2,10,"A9",  0,stmt);
//		EdepotItems.insertEdepotItem("201", "Dell",     "420", 	2,3,15,"A7",  0,stmt);
//		EdepotItems.insertEdepotItem("202", "Emachine", "3958",	2,4,8, "B52", 0,stmt);
//		EdepotItems.insertEdepotItem("301", "Envision", "720", 	3,4,6, "C27", 0,stmt);
//		EdepotItems.insertEdepotItem("302", "Samsung",  "712", 	3,4,6, "C13", 0,stmt);
//		EdepotItems.insertEdepotItem("401", "Symantec", "2005", 5,7,9, "D27", 0,stmt);
//		EdepotItems.insertEdepotItem("402", "Mcafee",   "2005", 5,7,9, "D1",  0,stmt);
//		EdepotItems.insertEdepotItem("501", "HP", 		"1320", 2,3,5, "E7",  0,stmt);
//		EdepotItems.insertEdepotItem("601", "HP", 		"435", 	2,3,9, "F9",  0,stmt);
//		EdepotItems.insertEdepotItem("602", "Cannon", 	"738", 	2,3,5, "F3",  0,stmt);
		
//		EdepotItems.updateEdepotQuantity("101", 100, stmt);
		
//		EdepotItems.updateEdepotReplenishment("101", 500, stmt);
//		EdepotItems.getQuantityForItem("101", stmt);
//		EdepotItems.getReplenishmentForItem("101", stmt);
//		EdepotItems.subtractQuantityForItemsSold("101", 15, stmt);
//		EdepotItems.receiveShippingLabel( "102", "HP",       "6111", 1,2,10,"A9",  0,stmt);
//		EdepotItems.receiveShipment("101", 2, stmt);
		
		EdepotItems.printall(stmt);
		
		conn.close();
	}
	
	public static void resetDB(Statement stmt){
		DiscAndShipPrcnt.dropDiscAndShipPrcnt(stmt);
		EmartPreviousOrders.dropEmartPreviousOrders(stmt);
		
		EmartCart.dropEmartCart(stmt);
		EmartCustomers.dropEmartCustomer(stmt);
		EmartItems.dropEmartItems(stmt);
		createTable(EmartCustomers.create_table_sql,stmt);
		createTable(EmartItems.create_table_sql,stmt);
		createTable(EmartCart.create_table_sql,stmt);
		
		createTable(EmartPreviousOrders.create_table_sql,stmt);
		createTable(DiscAndShipPrcnt.create_table_sql,stmt);
		DiscAndShipPrcnt.insertDefaults(stmt);


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


