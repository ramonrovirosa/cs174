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
		
		resetDB(stmt);
		
		ConsoleUI.initialPrompt(stmt);
		conn.close();
	}
	
	public static void resetDB(Statement stmt){
		
		DiscAndShipPrcnt.dropDiscAndShipPrcnt(stmt);
		EmartPreviousOrders.dropEmartPreviousOrders(stmt);
		EmartAccessories.dropEmartAccessories(stmt);
		
		
		
		EmartCart.dropEmartCart(stmt);
		EmartCustomers.dropEmartCustomer(stmt);
		EmartItems.dropEmartItems(stmt);
		
		EdepotItems.dropEdepotItem(stmt);
		EdepotShippingNotice.dropEdepotshippingNoticeItem(stmt);
		
		createTable(EdepotItems.create_table_sql, stmt);
		createTable(EdepotShippingNotice.create_table_sql, stmt);
		
		createTable(EmartCustomers.create_table_sql,stmt);
		createTable(EmartItems.create_table_sql,stmt);
		createTable(EmartCart.create_table_sql,stmt);
		createTable(EmartAccessories.create_table_sql, stmt);
		createTable(EmartPreviousOrders.create_table_sql,stmt);
		createTable(DiscAndShipPrcnt.create_table_sql,stmt);
		
		DiscAndShipPrcnt.insertDefaults(stmt);
		
		EmartCustomers.insertEmartCustomer("Rhagrid", 	"Rubeus Hagrid", 		"Rhagrid", 	"rhagrid@cs", 	"123 MyStreet, Goleta apt A, Ca", 0, "Gold", stmt);
		EmartCustomers.insertEmartCustomer("Mhooch",  	"Madam Hooch", 			"Mhooch", 	"mhooch@cs", 	"123 MyStreet, Goleta apt B, Ca", 0, "Silver",stmt);
		EmartCustomers.insertEmartCustomer("Amoody",  	"Alastor Moody", 		"Amoody", 	"amoody@cs", 	"123 MyStreet, Goleta apt C, Ca", 0, "New",stmt);
		EmartCustomers.insertEmartCustomer("Pquirrell", "Professor Quirrell", 	"Pquirrell","pquirrell@cs",	"123 MyStreet, Goleta apt D, Ca", 0, "New", stmt);
		EmartCustomers.insertEmartCustomer("Sblack", 	"Sirius Black", 		"Sblack", 	"sblack@cs",	"123 MyStreet, Goleta apt E, Ca", 1, "Green",stmt);
		EmartCustomers.insertEmartCustomer("Ddiggle", 	"Dedalus Diggle", 		"Ddiggle", 	"ddiggle@cs",	"123 MyStreet, Goleta apt F, Ca", 0, "Green", stmt);

		EdepotItems.insertEdepotItem("AA00101", "HP", 		"6111", 1,2,10,"A9", stmt);
		EdepotItems.insertEdepotItem("AA00201", "Dell",     "420", 	2,3,15,"A7", stmt);
		EdepotItems.insertEdepotItem("AA00202", "Emachine", "3958",	2,4,8, "B52",stmt);
		EdepotItems.insertEdepotItem("AA00301", "Envision", "720", 	3,4,6, "C27",stmt);
		EdepotItems.insertEdepotItem("AA00302", "Samsung",  "712", 	3,4,6, "C13", stmt);
		EdepotItems.insertEdepotItem("AA00401", "Symantec", "2005", 5,7,9, "D27", stmt);
		EdepotItems.insertEdepotItem("AA00402", "Mcafee",   "2005", 5,7,9, "D1", stmt);
		EdepotItems.insertEdepotItem("AA00501", "HP", 		"1320", 2,3,5, "E7", stmt);
		EdepotItems.insertEdepotItem("AA00601", "HP", 		"435", 	2,3,9, "F9", stmt);
		EdepotItems.insertEdepotItem("AA00602", "Cannon", 	"738", 	2,3,5, "F3", stmt);
		
		EmartItems.insertEmartItem("AA00101", "Laptop", "HP", "6111",
				"Processer speed: 3.33Ghz, Ram size: 512 Mb, Hard disk size: 100Gb, Display Size: 17", 
				12,1630,2, stmt);
		EmartItems.insertEmartItem("AA00201", "Desktop", "Dell",     "420", 
				"Processer speed: 2.53Ghz, Ram size: 256 Mb, Hard disk size: 80Gb, Os: none",
				12,239,3, stmt);
		EmartItems.insertEmartItem("AA00202", "Desktop", "Emachine", "3958",
				"Processer speed: 2.9Ghz, Ram size: 512 Mb, Hard disk size: 80Gb",
				12,369.99,4, stmt);
		EmartItems.insertEmartItem("AA00301", "Monitor", "Envision", "720",
				"Size: 17\", Weight: 25 lb.",
				36,69.99,4, stmt);
		EmartItems.insertEmartItem("AA00302", "Monitor", "Samsung",  "712", 
				"Size: 17\", Weight: 9.6 lb.",
				36,279.99,4, stmt);
		EmartItems.insertEmartItem("AA00401", "Software", "Symantec", "2005", 
				"Required disk size: 128 MB, Required RAM size: 64 MB",
				60,19.99,7, stmt);
		EmartItems.insertEmartItem("AA00402", "Software", "Mcafee",   "2005", 
				"Required disk size: 128 MB, Required RAM size: 64 MB",
				60,19.99,7, stmt);
		EmartItems.insertEmartItem("AA00501", "Printer", "HP", 		"1320",
				"Resoulution: 1200 dpi, Sheet capacity: 500, Weight: .4 lb",
				12,299.99,3, stmt);
		EmartItems.insertEmartItem("AA00601", "Printer", "HP", 		"435",
				"Resoulution: 3.1 Mp, Max zoom: 5 times, Weight: 24.7 lb",
				3,119.99,3, stmt);
		EmartItems.insertEmartItem("AA00602", "Camera", "Cannon", 	"738", 
				"Resoulution: 3.1 Mp, Max zoom: 5 times, Weight: 24.7 lb",
				1,329.99,3, stmt);	
				
		EmartAccessories.insertAccessory("AA00301","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00301","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00302","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00302","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00401","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00401","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00401","AA00101",stmt);
		EmartAccessories.insertAccessory("AA00402","AA00101",stmt);
		EmartAccessories.insertAccessory("AA00402","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00402","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00501","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00501","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00601","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00601","AA00202",stmt);
		EmartAccessories.insertAccessory("AA00602","AA00201",stmt);
		EmartAccessories.insertAccessory("AA00602","AA00202",stmt);


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


