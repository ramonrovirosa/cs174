import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EmartCart {
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd";
	//create cart table
	//itemid,name,customerid,quantity
	static String create_table_sql = "CREATE TABLE EmartCart " +
            "(itemID CHAR(20), " + 
            " customerID CHAR(20), " +
            " name CHAR(20), " +
            " quantity INTEGER, " +
            " PRIMARY KEY ( customerID, itemID ),"+
            " FOREIGN KEY (itemID) REFERENCES EmartItems (stockno)," +
            " FOREIGN KEY (customerID) REFERENCES EmartCustomers (customerID) )";
	
	
	//Add items to the cart
	public static void insertItemInCart( String itemID, String customerID, String name, int quantity, Statement stmt ){
		//first check if item is already in cart...and if it is do an update else do insert
		//first check if item is in cart
		ResultSet rs1;
		String queryItems = "SELECT C.quantity " +
							" From EmartCart C "+
							" Where C.itemID='"+itemID + "'" +
							" AND C.customerID ='"+customerID+"'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(queryItems);
			//returned nothing, insert brand new item...
			if(!rs1.next()){
				executeInsertQuery(itemID,customerID,name,quantity, stmt);
			}
			else{
				//update instead of insert
				int prevQuantity = rs1.getInt("quantity");
				quantity=quantity+prevQuantity;
				updateCart(itemID,quantity, customerID, stmt);
			}
			
			//update item count in EmartItems???
			
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		
	}
	
	private static void updateCart(String itemID, int quantity,String customerID, Statement stmt){
		String sql = "Update EmartCart "+
				 " SET quantity='" + quantity + "' "+
				 " Where "+ " itemID='"+itemID +"'" +
				 " AND customerID ='"+customerID+"'";;
		try{
//			System.out.println(sql);
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Updated quantity of "+EmartItems.getItemName(stmt,itemID).trim()+" to "+quantity);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	private static void executeInsertQuery( String itemID, String customerID, String name, int quantity, Statement stmt){
		String sql = "INSERT INTO EmartCart ("+
				   "itemID,"+
				   "customerID,"+
				   "name,"+
				   "quantity"+
				   ") "+
	"Values (" + "'" + itemID + "'," 
			   + "'" + customerID + "'," 
			   + "'" + name + "'," 
			   + "'" + quantity + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println("Inserted "+quantity+" "+name.trim()+"(s) into cart");
			//System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//Delete Items from the cart
	public static void deleteItemFromCart(String itemID, String customerID, Statement stmt){
		String sql = "DELETE FROM EmartCart WHERE itemID = "+itemID+
					 "AND customerID='"+customerID+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Removed all"+EmartItems.getItemName(stmt,itemID).trim()+"(s) from "+EmartCustomers.getCustomerName(customerID,stmt).trim()+"'s cart");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static void deleteItemFromCartSilent(String itemID, String customerID, Statement stmt){
		String sql = "DELETE FROM EmartCart WHERE itemID = '"+itemID+
					 "' AND customerID='"+customerID+"'";
		try{
			stmt.executeUpdate(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//remove a quantity of an item from the cart
	public static void decrementQuantity(String itemID, String customerID, int quantity,  Statement stmt){
		ResultSet rs1;
		String queryItems = "SELECT C.quantity " +
							" From EmartCart C "+
							" Where C.itemID='"+itemID + "'"+
							" AND C.customerID='"+customerID+"'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(queryItems);
			rs1.next();
			int prevQuantity = rs1.getInt("quantity");
			quantity=prevQuantity-quantity;
			if(quantity<0){
				deleteItemFromCart(itemID,customerID,stmt);
			}else{
				updateCart(itemID,quantity, customerID, stmt);
			}
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	//drop table
	public static void dropEmartCart(Statement stmt){
		try{
			stmt.executeUpdate("drop table EmartCart");
			System.out.println("dropped EmartCart table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	//Calculate cart total price
	//gold customer 10% off order
	//silver 5% off order
	//10% shipping & handling fee if total less than or equal $100
	public static int cartTotalWithoutTaxOrShipping(String customerID, Statement stmt){
		ResultSet rs1;
		String query = "SELECT C.quantity, A.price "+
					   " FROM EmartCart C, EmartItems A "+
					   " WHERE C.itemID=A.stockno"+
					   " AND C.customerID='"+customerID+"'";
		int total=0;
//		System.out.println(query);
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				total+=rs1.getInt("quantity")*rs1.getInt("price");
			}
			//System.out.println("Cart total: $"+total);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return total;
	}
	
	//getCustomerStatus
	public static String customerStatus(String customerID, Statement stmt){
		ResultSet rs,rs1;
		String status="";
//		int customerID = 0;
//		String queryID="SELECT C.customerID From  EmartCart C ";
//		try{
//			rs = stmt.executeQuery(queryID);
//			rs.next();
//			customerID = rs.getInt("customerID");
//			System.out.println("customerID: "+ customerID);
//		}catch(SQLException se){
//		      //Handle errors for JDBC
//			  System.out.println(se);
//		      se.printStackTrace();
//		}
		//now get status based on customer id
		String getStatus="SELECT C.status From  EmartCustomers C WHERE C.customerID ='" + customerID+"'"; 
		try{
			rs1 = stmt.executeQuery(getStatus);
			rs1.next();
			status = rs1.getString("status");
			//System.out.println("status: "+status);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return status;
	}
	//shipping percentage
	public static int getShippingPcnt(Statement stmt){
		ResultSet rs,rs1;
		int percent = 0;
		String queryPcnt="SELECT C.percentage From  DiscAndShipPrcnt C WHERE C.name = 'Shipping' ";
		try{
			rs = stmt.executeQuery(queryPcnt);
			rs.next();
			percent = rs.getInt("percentage");
			//System.out.println("shipping percentage: "+ percent);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return percent;
	}
	//status discount
	public static int getStatusDiscount(String customerID, Statement stmt){
		ResultSet rs,rs1;
		String status=customerStatus(customerID, stmt);
		int percent = 0;
		String queryPcnt="SELECT C.percentage From  DiscAndShipPrcnt C WHERE C.name ='"+status+"'";
		try{
			rs = stmt.executeQuery(queryPcnt);
			rs.next();
			percent = rs.getInt("percentage");
			//System.out.println("percentage: "+ percent);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return percent;
	}
	//CalculateGrandTotal
	public static int calculateGrantCartTotal(int preTotal, int discount, int shipping ){
		if(preTotal > 100) 
			shipping = 0;
		Double disc = preTotal*discount*.01;
		return preTotal - disc.intValue() + shipping;
	}

	public static void checkoutCart(String customerID, Statement stmt) throws SQLException{
		int total = calculateGrantCartTotal(cartTotalWithoutTaxOrShipping(customerID,stmt), getStatusDiscount(customerID, stmt), getShippingPcnt(stmt));
		System.out.println("The grand checkout total is: "+total);
		int orderno = EmartPreviousOrders.getNewOrderNo(stmt);
		ResultSet rs = stmt.executeQuery("Select itemID, quantity from EmartCart where customerID = '"+customerID+"'");
		while(rs.next()){
			System.out.println("calling instert prev order");
			String itemID = rs.getString("ITEMID");
			int quantity = rs.getInt("quantity");
			EmartPreviousOrders.insertPreviousOrder(orderno, customerID, itemID, quantity, now(), EmartItems.getItemPrice(stmt,itemID), stmt);
			System.out.println("inserted into prev order");
			deleteItemFromCartSilent(itemID, customerID, stmt);
		}
		rs.close();
		return;
	}
	
	
	//	print all items in cart
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCart");
		   
		// Iterate through the result and print the data
		System.out.println("contents of EmartCart:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("("+rs.getString("itemID")+","+
								   rs.getString("customerID")+","+
								   rs.getString("name")+","+
								   rs.getInt("quantity")+")" 
							);
		}
		rs.close();
	}
		
	public static void printCustomerCart( Statement stmt, String id) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCart where customerID ='"+id+"'");
		// Iterate through the result and print the data
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Stockno: "+rs.getString("itemID").trim()+
								", Name: "+rs.getString("name").trim()+
								", Quantity: "+rs.getInt("quantity")
							);
		}
		rs.close();
	}

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		System.out.println(sdf.format(cal.getTime()));
		return sdf.format(cal.getTime());
		}
}
