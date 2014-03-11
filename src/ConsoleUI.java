import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsoleUI {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static void initialPrompt(Statement stmt) throws IOException, SQLException{
		System.out.print("Press 1 for customer interface, press 2 to manage emart, press 3 to manage edepot, press 0 to exit\n");
        String str = br.readLine();
        if (str.equals("1")){
        	CustomerHandler(stmt);
        }else if (str.equals("2")){
        	LoginManagerHandler(stmt);
        }else if (str.equals("3")){
        	return;
        }else if (str.equals("0")){
        	System.exit(0);
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
	    EmartCustomers.printallformatted(stmt);
	    System.out.println("Please enter customer ID you wish to log in as:");
        String id = br.readLine();
        String name = EmartCustomers.getCustomerName(id,stmt);
        System.out.println("Welcome "+name.trim()+"!");
        LoggedInCustomerHandler( stmt, id );
	}
	
	static void LoginManagerHandler( Statement stmt ) throws IOException, SQLException{
	    System.out.println("Current Customers in our system:");
	    EmartCustomers.printallformatted(stmt);
	    while(true){
	    	System.out.println("Please enter manager ID you wish to log in as, press 0 to cancel");
	    	String id = br.readLine();
	    	if(id.equals("0")){
	    		initialPrompt(stmt);
	    		break;
	    	}
	    	if(EmartCustomers.isManager(id, stmt)){
		    	String name = EmartCustomers.getCustomerName(id,stmt);
		    	System.out.println("Welcome "+name.trim()+"!");
		    	ManagerHandler( stmt );
		    	break;
	    	}else{
	    		System.out.println("This is not a manager accout no, try again");
	    	}
	    }
	}
	
	static void LoggedInCustomerHandler( Statement stmt, String id ) throws IOException, SQLException{
		System.out.println("press 1 to view cart, press 2 to edit cart contents, press 3 to view/rerun previous orders, "
				+ "\npress 4 to view your status, press 5 to checkout, press 0 to logout");
        String str = br.readLine();
        if(str.equals("1")){
        	CustomerViewCartHandler(stmt,id);
        }else if(str.equals("2")){
        	CustomerEditCart(stmt, id);
        }else if(str.equals("3")){
        	EmartPreviousOrders.printallcustomer(id,stmt);
        	System.out.println("To rerun a previous order, enter the order no. Enter 0 to go back.");
            String str1 = br.readLine();
            if( str1.equals("0")){
        		LoggedInCustomerHandler(stmt,id);
            }else{
            	EmartPreviousOrders.rerunPreviousOrder(Integer.parseInt(str1),stmt);
            	LoggedInCustomerHandler(stmt,id);
            }
        }else if(str.equals("4")){
        	System.out.println("Your current customer status is: "+EmartCustomers.getCustomerStatus(id, stmt).trim());
        	LoggedInCustomerHandler(stmt,id);
        }else if(str.equals("5")){
        	EmartCart.checkoutCart(id,stmt);
        	LoggedInCustomerHandler(stmt,id);
        }else if(str.equals("0")){
        	CustomerHandler(stmt);
        }else{
        	System.out.println("Could not read input...");
        	LoggedInCustomerHandler(stmt, id);
        }	
	}
	
	static void CustomerEditCart( Statement stmt, String id) throws NumberFormatException, IOException, SQLException{
			System.out.println("press 1 to add items to cart, press 2 to search for items, press 3 to remove items from cart, press 0 to go back");
	        String str = br.readLine();
			if(str.equals("1")){
		        	CustomerAddItemsToCart(stmt,id);
			}else if(str.equals("2")){
				CustomerSearchItems(stmt,id);
	        	CustomerEditCart(stmt,id);
		    }else if(str.equals("3")){
		        	CustomerRemoveItemsCart(stmt,id);
		    }else if(str.equals("0")){
		        	LoggedInCustomerHandler(stmt,id);
		    }else{
		        	System.out.println("Could not read input...");
		        	CustomerEditCart(stmt, id);
		    }	
			CustomerEditCart(stmt,id);
	}

	static void CustomerSearchItems(Statement stmt, String id) throws IOException, SQLException{
		System.out.println("If you do not want to search by the prompted critia, just press enter");
		System.out.println("Enter stockno:"); String stockno = br.readLine();
		System.out.println("Enter manufacturer:"); String manu = br.readLine();
		System.out.println("Enter modelno:"); String modelno = br.readLine();
		System.out.println("Enter category:"); String category = br.readLine();
		System.out.println("Enter description:"); String desc = br.readLine();
		EmartItems.searchEmartItems(stockno, category, manu, modelno, desc, stmt);
		LoggedInCustomerHandler(stmt, id);
	}
	
	static void CustomerRemoveItemsCart( Statement stmt, String id) throws NumberFormatException, IOException, SQLException{
		System.out.println("Enter stockno of item you would like to remove from cart, or press 0 to cancel");
		String stock_no = br.readLine();
		System.out.println("Enter quantity you wish to remove from cart, enter 'all' to remove all");
		String q = br.readLine();
        if(stock_no.equals("0")){
        	CustomerEditCart(stmt,id);
        }else{
        	if(q.equals("all")){
        		EmartCart.deleteItemFromCart(stock_no, id, stmt);
        	}else{
        		EmartCart.decrementQuantity(stock_no,id, Integer.parseInt(q),stmt);
        	}
        }
	}

	static void CustomerAddItemsToCart( Statement stmt, String id) throws NumberFormatException, IOException, SQLException{
		while(true){
			System.out.println("Enter stockno of item you would like to add to cart, or press 0 to cancel");
			String stock_no = br.readLine();
	        if(stock_no.equals("0")){
	        	break;
	        }
			System.out.println("How many would you like to add?");
			int quantity = Integer.parseInt(br.readLine());
			String item_name = EmartItems.getItemName(stmt, stock_no);
			EmartCart.insertItemInCart(stock_no, id, item_name, quantity, stmt);
			System.out.println("Would you like to add another item? (y/n)");
			String rs = br.readLine();
			if (rs.equals("n")){
				break;
			}else if(!rs.equals("y")){
				System.out.println("Couldn't read input, going back to cart menu");
				CustomerEditCart(stmt, id);
			}
		}
		CustomerEditCart(stmt, id);
	}
	
	static void CustomerViewCartHandler( Statement stmt, String id ) throws SQLException, IOException{
		System.out.println("Here are the contents of your cart:");
		EmartCart.printCustomerCart( stmt, id);
		LoggedInCustomerHandler( stmt, id);
	}
	
	static void CreateCustomerHandler( Statement stmt ) throws IOException, NumberFormatException, SQLException{
		System.out.println("enter new customer number:");
		String customerID = br.readLine();
       	System.out.println("enter new customer name:");
       	String new_name = br.readLine();
       	System.out.println("enter new customer password:");
       	String email = br.readLine();
       	System.out.println("enter new customer email:");
       	String password = br.readLine();
       	System.out.println("enter new customer address:");
       	String address = br.readLine();
       	System.out.println("is this a manager account? (1 or 0):");
       	String manager = br.readLine();
       	EmartCustomers.insertEmartCustomer(customerID, new_name, password,email, address, Integer.parseInt(manager),stmt);
       	LoggedInCustomerHandler(stmt, customerID);
       	return;	    
	}
	
	static void ManagerHandler(Statement stmt) throws IOException, SQLException{
		
		System.out.println("press 1 manage customers, press 2 to manage catalog, press 3 to sales from last month, press 0 to go back");
        String str = br.readLine();
        if(str.equals("1")){
        	ManagerEditCustomersHandler(stmt);
        }else if(str.equals("2")){
        	ManagerEditCatalogHandler(stmt);
        }else if(str.equals("3")){
        	EmartPreviousOrders.findPreviousOrdersByDate(EmartCart.now(), EmartCart.monthAgo(), stmt );
        	ManagerHandler(stmt);
        }else if(str.equals("0")){
        	initialPrompt(stmt);
        }else{
        	System.out.println("Could not read input...");
        	ManagerHandler(stmt);
        }
	}
	
	static void ManagerEditCustomersHandler(Statement stmt) throws SQLException, IOException{
		System.out.println("press 1 to view customers, press 2 to update customer status, press 0 to go back");
		String str = br.readLine();
        if(str.equals("1")){
        	EmartCustomers.printallformattedlong(stmt);
        	ManagerEditCustomersHandler(stmt);
        }else if(str.equals("2")){
        	System.out.println("Enter id of customer you wish to update the status of, 0 to cancel");
        	String no = br.readLine();
        	if(!no.equals("0")){
            	System.out.println("What would you like the new status to be? (New,Gold,Silver,Green)");
            	String status = br.readLine();
        		EmartCustomers.updateStatus(no,status,stmt);
            	ManagerEditCustomersHandler(stmt);
        	}
        }else if(str.equals("0")){
        	ManagerHandler(stmt);
        }
	}
	
	static void ManagerEditCatalogHandler(Statement stmt) throws SQLException, IOException{
		System.out.println("press 1 to view items, press 2 to add items, press 3 to remove items, press 4 to update items, press 0 to go back");
        String str = br.readLine();
        if(str.equals("1")){
    		EmartItems.printallformatted(stmt);
    		ManagerEditCatalogHandler(stmt);
        }else if(str.equals("2")){
	        	ManagerAddItemHandler(stmt);
		 }else if(str.equals("3")){
			 ManagerRemoveItemHandler(stmt);
		 }else if(str.equals("4")){
			 ManagerUpdateItemHandler(stmt);
	     }else if(str.equals("0")){
	        	ManagerHandler(stmt);
	     }else{
	        	System.out.println("Could not read input...");
	        	ManagerEditCatalogHandler(stmt);
	        }
	}
	
	static void ManagerUpdateItemHandler(Statement stmt) throws NumberFormatException, IOException, SQLException{
		while(true){
		System.out.println("Please enter the stock no of the item you would like to update:");
		String stockno = br.readLine();
		String name = EmartItems.getItemName(stmt, stockno);
		System.out.println("Selected "+name);
		System.out.println("Press 1 to update price, press 2 to update quantity, press 0 to choose a new item");
		String str = br.readLine();
		 if(str.equals("1")){
			 System.out.println("Please enter the new price for "+name+":");
			 int price = Integer.parseInt(br.readLine());
			 EmartItems.updatePrice(stockno, price, stmt);
		 }else if(str.equals("2")){
			 System.out.println("Please enter the new quantity for "+name+":");
			 int quantity = Integer.parseInt(br.readLine());
			 EmartItems.updateQuantity(stockno, quantity, stmt);
		 }else if(str.equals("0")){
			 ManagerEditCatalogHandler(stmt);
		 }
		 System.out.println("Would you like to edit antoher item? (y/n)");
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

	static void ManagerRemoveItemHandler(Statement stmt) throws NumberFormatException, IOException, SQLException{
		while(true){
			System.out.println("Enter stockno of item to remove:");
			String stockno = br.readLine();
			EmartItems.removeByStockNo(stockno, stmt);
			System.out.println("Would you like to remove antoher item? (y/n)");
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
	
	
	static void ManagerAddItemHandler(Statement stmt) throws NumberFormatException, IOException, SQLException{
		while(true){
			String stockno="";
			while(true){
				System.out.println("Enter stockno of new item:");
				stockno = br.readLine();
				if(!stockno.matches("[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]")){
					System.out.println("Invalid stock number, try again");
				}else{
					break;
				}
					
			}
			System.out.println("Enter category of new item:");
			String category = br.readLine();
			System.out.println("Enter manufacturer of new item:");
			String manu = br.readLine();
			System.out.println("Enter modelno of new item:");
			String modelno = br.readLine();
			System.out.println("Enter description of new item:");
			String desc = br.readLine();
			System.out.println("Enter quantity of new item");
			int quantity = Integer.parseInt(br.readLine());
			System.out.println("Enter warrenty of new item");
			int warrenty = Integer.parseInt(br.readLine());			
			System.out.println("Enter price of new item");
			int price = Integer.parseInt(br.readLine());
			EmartItems.insertEmartItem(stockno, category, manu, modelno, desc,warrenty, price,quantity, stmt);
			ManagerAccessoryHandler(stockno,stmt);
			System.out.println("Would you like to add antoher item to Emart? (y/n)");
			String r = br.readLine();
			if (r.equals("n")){
				break;
			}else if(!r.equals("y")){
				System.out.println("Couldn't read input, going back to manager menu");
				ManagerHandler(stmt);
			}
		}
		ManagerEditCatalogHandler(stmt);
	}

	static void ManagerAccessoryHandler(String stockno, Statement stmt) throws IOException{
		System.out.println("Is this item an accessory of another item?(y,n)");
		if (br.readLine().equals("y")){
			while(true){
				System.out.println("Please enter the stock number of an item that has this item as an accessory");
				String parent = br.readLine();
				EmartAccessories.insertAccessory(parent, stockno, stmt);
				System.out.println("Would you like to add another item  that has this item as an accessory?(y,n)");
				if(br.readLine().equals("n")){
					break;
				}
			}
		}
		System.out.println("Does this item have accessories?(y,n)");
		if (br.readLine().equals("y")){
			while(true){
				System.out.println("Please enter the stock number of an item that accessorizes this item");
				String parent = br.readLine();
				EmartAccessories.insertAccessory(stockno, parent, stmt);
				System.out.println("Would you like to add another accessory for this item?(y,n)");
				if(br.readLine().equals("n")){
					break;
				}
			}
		}
		return;
	}

}
