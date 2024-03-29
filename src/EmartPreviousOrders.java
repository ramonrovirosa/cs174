import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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
			
	public static void insertPreviousOrder(int orderno, String customerID, String itemID, int quantity, String orderDate, double price, Statement stmt){
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
			//System.out.println(sql);
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
		ResultSet rs = stmt.executeQuery ("select * from EmartPreviousOrders where customerID='"+customerID+"'");

		// Iterate through the result and print the data
		System.out.println("Your previous orders:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Order Number: "+rs.getInt("orderno")+
						", Stock Number: "+rs.getString("itemID").trim()+
						", Customer ID: "+rs.getString("customerID").trim()+
						", Quantity: "+rs.getInt("quantity")+
						", Price: $"+rs.getInt("price")+
						", Order Date: "+rs.getDate("orderDate")
				);
		}
		rs.close();
	}
	
	public static void printallorder( int orderid, Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartPreviousOrders where orderno="+orderid);

		// Iterate through the result and print the data
		System.out.println("Previous order no."+orderid+":");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Stock Number: "+rs.getString("itemID").trim()+
						", Customer ID: "+rs.getString("customerID").trim()+
						", Quantity: "+rs.getInt("quantity")+
						", Price: $"+rs.getInt("price")+
						", Order Date: "+rs.getDate("orderDate")
				);
		}
		rs.close();
	}
	//find previous orders by date
	//Date format yyyy-mm-dd
	public static void findPreviousOrdersByDate(String ordersBeforeDate, String ordersAfterDate,  Statement stmt)throws SQLException{
		//if you are only asked to check the orders before a certain date, then use the first if.
		//if you are checking orders between a specified period than else
		String query = "";
		if(ordersAfterDate == ""){
			query = "Select * FROM EmartPreviousOrders WHERE orderDate <= "+"TO_DATE(" +"'" + ordersBeforeDate +"','yyyy-mm-dd"+ "')";
		}else{
			query = "Select * FROM EmartPreviousOrders WHERE orderDate <= "+"TO_DATE(" +"'" + ordersBeforeDate +"','yyyy-mm-dd"+ "') AND orderDate >= "+"TO_DATE(" +"'" + ordersAfterDate +"','yyyy-mm-dd"+ "')";
		}
		
		
		ResultSet rs = stmt.executeQuery (query);
		   
		System.out.println("EmartPreviousOrders before date "+ordersBeforeDate+":");
		System.out.println( "orderno,  "+
				"customerID,    "+
				"itemID,    "+
				"quantity,    "+
				"orderDate,    "+
				"price"
				);
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(rs.getInt("orderno")+"        "+
					   rs.getString("customerID").trim()+"          "+
					   rs.getString("itemID").trim()+"      "+
					   rs.getInt("quantity")+"         "+
					   rs.getDate("orderDate")+"     "+
					   rs.getInt("price") 
				);
		}
		rs.close();
	}
	
	//delete item from previous orders
	public static void deletePreviousOrders(String orderno, String itemID, Statement stmt){
		String sql = "DELETE FROM EmartPreviousOrders WHERE itemID = '"+itemID+
				 "' AND orderno='"+orderno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed order for item no."+itemID+" in order "+orderno+" from the database");
		}catch(SQLException se){
		 //Handle errors for JDBC
			 System.out.println(se);
		     se.printStackTrace();
		}
	}
	
	//run previous order by order#
	public static void rerunPreviousOrder(int orderno, Statement stmt)throws SQLException{
		String query = "SELECT itemID, quantity, customerID FROM EmartPreviousOrders WHERE orderno='"+orderno+"'";
		ResultSet rs = stmt.executeQuery (query);
		
		ArrayList<previousOrderVariables> rerunOrder = new ArrayList<previousOrderVariables>();
		String customerID="0";
		while(rs.next()){
			// Get the value from column "columnName" with integer type
				int quantity = rs.getInt("quantity");
				String itemID = rs.getString("itemID").replaceAll("\\s","");
				customerID = rs.getString("customerID").replaceAll("\\s","");
				previousOrderVariables item = new previousOrderVariables(quantity,itemID,customerID);
				rerunOrder.add(item);	
		}
		for(int i=0;i<rerunOrder.size();i++){
			EmartCart.insertItemInCart( rerunOrder.get(i).getItemID(),rerunOrder.get(i).getCustomerID(),EmartItems.getItemName(stmt, rerunOrder.get(i).getItemID()),rerunOrder.get(i).getQuantity(),stmt);
		}
		EmartCart.checkoutCart(customerID,stmt);
		
		rs.close();
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

class previousOrderVariables{
	int quantity;
	String itemID;
	String customerID;
	public previousOrderVariables(int q, String i, String c){
		quantity=q;
		itemID=i;
		customerID=c;
	}
	public int getQuantity(){
		return quantity;
	}
	public String getItemID(){
		return itemID;
	}
	public String getCustomerID(){
		return customerID;
	}
}
