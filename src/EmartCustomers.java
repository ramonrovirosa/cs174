import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartCustomers {
	static String create_table_sql = "CREATE TABLE EmartCustomers " +
            "(customerID INTEGER not NULL, " +
            " name CHAR(20), " + 
            " status CHAR(20), "+
            " Order1 INTEGER, " +
            " Order2 INTEGER, " +
            " Order3 INTEGER, " +
            " PRIMARY KEY ( customerID ))";
	
	public static void insertEmartCustomer(int customerID, String name, Statement stmt){
		String sql = "INSERT INTO EmartCustomers ("+
												   "customerID,"+
												   "name,"+
												   "status,"+
												   "order1,"+
												   "order2,"+
												   "order3"+
												   ") "+
									"Values (" + "'" + customerID + "',"
											   + "'" + name + "'," 
											   + "'" + "new" + "',"
											   + "'" + "0" + "',"
											   + "'" + "0" + "',"
											   + "'" + "0" + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static void updateEmartCustomerOrderAndStatus(int customerID, int totalCost, Statement stmt){
		ResultSet rs1;
		
		String order1="SELECT * From  EmartCustomers C WHERE C.customerID =" + customerID; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(order1);
			rs1.next();
			int orders[] = {totalCost, rs1.getInt(4), rs1.getInt(5)};
			String newStatus = getStatusAndUpdate(orders); 
			System.out.println(newStatus);
			//Update Emart Customer Orders && Status
			String sql = orderStatusString(customerID, orders,newStatus);
			updateOrderStatus(sql, stmt);
			System.out.println("Updated customer: " + sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	private static void updateOrderStatus(String sql, Statement stmt){
		try{
			ResultSet rs2 =  stmt.executeQuery(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	private static String getStatusAndUpdate(int orders[]){
		int orderTotals = orders[0]+orders[1]+orders[2];
		if(orderTotals > 500)
			return "Gold";
		else if(orderTotals >100 && orderTotals <=500)
			return "Silver";
		else if(orderTotals > 0 && orderTotals <=100)
			return "Green";
		else
			return "New";
	}
	private static String orderStatusString(int customerID, int orders[], String newStatus){
		String sql = "Update EmartCustomers "+
				 "SET status='" + newStatus + "', " +
				 "order1='" + orders[0] + "', " +
				 "order2='" + orders[1] + "', " +
				 "order3='" + orders[2] + "' " +
				 "Where "+ " customerID='"+customerID +"'";
		return sql;
	}
	
}
