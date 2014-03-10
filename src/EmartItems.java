import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartItems {
	static String create_table_sql = "CREATE TABLE EmartItems " +
						             "(stockno CHAR(20) not NULL, " +
						             " category CHAR(20), " +
						             " manufacturer CHAR(20), " +
						             " modelno CHAR(20), " +
						             " description CHAR(500), " +
						             " warranty INTEGER, " +
						             " price INTEGER, " +
						             " quantity INTEGER, " +
						             " PRIMARY KEY ( stockno ))";
							
	
	public static void searchEmartItem(String stockno, Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems where stockno ="+stockno);
		System.out.println("Your search returned:");
		System.out.println("stockno,"+
						   "category,"+
						   "manufacturer,"+
						   "modelno,"+
						   "description,"+
						   "warranty,"+
						   "price,"+
						   "quantity,"+
						   ") ");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+","+
								   rs.getString("category").replaceAll("\\s+","")+","+
								   rs.getString("manufacturer").replaceAll("\\s+","")+",  desc:"+
								   rs.getString("modelno").replaceAll("\\s+","")+","+
								   rs.getString("description").replaceAll("\\s+","")+",  "+
								   rs.getInt("warranty")+","+
								   rs.getInt("price")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("price")
							);
		}
		rs.close();
	}
	
	//insertEmartItem
	public static void insertEmartItem(String stockno, String category,String manufacturer,
									   String modelno, String description,
									   int warranty,int price, int quantity, 
									   Statement stmt){
		String sql = "INSERT INTO EmartItems ("+
												   "stockno,"+
												   "category,"+
												   "manufacturer,"+
												   "modelno,"+
												   "description,"+
												   "warranty,"+
												   "price,"+
												   "quantity"+
												   ") "+
									"Values (" + "'" + stockno + "',"
											   + "'" + category + "'," 
											   + "'" + manufacturer + "',"
											   + "'" + modelno + "',"
											   + "'" + description + "',"
											   + "'" + warranty + "',"
											   + "'" + price + "',"
											   + "'" + quantity + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println("Inserted "+quantity+" "+manufacturer+" into Emart");
			//System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static String getItemName( Statement stmt, String stockno ) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT category from EmartItems where stockno ='"+stockno+"'");
		rs.next();
		String name = rs.getString("category").replaceAll("\\s+","");
		rs.close();
		return name;		
	}
	
	public static int getItemPrice( Statement stmt, String stockno ) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT price from EmartItems where stockno ='"+stockno+"'");
		rs.next();
		int price = rs.getInt("price");
		System.out.println("in get price;");
		rs.close();
		return price;		
	}
	
	//print all items
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems");
		   
		// Iterate through the result and print the data
		System.out.println("Contents of EmartItems:");
		System.out.println("stockno,"+
						   "category,"+
						   "manufacturer,"+
						   "modelno,"+
						   "description,"+
						   "warranty,"+
						   "price,"+
						   "quantity,"+
						   ") ");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+","+
								   rs.getString("category").replaceAll("\\s+","")+","+
								   rs.getString("manufacturer").replaceAll("\\s+","")+",  desc:"+
								   rs.getString("modelno").replaceAll("\\s+","")+","+
								   rs.getString("description").replaceAll("\\s+","")+",  "+
								   rs.getInt("warranty")+","+
								   rs.getInt("price")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("price")
							);
		}
		rs.close();
	}
	
	public static void printallformatted( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems");
		   
		// Iterate through the result and print the data
		System.out.println("Contents of your Emart Catalog:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Stock Number:"+rs.getString("stockno").trim()+
							", Category:"+rs.getString("category").trim()+
							", Price: "+rs.getString("price")+
							", Quantity: "+rs.getInt("quantity") 
							);
		}
		rs.close();
	}
	
	//update quantity
	public static void updateItemQuantityAdd(String stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno ='" + stockno+"'"; 
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
	public static void updateItemQuantitySubtract(String stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno ='" + stockno+"'"; 
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
	public static void updateQuantity(String stockno, int quantity, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET quantity='" + quantity + "' " +
				 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Updated quantity to "+quantity);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
				
	}
	//update emart items Price...
	public static void updatePrice(String stockno, int price, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET price='" + price + "' " +
				 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Set price to "+price);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	//remove item by stock#
	public static void removeByStockNo(String stockno, Statement stmt){
		String sql = "DELETE FROM EmartItems WHERE stockno = '"+stockno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed item no. "+stockno+" from Emart");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//drop table
	public static void dropEmartItems(Statement stmt){
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


