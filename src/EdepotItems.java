import java.sql.*;
import java.util.ArrayList;


public class EdepotItems {
	static String create_table_sql = "CREATE TABLE EdepotItems " +
            "(stockno CHAR(20) not NULL, " +
            " manufacturer CHAR(20), " +
            " modelno CHAR(20), " +
            " min INTEGER, " +
            " quantity INTEGER, " +
            " max INTEGER, " +
            " location CHAR(20), " +
            " replenishment INTEGER, " +
            " PRIMARY KEY ( stockno ))";
	
//	//get name by stockno
//	public static void getNamebyStockNO(int stockno, Statement stmt) throws SQLException{
//		ResultSet rs = stmt.executeQuery ("select name from EdepotItems where stockno = "+stockno);
//
//		// Iterate through the result and print the data
//		System.out.println("name where stockno is "+stockno+":");
//		while(rs.next()){
//			// Get the value from column "columnName" with integer type
//			System.out.println(rs.getString("name"));
//		}
//		rs.close();
//	}
	

	public static String getManufacturer(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select manufacturer FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		String manu = rs1.getString("manufacturer");
		return manu;
	}
	public static String getLocation(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select location FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		String loc = rs1.getString("location");
		return loc;
	}
	public static String getModelno(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select modelno FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		String modelno = rs1.getString("modelno");
		return modelno;
	}
	public static int getMin(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select min FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		int min = rs1.getInt("min");
		return min;
	}
	public static int getMax(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select max FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		int max = rs1.getInt("max");
		return max;
	}

	//print whole table
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EdepotItems");
		System.out.println("contents of EdepotItems:");
		System.out.println("stockno,"+
				   "manufacturer,"+
				   "modelno,"+
				   "min,"+
				   "quantity,"+
				   "max,"+
				   "location,"+
				   "replenishment");

		// Iterate through the result and print the data
		while(rs.next()){
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+",    "+
								   rs.getString("manufacturer").replaceAll("\\s+","")+",         "+
								   rs.getString("modelno").replaceAll("\\s+","     ")+
								   rs.getInt("min")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("max")+",     "+
								   rs.getString("location").replaceAll("\\s+","")+","+
								   rs.getInt("replenishment")
				);
		}
		rs.close();
	}
	//remove by stockno
	public static void removebystockno(String stockno, Statement stmt){
		String sql = "DELETE FROM EdepotItems WHERE stockno = '"+stockno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed item no. "+stockno+" from the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//Insert new item into table
	public static void insertEdepotItem(String stockno, String manufacturer, String modelno, 
										int min, int quantity, int max,
										String location, Statement stmt){
		String sql = "INSERT INTO EdepotItems ("+
												   "stockno,"+
												   "manufacturer,"+
												   "modelno,"+
												   "min,"+
												   "quantity,"+
												   "max,"+
												   "location,"+
												   "replenishment"+
											") "+
										"Values (" + "'" + stockno + "',"
										   + "'" + manufacturer + "'," 
										   + "'" + modelno + "',"
										   + "'" + min + "',"
										   + "'" + quantity + "',"
										   + "'" + max + "',"
										   + "'" + location + "', 0 )";
		try{
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Inserted item no. "+stockno+" into Edepot");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateEdepotQuantity(String stockno, int quantity, Statement stmt) throws SQLException{
		String sql = "Update EdepotItems "+
					 "SET quantity='" + quantity + "' " +
					 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Updated quantity for "+stockno+" in the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		checkIfNeedToSendReplenishmentOrder(stockno,stmt);

	}
	public static void subtractQuantityForItemsSold(String stockno, int quantitySold, Statement stmt)throws SQLException{
		int quantity = getQuantityForItem(stockno, stmt);
		int newQuantity = quantity - quantitySold;
		updateEdepotQuantity(stockno, newQuantity, stmt);
		//check to see if you need to send a replenishment order
		checkIfNeedToSendReplenishmentOrder(stockno,stmt);
	}
	
	public static int getQuantityForItem(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select quantity FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		int quantity = rs1.getInt("quantity");
		return quantity;
	}
	public static int getReplenishmentForItem(String stockno,Statement stmt)throws SQLException{
		ResultSet rs1;
		String sql = "Select replenishment FROM EdepotItems WHERE stockno='"+stockno +"'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		int replenishment = rs1.getInt("replenishment");
		System.out.println(replenishment);
		return replenishment;
	}
	public static void setReplenishmentToZero(String stockno, Statement stmt){
		updateEdepotReplenishment(stockno, 0, stmt);
	}
	
	public static void updateEdepotReplenishment(String stockno, int replenishment, Statement stmt){
		String sql = "Update EdepotItems "+
					 "SET replenishment='" + replenishment + "' " +
					 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("Updated replenishment for "+stockno+" in the database");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	//drop the EdepotItems table
	public static void dropEdepotItem(Statement stmt){
		try{
			stmt.executeUpdate("drop table EdepotItems");
			System.out.println("dropped edepotitems table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	public static void receiveShippingNotice(String shippingNoticeID, String companyName,
			String stockno, String manufacturer, String modelno, 
			int min, int quantity, int max,
			String location, Statement stmt){
				
		//First check to see if item is allready in table
		ResultSet rs1;
		String item1="SELECT * From  EdepotItems WHERE stockno ='" + stockno+"'"; 
		try{
			//
			rs1 = stmt.executeQuery(item1);
			if(rs1.next()){
				//update replenishment
				updateEdepotReplenishment(stockno, quantity, stmt);
			}else{
				//insert item in table 
				
				insertEdepotItem(stockno,manufacturer, modelno, 
						min, quantity, max,
						location, stmt);
			}	
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
		EdepotShippingNotice.insertEdepotShippingNotice(shippingNoticeID, stockno, companyName, manufacturer, modelno, quantity,  stmt);
	
	}

	public static void receiveShipment(String shippingNoticeID, Statement stmt)throws SQLException{	
		//check that a shipping notice was received
		String stockno = EdepotShippingNotice.getStockNo(shippingNoticeID, stmt);
		boolean received = EdepotShippingNotice.shippingNoticeWasReceived(shippingNoticeID, stmt);
		if(received){
			EdepotShippingNotice.updateShipmentReceivedToTrue(shippingNoticeID,stmt);
			int newQuantity = getReplenishmentForItem(stockno, stmt);
			newQuantity+= getQuantityForItem(stockno,stmt);
			updateEdepotQuantity(stockno, newQuantity,stmt);
			setReplenishmentToZero(stockno,stmt);
		}else{
			System.out.println("SORRY, The Shipping Notice Was Never Received");
		}
		
	}

	public static void checkIfNeedToSendReplenishmentOrder(String stockno,Statement stmt)throws SQLException{
		String sql = "Select manufacturer FROM EdepotItems WHERE stockno='"+stockno+"'";
		ResultSet rs = stmt.executeQuery (sql);
		rs.next();
		String manufacturer= rs.getString("manufacturer").replaceAll("\\s+","");
		String checkQuantity="Select stockno, quantity, max "+
							 "FROM EdepotItems "+
							 "WHERE manufacturer='"+manufacturer+"' "+
							 "AND min>=quantity";
		rs=stmt.executeQuery(checkQuantity);
		ArrayList<replenishMentOrderVariables> rs1 = new ArrayList<replenishMentOrderVariables>();
		replenishMentOrderVariables item;
		while(rs.next()){
			item=new replenishMentOrderVariables(
					 rs.getString("stockno").replaceAll("\\s+",""),
					 rs.getInt("quantity"),
					 rs.getInt("max")
					);
			rs1.add(item);
		}
		if(rs1.size()>=3){
			System.out.println("Need to Send replenishment order to manufacturer "+manufacturer+" for: ");
			for(int i=0;i<rs1.size();i++){
				System.out.println(
							"Stocknumber: "+ rs1.get(i).getS() + ", "+
							"Manufacturer: "+ manufacturer + ", "+
							"Quantity to order: "+ (rs1.get(i).getM() - rs1.get(i).getQ())
						);
			}
		}
	}
	
}
class replenishMentOrderVariables{
	String stockno;
	int quantity, max;
	public replenishMentOrderVariables(String no, int q, int m){
		stockno=no;
		quantity=q;
		max=m;
	}
	public String getS(){
		return stockno;
	}
	public int getQ(){
		return quantity;
	}
	public int getM(){
		return max;
	}
} 
