import java.sql.*;


public class EdepotItems {
	static String create_table_sql = "CREATE TABLE EdepotItems " +
            "(stockno CHAR(20) not NULL, " +
            " manufacturer CHAR(20), " +
            " modelno CHAR(20), " +
            " min INTEGER, " +
            " quantity INTEGER, " +
            " max INTEGER, " +
            " location CHAR(20), " +
            " replenishment INTEGER, " +
            " PRIMARY KEY ( stockno ))";
	
//	//get name by stockno
//	public static void getNamebyStockNO(int stockno, Statement stmt) throws SQLException{
//		ResultSet rs = stmt.executeQuery ("select name from EdepotItems where stockno = "+stockno);
//
//		// Iterate through the result and print the data
//		System.out.println("name where stockno is "+stockno+":");
//		while(rs.next()){
//			// Get the value from column "columnName" with integer type
//			System.out.println(rs.getString("name"));
//		}
//		rs.close();
//	}
	//print whole table
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EdepotItems");
		System.out.println("contents of EdepotItems:");
		System.out.println("stockno,"+
				   "manufacturer,"+
				   "modelno,"+
				   "min,"+
				   "quantity,"+
				   "max,"+
				   "location,"+
				   "replenishment"+
				   ") ");

		// Iterate through the result and print the data
		while(rs.next()){
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+",    "+
								   rs.getString("manufacturer").replaceAll("\\s+","")+",         "+
								   rs.getString("modelno").replaceAll("\\s+","     ")+
								   rs.getInt("min")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("max")+",     "+
								   rs.getString("location").replaceAll("\\s+","")+","+
								   rs.getInt("replenishment")
				);
		}
		rs.close();
	}
	//remove by stockno
	public static void removebystockno(String stockno, Statement stmt){
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
	public static void insertEdepotItem(String stockno, String manufacturer, String modelno, 
										int min, int quantity, int max,
										String location, int replenishment, Statement stmt){
		String sql = "INSERT INTO EdepotItems ("+
												   "stockno,"+
												   "manufacturer,"+
												   "modelno,"+
												   "min,"+
												   "quantity,"+
												   "max,"+
												   "location,"+
												   "replenishment"+
											") "+
										"Values (" + "'" + stockno + "',"
										   + "'" + manufacturer + "'," 
										   + "'" + modelno + "',"
										   + "'" + min + "',"
										   + "'" + quantity + "',"
										   + "'" + max + "',"
										   + "'" + location + "',"
										   + "'" + replenishment + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateEdepotQuantity(String stockno, int quantity, Statement stmt){
		String sql = "Update EdepotItems "+
					 "SET quantity='" + quantity + "' " +
					 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Updated quantity for "+stockno+" in the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static void updateEdepotReplenishment(String stockno, int replenishment, Statement stmt){
		String sql = "Update EdepotItems "+
					 "SET replenishment='" + replenishment + "' " +
					 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Updated replenishment for "+stockno+" in the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	//drop the EdepotItems table
	public static void dropEdepotItem(Statement stmt){
		try{
			stmt.executeUpdate("drop table EdepotItems");
			System.out.println("dropped edepotitems table");
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
