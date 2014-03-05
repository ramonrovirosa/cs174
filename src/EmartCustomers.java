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
}
