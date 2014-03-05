import java.io.*;

public class ConsoleUI {
	
	static void initialPrompt() throws IOException{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press 1 for Emart, press 2 for Edepot");
        String str = br.readLine();
        if (str.equals("1")){
        	EmartHandler();
        }else if (str.equals("2")){
        	EdepotHandler();
        }else{
        	System.out.println("Could not read input...");
        }
	}
	
	static void EmartHandler(){
		System.out.println("called emart handler function");
	}
	
	static void EdepotHandler(){
		System.out.println("called edepot handler");
	}
}
