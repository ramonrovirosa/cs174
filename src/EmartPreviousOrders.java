
public class EmartPreviousOrders {
	String sql= "CREATE TABLE EmartPreviousOrders "+
				"(orderno INTEGER not NULL, " +
	            " customerID INTEGER not null, " + 
	            " itemID INTEGER not null, " +
	            " quantity INTEGER, " +
	            " date CHAR(20), " +
	            " price INTEGER,"+
	            " PRIMARY KEY (orderno, customerID),"+
	            " FOREIGN KEY (customerID) REFERENCES EmartCustomers(customerID),"+
	            " FOREIGN KEY (itemID) REFERENCES EmartItems(itemID) )";
}
