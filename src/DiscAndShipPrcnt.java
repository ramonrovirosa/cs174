import java.sql.SQLException;
import java.sql.Statement;


public class DiscAndShipPrcnt {
	static String create_table_sql = "CREATE TABLE DiscAndShipPrcnt " +
            "(name CHAR(20) not NULL, " +
            " percentage INTEGER not NULL, " + 
            " PRIMARY KEY ( name ))";
	
	public static void insertDefaults(Statement stmt){
		String gold = "INSERT INTO DiscAndShipPrcnt ("+
												   "name,"+
												   "percentage"+
												   ") "+
									"Values (" + "'" + "Gold" + "',"
											   + "'" + 10 + "')";
		String silver = "INSERT INTO DiscAndShipPrcnt ("+
													   "name,"+
													   "percentage"+
													   ") "+
								"Values (" + "'" + "Silver" + "',"
										   + "'" + 5 + "')";

		String green = "INSERT INTO DiscAndShipPrcnt ("+
												   "name,"+
												   "percentage"+
												   ") "+
									"Values (" + "'" + "Green" + "',"
											   + "'" + 0 + "')";
		String shipping = "INSERT INTO DiscAndShipPrcnt ("+
													   "name,"+
													   "percentage"+
													   ") "+
								"Values (" + "'" + "Shipping" + "',"
										   + "'" + 10 + "')";
		try{
			stmt.executeUpdate(gold);
			stmt.executeUpdate(silver);
			stmt.executeUpdate(green);
			stmt.executeUpdate(shipping);
			System.out.println(shipping);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	//update percentage
	public static void updatePercentage(String name, int percentage, Statement stmt){
		String sql = "Update DiscAndShipPrcnt "+
				 " SET percentage='" + percentage +"'"+
				 " Where "+ " name='"+name +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println(sql);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	//Drop Table
	public static void dropDiscAndShipPrcnt(Statement stmt){
		try{
			stmt.executeUpdate("drop table DiscAndShipPrcnt");
			System.out.println("dropped DiscAndShipPrcnt table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
}
}
