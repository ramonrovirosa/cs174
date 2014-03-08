import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsoleUI {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static void initialPrompt(Statement stmt) throws IOException, SQLException{
		System.out.print("Press 1 for customer interface, press 2 to manage emart, press 3 to manage edepot, press 0 to exit for\n");
        String str = br.readLine();
        if (str.equals("1")){
        	CustomerHandler(stmt);
        }else if (str.equals("2")){
        	ManagerHandler(stmt);
        }else if (str.equals("3")){
        	return;
        }else if (str.equals("0")){
        	return;
        }else{
        	System.out.println("Could not read input...");
        	initialPrompt(stmt);
        }
	}
	
	 static void CustomerHandler( Statement stmt) throws IOException, SQLException{
		System.out.print("press 1 to create new customer account, press 2 to log in, press 0 to go back\n");
        String str = br.readLine();
        if (str.equals("1")){
        	CreateCustomerHandler(stmt);
        	CustomerHandler(stmt);
        }else if (str.equals("2")){
        	LoginCustomerHandler(stmt);	
        }else if (str.equals("0")){
        	initialPrompt(stmt);
        }else{
        	System.out.println("Could not read input...");
        	CustomerHandler(stmt);
        }
	}
	
	static void LoginCustomerHandler( Statement stmt ) throws IOException, SQLException{
	    System.out.println("Current Customers in our system:");
	    EmartCustomers.printall(stmt);
	    System.out.println("Please enter customer ID you wish to log in as:");
        Integer id = Integer.parseInt(br.readLine());
        String name = EmartCustomers.getCustomerName(id,stmt);
        System.out.println("Welcome "+name+"!");
        LoggedInCustomerHandler( stmt, id );
	}
	
	static void LoggedInCustomerHandler( Statement stmt, Integer id ) throws IOException, SQLException{
		System.out.println("press 1 to view cart, press 2 to add items to cart, press 3 to view previous orders, press 4 to view your status, press 5 to search for items, press 0 to go back");
        String str = br.readLine();
        if(str.equals("1")){
        	CustomerViewCartHandler(stmt,id);
        }else if(str.equals("2")){
        	CustomerAddItemsToCart(stmt, id);
        }else if(str.equals("0")){
        	CustomerHandler(stmt);
        }else{
        	System.out.println("Could not read input...");
        	LoggedInCustomerHandler(stmt, id);
        }	
	}
	
	static void CustomerAddItemsToCart( Statement stmt, int id) throws NumberFormatException, IOException, SQLException{
		while(true){
			System.out.println("Enter stockno of item you would like to add to cart");
			int stock_no = Integer.parseInt(br.readLine());
			System.out.println("How many would you like to add?");
			int quantity = Integer.parseInt(br.readLine());
			String item_name = EmartItems.getItemName(stmt, stock_no);
			EmartCart.insertItemInCart(stock_no, id, item_name, quantity, stmt);
			System.out.println("Would you like to add another item? (y/n)");
			String rs = br.readLine();
			if (rs.equals("n")){
				break;
			}else if(!rs.equals("y")){
				System.out.println("Couldn't read input, going back to login menu");
				LoggedInCustomerHandler(stmt, id);
			}
		}
		LoggedInCustomerHandler(stmt, id);
	}
	
	static void CustomerViewCartHandler( Statement stmt, Integer id ) throws SQLException, IOException{
		System.out.println("Here are the contents of your cart:");
		EmartCart.printCustomerCart( stmt, id);
		LoggedInCustomerHandler( stmt, id);
	}
	
	static void CreateCustomerHandler( Statement stmt ) throws IOException{
		String new_stock_no="";
		while(true){
			System.out.println("enter new customer number:\n");
			new_stock_no = br.readLine();
			if (new_stock_no.matches("[0-9][0-9][0-9][0-9][0-9]")){
					break;
			}
			System.out.println("Sorry, invalid customer ID, try again");
		}
       	System.out.println("enter new customer name:\n");
       	String new_name = br.readLine();
       	EmartCustomers.insertEmartCustomer(Integer.parseInt(new_stock_no), new_name, stmt);
       	return;	    
	}
	
	static void ManagerHandler(Statement stmt) throws IOException, SQLException{
		System.out.println("press 1 to edit Emart catalog, press 0 to go back");
        String str = br.readLine();
        if(str.equals("1")){
        	ManagerEditCatalogHandler(stmt);
        }else if(str.equals("0")){
        	initialPrompt(stmt);
        }else{
        	System.out.println("Could not read input...");
        	ManagerHandler(stmt);
        }
	}
	
	static void ManagerEditCatalogHandler(Statement stmt) throws SQLException, IOException{
		System.out.println("Current items in the catalog:");
		EmartItems.printall(stmt);
		System.out.println("press 1 to add items, press 2 to remove items, press 3 to update items, press 0 to go back");
        String str = br.readLine();
		 if(str.equals("1")){
	        	ManagerAddItemHandler(stmt);
	     }else if(str.equals("0")){
	        	ManagerHandler(stmt);
	     }else{
	        	System.out.println("Could not read input...");
	        	ManagerEditCatalogHandler(stmt);
	        }
	}
	
	static void ManagerAddItemHandler(Statement stmt) throws NumberFormatException, IOException, SQLException{
		while(true){
			System.out.println("Enter stockno of new item:");
			int stockno = Integer.parseInt(br.readLine());
			System.out.println("Enter name of new item:");
			String name = br.readLine();
			System.out.println("Enter quantity of new item");
			int quantity = Integer.parseInt(br.readLine());
			System.out.println("Enter price of new item");
			int price = Integer.parseInt(br.readLine());
			EmartItems.insertEmartItem(stockno, name, quantity, price, stmt);
			System.out.println("Would you like to add antoher item? (y/n)");
			String rs = br.readLine();
			if (rs.equals("n")){
				break;
			}else if(!rs.equals("y")){
				System.out.println("Couldn't read input, going back to manager menu");
				ManagerHandler(stmt);
			}
		}
		ManagerEditCatalogHandler(stmt);
	}
}
