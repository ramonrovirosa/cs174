import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartPreviousOrders {
	static String create_table_sql= "CREATE TABLE EmartPreviousOrders "+
									" (orderno INTEGER not NULL, " +
						            " customerID CHAR(20) not null, " + 
						            " itemID CHAR(20) not null, " +
						            " quantity INTEGER, " +
						            " orderDate DATE, " +
						            " price INTEGER,"+
						            " PRIMARY KEY (orderno, itemID),"+
						            " FOREIGN KEY (itemID) REFERENCES EmartItems (stockno)," +
						            " FOREIGN KEY (customerID) REFERENCES EmartCustomers (customerID) )";
			
	public static void insertPreviousOrder(int orderno, String customerID, String itemID, int quantity, String orderDate, int price, Statement stmt){
		String sql = "INSERT INTO EmartPreviousOrders ("+
					 " orderno,"+
					 " customerID,"+
					 " itemID,"+
					 " quantity,"+
					 " orderDate,"+
					 " price"+
					 " ) "+
					" Values (" + "'" + orderno + "'," 
							   + "'" + customerID + "'," 
							   + "'" + itemID + "'," 
							   + "'" + quantity + "', " 
							   + "TO_DATE(" +"'" + orderDate +"','yyyy-mm-dd"+ "')," 
							   + "'" + price + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		return;		
	}
	//print all
	
	public static int getNewOrderNo( Statement stmt ) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT max(orderno) as max from EmartPreviousOrders");
		rs.next();
		int max = rs.getInt("max");
		return max+1;		
	}
	
	public static void printall( Statement stmt) throws SQLException{
			ResultSet rs = stmt.executeQuery ("select * from EmartPreviousOrders");

			// Iterate through the result and print the data
			System.out.println("contents of EmartPreviousOrders:");
			System.out.println( "orderno, "+
					"customerID, "+
					"itemID, "+
					"quantity, "+
					"orderDate, "+
					"price"
					);
			while(rs.next()){
				// Get the value from column "columnName" with integer type
				System.out.println(rs.getInt("orderno")+"      "+
						   rs.getString("customerID")+"          "+
						   rs.getString("itemID")+"      "+
						   rs.getInt("quantity")+"       "+
						   rs.getDate("orderDate")+"  "+
						   rs.getInt("price") 
					);
			}
			rs.close();
		}
	
	public static void printallcustomer( String customerID, Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartPreviousOrders where customerID="+customerID);

		// Iterate through the result and print the data
		System.out.println("Your previous orders:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Order Number: "+rs.getInt("orderno")+
						", Stock Number: "+rs.getString("itemID")+
						", Customer ID: "+rs.getString("customerID")+
						"Quantity: "+rs.getInt("quantity")+
						"Price: "+rs.getInt("price")+
						"Order Date: "+rs.getDate("orderDate")
				);
		}
		rs.close();
	}
	//find previous orders by date
	//Date format yyyy-mm-dd
	public static void findPreviousOrdersByDate(String ordersBeforeDate, String ordersAfterDate,  Statement stmt)throws SQLException{
		//if you are only asked to check the orders before a certain date, then use the first if.
		//if you are checking orders beetween a specified period than else
		String query = "";
		if(ordersAfterDate == ""){
			query = "Select * FROM EmartPreviousOrders WHERE orderDate <= "+"TO_DATE(" +"'" + ordersBeforeDate +"','yyyy-mm-dd"+ "')";
		}else{
			query = "Select * FROM EmartPreviousOrders WHERE orderDate <= "+"TO_DATE(" +"'" + ordersBeforeDate +"','yyyy-mm-dd"+ "') AND orderDate >= "+"TO_DATE(" +"'" + ordersAfterDate +"','yyyy-mm-dd"+ "')";
		}
		
		
		ResultSet rs = stmt.executeQuery (query);
		   
		System.out.println("EmartPreviousOrders before date "+ordersBeforeDate+":");
		System.out.println( "orderno, "+
				"customerID, "+
				"itemID, "+
				"quantity, "+
				"orderDate, "+
				"price"
				);
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(rs.getInt("orderno")+"      "+
					   rs.getString("customerID")+"          "+
					   rs.getString("itemID")+"      "+
					   rs.getInt("quantity")+"         "+
					   rs.getDate("orderDate")+"  "+
					   rs.getInt("price") 
				);
		}
		rs.close();
	}
	
	//delete item from previous orders
	public static void deletePreviousOrders(int orderno, int itemID, Statement stmt){
		String sql = "DELETE FROM EmartPreviousOrders WHERE itemID = '"+itemID+
				 "' AND orderno='"+orderno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed EmartPreviousOrders from the database");
		}catch(SQLException se){
		 //Handle errors for JDBC
			 System.out.println(se);
		     se.printStackTrace();
		}
	}
	
	//run previous order by order#
	public static void rerunPreviousOrder(int orderno, Statement stmt){
		
	}
	
	
	public static void dropEmartPreviousOrders(Statement stmt){
		try{
			stmt.executeUpdate("drop table EmartPreviousOrders");
			System.out.println("dropped EmartPreviousOrders table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		
	}
}
