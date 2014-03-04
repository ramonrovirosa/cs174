import java.sql.*;


public class EdepotItems {
	static String create_table_sql = "CREATE TABLE EdepotItems " +
            "(stockno INTEGER not NULL, " +
            " name CHAR(20), " + 
            " PRIMARY KEY ( stockno ))";
	//get name by stockno
	public static void getNamebyStockNO(int stockno, Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select name from EdepotItems where stockno = "+stockno);

		// Iterate through the result and print the data
		System.out.println("name where stockno is "+stockno+":");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(rs.getString("name"));
		}
		rs.close();
	}
	//print whole table
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EdepotItems");

		// Iterate through the result and print the data
		System.out.println("contents of EdepotItems:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("("+rs.getInt("stockno")+","+rs.getString("name")+")");
		}
		rs.close();
	}
	//remove by stockno
	public static void removebystockno(int stockno, Statement stmt){
		String sql = "DELETE FROM EdepotItems WHERE stockno = "+stockno;
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed stockno"+stockno+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//Insert new item into table
	public static void insertEdepotItem(int stockno, String name, Statement stmt){
		String sql = "INSERT INTO EdepotItems VALUES ("+stockno+", '"+name+"')";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Inserted "+name+" into the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//drop the EdepotItems table
	public static void dropEdepotItem(Statement stmt){
		System.out.println("dropped edepotitems table");
		try{
			stmt.executeUpdate("drop table EdepotItems");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	//
	//createTable string...&& then get it and pass it to createTable(String sql, Statement stmt) inside of main
	
	//Shipment(with notice)
	//	update replenishment for model #
	//Shipment
	//make sure shipment qty==replenishment.qty 
	//update quantity inside of edepot items.
}
