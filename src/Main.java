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
		
//		createTable(EmartCustomers.create_table_sql,stmt);
//		EmartCustomers.insertEmartCustomer(200, "Brenda", stmt);
//		EmartCustomers.insertEmartCustomer(10, "Ramon", stmt);
//		EmartCustomers.insertEmartCustomer(50, "Alex", stmt);
//		EmartCustomers.removeByCustomerID(10, stmt);
		
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
