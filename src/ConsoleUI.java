import java.io.*;
import java.sql.Statement;

public class ConsoleUI {
	
	static void initialPrompt(Statement stmt) throws IOException{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press 1 for customer interface, press 2 for manager interface, press 3 to exit ");
        String str = br.readLine();
        if (str.equals("1")){
        	CustomerHandler(stmt);
        }else if (str.equals("2")){
        	ManagerHandler(stmt);
        }else if (str.equals("3")){
        	return;
        }else{
        	System.out.println("Could not read input...");
        }
	}
	
	static void CustomerHandler( Statement stmt) throws IOException{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("press 1 to create new customer account, press 2 to go back");
        String str = br.readLine();
        if (str.equals("1")){
        	System.out.println("enter new customer number:");
        	String new_stock_no = br.readLine();
        	System.out.println("enter new customer name:");
        	String new_name = br.readLine();
        	EmartCustomers.insertEmartCustomer(Integer.parseInt(new_stock_no), new_name, stmt);
        	CustomerHandler(stmt);
        }else if (str.equals("2")){
        	initialPrompt(stmt);
        }else{
        	System.out.println("Could not read input...");
        }
	}
	
	static void ManagerHandler(Statement stmt){
		System.out.println("called manager handler");
	}
}
