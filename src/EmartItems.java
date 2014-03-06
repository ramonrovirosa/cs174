import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartItems {
	static String create_table_sql = "CREATE TABLE EmartItems " +
            "(stockno INTEGER not NULL, " +
            " name CHAR(20), " + 
            " price INTEGER, " +
            " quantity INTEGER, " +
            " PRIMARY KEY ( stockno ))";
	
	//insertEmartItem
	public static void insertEmartItem(int stockno, String name, int quantity, int price, Statement stmt){
		String sql = "INSERT INTO EmartItems ("+
												   "stockno,"+
												   "name,"+
												   "price,"+
												   "quantity"+
												   ") "+
									"Values (" + "'" + stockno + "',"
											   + "'" + name + "'," 
											   + "'" + price + "'," 
											   + "'" + quantity + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	//print all items
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems");
		   
		// Iterate through the result and print the data
		System.out.println("contents of EmartItems:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("("+rs.getInt("stockno")+","+
								   rs.getString("name")+")"+","+
								   rs.getString("price")+")"+","+
								   rs.getInt("quantity") 
							);
		}
		rs.close();
	}
	
	//update quantity
	public static void updateItemQuantityAdd(int stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno =" + stockno; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(item1);
			rs1.next();
			int currentQuantity= rs1.getInt("quantity");
			int newQuantity=currentQuantity+quantity;
			//Update Emart Customer Orders && Status
			updateQuantity(stockno, newQuantity, stmt);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateItemQuantitySubtract(int stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno =" + stockno; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(item1);
			rs1.next();
			int currentQuantity= rs1.getInt("quantity");
			int newQuantity=currentQuantity-quantity;
			//Update Emart Customer Orders && Status
			updateQuantity(stockno, newQuantity, stmt);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateQuantity(int stockno, int quantity, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET quantity='" + quantity + "' " +
				 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
				
	}
	//update emart items Price...
	public static void updatePrice(int stockno, int price, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET price='" + price + "' " +
				 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	//remove item by stock#
	public static void removeByStockNo(int stockno, Statement stmt){
		String sql = "DELETE FROM EmartItems WHERE stockno = "+stockno;
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed EmartItems "+stockno+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//drop table
	public static void dropEmartCustomer(Statement stmt){
		try{
			stmt.executeUpdate("drop table EmartItems");
			System.out.println("dropped EmartItems table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
}


