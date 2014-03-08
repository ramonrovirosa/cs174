import java.sql.*;

public class dbDriver {
	static Connection conn;
	public static void run() throws SQLException{
		
		// 1. Load the Oracle JDBC driver for this program
		try 
		{
			 DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		}
		catch ( Exception e)
		{ 
		e.printStackTrace(); 
		}
///////////////////////////////////////////////////
//test test alex simes
// 2. Test functions for each query
print_all();

}

public static void print_all() throws SQLException
{}
}