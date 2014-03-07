import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartCart {
	//create cart table
	//cartid,itemid,name,customerid,quantity
	static String create_table_sql = "CREATE TABLE EmartCart " +
            "(cartID INTEGER not NULL, " +
            " itemID INTEGER, " + 
            " customerID INTEGER, " +
            " name CHAR(20), " +
            " quantity INTEGER, " +
            " PRIMARY KEY ( cartID, itemID, customerID ),"+
            " FOREIGN KEY (itemID) REFERENCES EmartItems (stockno)," +
            " FOREIGN KEY (customerID) REFERENCES EmartCustomers (customerID) )";
	
	
	//Add items to the cart
	public static void insertItemInCart(int cartID, int itemID, int customerID, String name, int quantity, Statement stmt ){
		//first check if item is already in cart...and if it is do an update else do insert
		//first check if item is in cart
		ResultSet rs1;
		String queryItems = "SELECT C.quantity " +
							" From EmartCart C "+
							" Where C.itemID='"+itemID + "'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(queryItems);
			//returned nothing, insert brand new item...
			if(!rs1.next()){
				executeInsertQuery(cartID,itemID,customerID,name,quantity, stmt);
			}
			else{
				//update instead of insert
				int prevQuantity = rs1.getInt("quantity");
				quantity=quantity+prevQuantity;
				updateCart(itemID,quantity, stmt);
			}
			
			//update item count in EmartItems???
			
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		
	}
	private static void updateCart(int itemID, int quantity, Statement stmt){
		String sql = "Update EmartCart "+
				 " SET quantity='" + quantity + "' "+
				 " Where "+ " itemID='"+itemID +"'";
		try{
			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	private static void executeInsertQuery(int cartID, int itemID, int customerID, String name, int quantity, Statement stmt){
		String sql = "INSERT INTO EmartCart ("+
				   "cartID,"+
				   "itemID,"+
				   "customerID,"+
				   "name,"+
				   "quantity"+
				   ") "+
	"Values (" + "'" + cartID + "',"
			   + "'" + itemID + "'," 
			   + "'" + customerID + "'," 
			   + "'" + name + "'," 
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
	//Delete Items from the cart
	public static void deleteItemFromCart(int itemID, Statement stmt){
		String sql = "DELETE FROM EmartCart WHERE itemID = "+itemID;
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed EmartCart "+itemID+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//remove a quantity of an item from the cart
	public static void removeQuantityOfItemFromCart(int itemID, int quantity, Statement stmt){
		ResultSet rs1;
		String queryItems = "SELECT C.quantity " +
							" From EmartCart C "+
							" Where C.itemID='"+itemID + "'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(queryItems);
			rs1.next();
			int prevQuantity = rs1.getInt("quantity");
			quantity=prevQuantity-quantity;
			updateCart(itemID,quantity, stmt);
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
	
//	print all items in cart
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCart");
		   
		// Iterate through the result and print the data
		System.out.println("contents of EmartCart:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("("+rs.getInt("cartID")+","+
								   rs.getInt("itemID")+","+
								   rs.getInt("customerID")+","+
								   rs.getString("name")+","+
								   rs.getInt("quantity")+")" 
							);
		}
		rs.close();
	}

}
