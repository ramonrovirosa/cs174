import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartCustomers {
	static String create_table_sql = "CREATE TABLE EmartCustomers " +
            "(customerID CHAR(20) not NULL, " +
            " name CHAR(20), " +
            " password CHAR(20), " +
            " email CHAR(20), " +
            " address CHAR(50), " +
            " isManager INTEGER, " +
            " status CHAR(20), "+
            " Order1 numeric(10,2), " +
            " Order2 numeric(10,2), " +
            " Order3 numeric(10,2), " +
            " PRIMARY KEY ( customerID ))";
          	
	public static void insertEmartCustomer(String customerID, String name, String password, String email, String address,int isManager, String status, Statement stmt){
		String sql = "INSERT INTO EmartCustomers ("+
												   "customerID,"+
												   "name,"+
												   "password,"+
												   "email,"+
												   "address,"+
												   "isManager,"+
												   "status,"+
												   "order1,"+
												   "order2,"+
												   "order3"+
												   ") "+
									"Values (" + "'" + customerID + "',"
											   + "'" + name + "'," 
											   + "'" + password + "'," 
											   + "'" + email + "'," 
											   + "'" + address + "'," 
											   + "'" + isManager + "'," 
											   + "'" + status + "',"
											   + "'" + "0" + "',"
											   + "'" + "0" + "',"
											   + "'" + "0" + "')";	
		try{
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Added "+name.trim()+" as an Emart customer!");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static void updateEmartCustomerOrderAndStatus(String customerID, double totalCost, Statement stmt){
		ResultSet rs1;
		String order1="SELECT * From  EmartCustomers C WHERE C.customerID ='" + customerID+"'"; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(order1);
			rs1.next();
			double orders[] = {totalCost, rs1.getDouble("Order1"), rs1.getDouble("Order2")};
			String newStatus = getStatusAndUpdate(orders); 
			System.out.println("Your status is now: "+newStatus);
			//Update Emart Customer Orders && Status
			String sql = orderStatusString(customerID, orders,newStatus);
			updateOrderStatus(sql, stmt);
			//System.out.println("Updated customer: " + sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//print whole table
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCustomers");
		   
		// Iterate through the result and print the data
		System.out.println("Contents of EmartCustomers:");
		System.out.println( "customerID, "+
							"name, "+
							"password, "+
							"email, "+
							"address, "+
							"isManager, "+
							"status, "+
							"order1, "+
							"order2, "+
							"order3"
							);
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(	   rs.getString("customerID").replaceAll("\\s+","")+","+
								   rs.getString("name").replaceAll("\\s+","")+","+
								   rs.getString("password").replaceAll("\\s+","")+","+
								   rs.getString("email").replaceAll("\\s+","")+","+
								   rs.getString("address").replaceAll("\\s+","")+","+
								   rs.getInt("isManager")+","+
								   rs.getString("status").replaceAll("\\s+","")+","+
								   rs.getInt("order1")+","+
								   rs.getInt("order2")+","+
								   rs.getInt("order3") 
							);
		}
		rs.close();
	}
	
	public static void printallformatted( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCustomers");
		   
		// Iterate through the result and print the data
		//System.out.println("contents of EmartCustomers:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Customer ID: "+rs.getString("customerID")+
								", Name: "+  rs.getString("name").trim()+
								", Status: "+rs.getString("status")
							);
		}
		rs.close();
	}
		
	
	public static void printallformattedlong( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartCustomers");
		   
		// Iterate through the result and print the data
		//System.out.println("contents of EmartCustomers:");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println("Customer ID: "+rs.getString("customerID").trim()+
								", Name: "+  rs.getString("name").trim()+
								", Status: "+rs.getString("status").trim()+
								", Email: "+rs.getString("email").trim()+
								", Address: "+rs.getString("address").trim()+
								", Is a manager?: "+rs.getInt("isManager")

							);
		}
		rs.close();
	}
	//remove by Customer ID
	public static void removeByCustomerID(String customerID, Statement stmt){
		String sql = "DELETE FROM EmartCustomers WHERE customerID = "+customerID;
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed customer "+customerID+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	//drop the EmartCustomers table
	public static void dropEmartCustomer(Statement stmt){
			try{
				stmt.executeUpdate("drop table EmartCustomers");
				System.out.println("dropped EmartCustomers table");
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
	
	public static void updateStatus(String customerID, String status, Statement stmt){
		String sql = "Update EmartCustomers "+
				 " SET status='" + status +"'"+
				 " Where "+ " customerID='"+customerID +"'";
		try{
			//System.out.println(sql);
			stmt.executeUpdate(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	private static String getStatusAndUpdate(double orders[]){
		double orderTotals = orders[0]+orders[1]+orders[2];
		if(orderTotals > 500)
			return "Gold";
		else if(orderTotals >100 && orderTotals <=500)
			return "Silver";
		else if(orderTotals > 0 && orderTotals <=100)
			return "Green";
		else
			return "New";
	}

	private static String orderStatusString(String customerID, double orders[], String newStatus){
		String sql = "Update EmartCustomers "+
				 "SET status='" + newStatus + "', " +
				 "order1='" + orders[0] + "', " +
				 "order2='" + orders[1] + "', " +
				 "order3='" + orders[2] + "' " +
				 "Where "+ " customerID='"+customerID +"'";
		return sql;
	}
	
	public static String getCustomerStatus(String customerID, Statement stmt){
		ResultSet rs1;
		String status="";
		String order1="SELECT C.status From  EmartCustomers C WHERE C.customerID ='" + customerID+"'"; 
		try{
			rs1 = stmt.executeQuery(order1);
			rs1.next();
			status = rs1.getString("status");
			//System.out.println(status);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return status;
	}
	
	public static String getCustomerName(String customerID, Statement stmt){
		ResultSet rs1;
		String name="";
		String order1="SELECT C.name From  EmartCustomers C WHERE C.customerID ='" + customerID+"'"; 
		try{
			rs1 = stmt.executeQuery(order1);
			rs1.next();
			name = rs1.getString("name");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		return name;
	}

	public static boolean isManager(String customerID, Statement stmt){
		ResultSet rs1;
		String isManagerString="";
		String order1="SELECT C.isManager From  EmartCustomers C WHERE C.customerID ='" + customerID+"'"; 
		try{
			rs1 = stmt.executeQuery(order1);
			rs1.next();
			isManagerString = rs1.getString("isManager");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
		if(isManagerString.equals("1"))
			return true;
		else
			return false;
	}
}

