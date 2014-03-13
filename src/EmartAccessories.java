import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EmartAccessories {
	static String create_table_sql = " CREATE TABLE EmartAccessories" +
									 " (stockno CHAR(20) not NULL, " + 
									 " accesoryno CHAR(20), "+
									 " PRIMARY KEY (stockno, accesoryno),"+
									 " FOREIGN KEY (stockno) REFERENCES EmartItems (stockno)," +
									 " FOREIGN KEY (accesoryno) REFERENCES EmartItems (stockno)" +
									 ")";
	public static void insertAccessory(String stockno, String accesoryno, Statement stmt){
		String insert = "INSERT INTO EmartAccessories ("+
												   "stockno,"+
												   "accesoryno"+
												   ") "+
												   "Values (" + "'" + stockno + "',"
													   + "'" + accesoryno + "')";
		try{
			stmt.executeUpdate(insert);
			//System.out.println(insert);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
	   }	
	}
	
	public static ArrayList<String> getAccessories(String stockno, Statement stmt) throws SQLException{
		ArrayList<String> accessories = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery ("select accesoryno from EmartAccessories where stockno ='"+stockno+"'");
		while(rs.next()){
			accessories.add(rs.getString("accesoryno"));
		}
		rs.close();
		return accessories;
	}
	
	//print
	public static void printAccessory(String stockno, Statement stmt){
		ResultSet rs1;
		String query = "Select accesoryno FROM EmartAccessories WHERE stockno='"+stockno +"'";
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				System.out.println("An accessory for "+stockno.trim()+" is: "+ rs1.getString("accesoryno").trim());
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
