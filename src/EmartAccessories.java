import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmartAccessories {
	static String create_table_sql = " CREATE TABLE EmartAccessories" +
									 " (stockno INTEGER not NULL, " + 
									 " accesoryno INTEGER, "+
									 " PRIMARY KEY (stockno, accesoryno),"+
									 " FOREIGN KEY (stockno) REFERENCES EmartItems (stockno)," +
									 " FOREIGN KEY (accesoryno) REFERENCES EmartItems (stockno)" +
									 ")";
	public static void insertAccessory(int stockno, int accesoryno, Statement stmt){
		String insert = "INSERT INTO EmartAccessories ("+
												   "stockno,"+
												   "accesoryno"+
												   ") "+
												   "Values (" + "'" + stockno + "',"
													   + "'" + accesoryno + "')";
		try{
			stmt.executeUpdate(insert);
			System.out.println(insert);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
	   }	
	}
	//print
	public static void printAccessory(int stockno, Statement stmt){
		ResultSet rs1;
		String query = "Select accesoryno FROM EmartAccessories WHERE stockno='"+stockno +"'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				System.out.println("Accessory for Item "+stockno+": "+ rs1.getInt("accesoryno"));
			}
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}

	//delete
	public static void deleteAccessory(int stockno, int accessoryno, Statement stmt){
		String sql = " DELETE FROM EmartAccessories WHERE stockno = "+stockno+
				 	 " AND accesoryno='"+accessoryno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed EmartAccessories "+accessoryno+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void dropEmartAccessories(Statement stmt){
		try{
			stmt.executeUpdate("drop table EmartAccessories");
			System.out.println("dropped EmartItems table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
}
