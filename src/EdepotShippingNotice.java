import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EdepotShippingNotice {
	static String create_table_sql = "CREATE TABLE EdepotShippingNotice " +
            "(shippingNoticeID CHAR(20) not NULL, " +
            " companyName CHAR(20), " +
            " manufacturer CHAR(20), " +
            " modelno CHAR(20), " +
            " quantity INTEGER, " +
            " shipmentReceived INTEGER, " +
            " PRIMARY KEY ( shippingNoticeID ))";
	
	//Insert new item into table
		public static void insertEdepotItem(String shippingNoticeID, String companyName, String manufacturer, 
											String modelno, int quantity, Statement stmt){
			String sql = "INSERT INTO EdepotShippingNotice ("+
										   "shippingNoticeID,"+
										   "companyName,"+
										   "manufacturer,"+
										   "modelno,"+
										   "quantity,"+
										   "shipmentReceived"+
												") "+
											"Values (" + "'" + shippingNoticeID + "',"
											   + "'" + companyName + "'," 
											   + "'" + manufacturer + "',"
											   + "'" + modelno + "',"
											   + "'" + quantity + "',"
											   + "'" + 0 + "')";	
			try{
				stmt.executeUpdate(sql);
				System.out.println(sql);
			}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		   }
		}
	
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EdepotShippingNotice");
		System.out.println("contents of EdepotShippingNotice:");
		System.out.println("shippingNoticeID,"+
				   "companyName,"+
				   "manufacturer,"+
				   "modelno,"+
				   "quantity,"+
				   "shipmentReceived"
		);

		// Iterate through the result and print the data
		while(rs.next()){
			System.out.println(	   rs.getString("shippingNoticeID").replaceAll("\\s+","")+",    "+
								   rs.getString("companyName").replaceAll("\\s+","")+",         "+
								   rs.getString("manufacturer").replaceAll("\\s+","     ")+
								   rs.getString("modelno").replaceAll("\\s+","")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("shipmentReceived")
				);
		}
		rs.close();
	}

	//drop the EdepotShippingNotice table
		public static void dropEdepotshippingNoticeItem(Statement stmt){
			try{
				stmt.executeUpdate("drop table EdepotShippingNotice");
				System.out.println("dropped EdepotShippingNotice table");
			}catch(SQLException se){
			      //Handle errors for JDBC
				  System.out.println(se);
			      se.printStackTrace();
			}
		}

		public static void updateShipmentReceivedToTrue(String shippingNoticeID, Statement stmt){
			String sql = "Update EdepotShippingNotice "+
					 "SET shipmentReceived='" + 1 + "' " +
					 "Where "+ " shippingNoticeID='"+shippingNoticeID +"'";
			try{
				stmt.executeUpdate(sql);
				//System.out.println(sql);
				//System.out.println("Updated quantity to "+quantity);
			}catch(SQLException se){
			      //Handle errors for JDBC
				  System.out.println(se);
			      se.printStackTrace();
			}
		}

		public static boolean shippingNoticeWasReceived(String shippingNoticeID, Statement stmt){
			ResultSet rs1;
			String query="SELECT * FROM EdepotShippingNotice WHERE shippingNoticeID='"+shippingNoticeID +"'";
			try{
				//get previous orders 1 & 2 for customers
				rs1 = stmt.executeQuery(query);
				if(rs1.next()){
					return true;
				}else
					return false;
				
			}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		   }
			return false;
		}
}
