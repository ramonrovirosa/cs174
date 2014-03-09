import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartPreviousOrders {
	static String create_table_sql= "CREATE TABLE EmartPreviousOrders "+
									" (orderno INTEGER not NULL, " +
						            " customerID INTEGER not null, " + 
						            " itemID INTEGER not null, " +
						            " quantity INTEGER, " +
						            " orderDate CHAR(20), " +
						            " price INTEGER,"+
						            " PRIMARY KEY (orderno, customerID, itemID),"+
						            " FOREIGN KEY (itemID) REFERENCES EmartItems (stockno)," +
						            " FOREIGN KEY (customerID) REFERENCES EmartCustomers (customerID) )";
			
	public static void insertPreviousOrder(int orderno, int customerID, int itemID, int quantity, String orderDate, int price, Statement stmt){
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
							   + "'" + quantity + "'," 
							   + "'" + orderDate + "'," 
							   + "'" + price + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		
	}
	//print all
		public static void printall( Statement stmt) throws SQLException{
			ResultSet rs = stmt.executeQuery ("select * from EmartPreviousOrders");

			// Iterate through the result and print the data
			System.out.println("contents of EdepotItems:");
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
						   rs.getInt("customerID")+"          "+
						   rs.getInt("itemID")+"      "+
						   rs.getInt("quantity")+"         "+
						   rs.getString("orderDate").replaceAll("\\s+","")+"     "+
						   rs.getInt("price") 
					);
			}
			rs.close();
		}
	//find previous orders by date
	
	//run previous order by order#
	
	
	
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
