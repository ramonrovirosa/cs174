import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EmartItems {
	static String create_table_sql = "CREATE TABLE EmartItems " +
						             "(stockno CHAR(20) not NULL, " +
						             " category CHAR(20), " +
						             " manufacturer CHAR(20), " +
						             " modelno CHAR(20), " +
						             " description CHAR(500), " +
						             " warranty INTEGER, " +
						             " price INTEGER, " +
						             " quantity INTEGER, " +
						             " PRIMARY KEY ( stockno ))";
							
	
	public static void searchEmartItems(String stockno, String category, String manu, String modelno, String desc, Statement stmt) throws SQLException{
		String sql = constructSearchQuery(stockno, category, manu, modelno, desc);
		ResultSet rs = stmt.executeQuery (sql);
		System.out.println("Your search returned:");
		System.out.println("stockno,"+
						   "category,"+
						   "manufacturer,"+
						   "modelno,"+
						   "description,"+
						   "warranty,"+
						   "price,"+
						   "quantity");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+","+
								   rs.getString("category").replaceAll("\\s+","")+","+
								   rs.getString("manufacturer").replaceAll("\\s+","")+","+
								   rs.getString("modelno").replaceAll("\\s+","")+","+
								   rs.getString("description").replaceAll("\\s+","")+",  "+
								   rs.getInt("warranty")+","+
								   rs.getInt("price")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("price")
							);
		}
		rs.close();
	}
	
	//insertEmartItem
	public static void insertEmartItem(String stockno, String category,String manufacturer,
									   String modelno, String description,
									   int warranty,int price, int quantity, 
									   Statement stmt){
		String sql = "INSERT INTO EmartItems ("+
												   "stockno,"+
												   "category,"+
												   "manufacturer,"+
												   "modelno,"+
												   "description,"+
												   "warranty,"+
												   "price,"+
												   "quantity"+
												   ") "+
									"Values (" + "'" + stockno + "',"
											   + "'" + category + "'," 
											   + "'" + manufacturer + "',"
											   + "'" + modelno + "',"
											   + "'" + description + "',"
											   + "'" + warranty + "',"
											   + "'" + price + "',"
											   + "'" + quantity + "')";	
		try{
			stmt.executeUpdate(sql);
			System.out.println("Inserted "+quantity+" "+manufacturer+" into Emart");
			//System.out.println(sql);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	
	public static String getItemName( Statement stmt, String stockno ) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT category from EmartItems where stockno ='"+stockno+"'");
		rs.next();
		String name = rs.getString("category").replaceAll("\\s+","");
		rs.close();
		return name;		
	}
	
	public static int getItemPrice( Statement stmt, String stockno ) throws SQLException{
		ResultSet rs3 = stmt.executeQuery("SELECT price from EmartItems where stockno ='"+stockno+"'");
		rs3.next();
		int price = rs3.getInt("price");
		rs3.close();
		return price;		
	}
	
	//print all items
	public static void printall( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems");
		   
		// Iterate through the result and print the data
		System.out.println("Contents of EmartItems:");
		System.out.println("stockno,"+
						   "category,"+
						   "manufacturer,"+
						   "modelno,"+
						   "description,"+
						   "warranty,"+
						   "price,"+
						   "quantity,"+
						   ") ");
		while(rs.next()){
			// Get the value from column "columnName" with integer type
			System.out.println(	   rs.getString("stockno").replaceAll("\\s+","")+","+
								   rs.getString("category").replaceAll("\\s+","")+","+
								   rs.getString("manufacturer").replaceAll("\\s+","")+",  desc:"+
								   rs.getString("modelno").replaceAll("\\s+","")+","+
								   rs.getString("description").replaceAll("\\s+","")+",  "+
								   rs.getInt("warranty")+","+
								   rs.getInt("price")+","+
								   rs.getInt("quantity")+","+
								   rs.getInt("price")
							);
		}
		rs.close();
	}
	
	public static void printallformatted( Statement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery ("select * from EmartItems");
		   
		// Iterate through the result and print the data
		System.out.println("Contents of your Emart Catalog:");
		ArrayList<itemVar> rs1 = new ArrayList<itemVar>();

		while(rs.next()){
			itemVar item = new itemVar(rs.getInt("quantity"),rs.getString("stockno"),rs.getString("category"),rs.getInt("price"),rs.getString("manufacturer"),rs.getString("modelno"),rs.getString("description"),rs.getInt("warranty"));
			rs1.add(item);
		}
		rs.close();
		for(int i=0;i<rs1.size();i++){
			// Get the value from column "columnName" with integer type
			System.out.println("******************************");
			System.out.println("Stock Number:"+rs1.get(i).getItemID().trim()+
							", Category:"+rs1.get(i).getCategory().trim()+
							", Price: "+rs1.get(i).getPrice()+
							", Quantity: "+rs1.get(i).getQuantity()+ 
							", Manufacturer: "+rs1.get(i).getManu().trim()+ 
							", ModelNo: "+rs1.get(i).getModel().trim()+ 
							", Description: "+rs1.get(i).getDesc().trim()+ 
							", Warrenty: "+rs1.get(i).getWarrenty()
							);
			EmartAccessories.printAccessory(rs1.get(i).getItemID(),stmt);
		}
		System.out.println("******************************");
	}
	
	//update quantity
	public static void updateItemQuantityAdd(String stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno ='" + stockno+"'"; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(item1);
			rs1.next();
			int currentQuantity= rs1.getInt("quantity");
			int newQuantity=currentQuantity+quantity;
			//Update Emart Customer Orders && Status
			updateQuantity(stockno, newQuantity, stmt);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateItemQuantitySubtract(String stockno, int quantity, Statement stmt){
		ResultSet rs1;
		String item1="SELECT * From  EmartItems C WHERE C.stockno ='" + stockno+"'"; 
		try{
			//get previous orders 1 & 2 for customers
			rs1 = stmt.executeQuery(item1);
			rs1.next();
			int currentQuantity= rs1.getInt("quantity");
			int newQuantity=currentQuantity-quantity;
			//Update Emart Customer Orders && Status
			updateQuantity(stockno, newQuantity, stmt);
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	public static void updateQuantity(String stockno, int quantity, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET quantity='" + quantity + "' " +
				 "Where "+ " stockno='"+stockno +"'";
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
	//update emart items Price...
	public static void updatePrice(String stockno, int price, Statement stmt){
		String sql = "Update EmartItems "+
				 "SET price='" + price + "' " +
				 "Where "+ " stockno='"+stockno +"'";
		try{
			stmt.executeUpdate(sql);
			//System.out.println(sql);
			System.out.println("Set price to "+price);
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	//remove item by stock#
	public static void removeByStockNo(String stockno, Statement stmt){
		String sql = "DELETE FROM EmartItems WHERE stockno = '"+stockno+"'";
		try{
			stmt.executeUpdate(sql);
			System.out.println("removed item no. "+stockno+" from Emart");
		}catch(SQLException se){
	      //Handle errors for JDBC
		  System.out.println(se);
	      se.printStackTrace();
	   }
	}
	//drop table
	public static void dropEmartItems(Statement stmt){
		try{
			stmt.executeUpdate("drop table EmartItems");
			System.out.println("dropped EmartItems table");
		}catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println(se);
		      se.printStackTrace();
		}
	}
	
	public static String constructSearchQuery(String stockno, String category, String manu, String modelno, String desc){
		int whereflag=0;
		String sql = "select * from EmartItems ";
		if(!stockno.equals("")){
			if (whereflag==0){
				sql+="WHERE stockno LIKE '%"+stockno+"%'";
				whereflag=1;
			}else
				sql+=" AND stockno LIKE '%"+stockno+"%'";
		}
		if(!category.equals("")){
			if (whereflag==0){
				sql+="WHERE category LIKE '%"+category+"%'";
				whereflag=1;
			}else
				sql+=" AND category LIKE '%"+category+"%'";
		}
		if(!manu.equals("")){
			if (whereflag==0){
				sql+="WHERE manufacturer LIKE '%"+manu+"%'";
				whereflag=1;
			}else
				sql+=" AND manufacturer LIKE '%"+manu+"%'";
		}
		if(!modelno.equals("")){
			if (whereflag==0){
				sql+="WHERE modelno LIKE '%"+modelno+"%'";
				whereflag=1;
			}else
				sql+=" AND modelno LIKE '%"+modelno+"%'";
		}
		if(!desc.equals("")){
			if (whereflag==0){
				sql+="WHERE description LIKE '%"+desc+"%'";
				whereflag=1;
			}else
				sql+=" AND description LIKE '%"+desc+"%'";
		}
		return sql;
	}
}

class itemVar{
	int quantity;
	String itemID;
	String category;
	int price;
	String manu;
	String modelno;
	String desc;
	int warrenty;
	public itemVar(int q, String i, String c, int p, String man, String md, String d, int w){
		quantity=q;
		itemID=i;
		category=c;
		price = p;
		desc=d;
		manu = man;
		warrenty=w;
		modelno = md;
	}
	public int getQuantity(){
		return quantity;
	}
	public int getWarrenty(){
		return warrenty;
	}
	public String getItemID(){
		return itemID;
	}
	public String getManu(){
		return manu;
	}
	public String getModel(){
		return modelno;
	}
	public String getDesc(){
		return desc;
	}
	public String getCategory(){
		return category;
	}
	public int getPrice(){
		return price;
	}
}


