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
        String id = br.readLine();
        String name = EmartCustomers.getCustomerName(Integer.parseInt(id),stmt);
        System.out.println("Welcome "+name+"!");
        LoggedInCustomerHandler(stmt);
	}
	
	static void LoggedInCustomerHandler( Statement stmt ) throws IOException, SQLException{
		System.out.println("press 1 to view cart, press 2 to add items to cart, press 3 to view previous orders, press 4 to view your status, press 5 to search for items, press 0 to go back");
        String str = br.readLine();
        if(str.equals("1")){
        
        	
        }else if(str.equals("0")){
        	CustomerHandler(stmt);
        }else{
        	System.out.println("Could not read input...");
        	LoggedInCustomerHandler(stmt);
        }	
	}
	
	static void CreateCustomerHandler( Statement stmt) throws IOException{
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
	
	static void ManagerHandler(Statement stmt){
		System.out.println("called manager handler");
	}
}
