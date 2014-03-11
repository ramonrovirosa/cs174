import java.io.IOException;
import java.sql.*;


public class Main {
	static Connection conn;
	static String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	static String strUsername = "alexander_simes";
	static String strPassword = "4998837";
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
		ConsoleUI.initialPrompt(stmt);
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


