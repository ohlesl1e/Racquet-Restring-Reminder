package reminder;
import com.google.gson.Gson;
import java.util.*;
import java.io.*;

public class Main {
	public static void printMenu(){
		System.out.println("Menu");
		System.out.println("1. Add String to Database");
		System.out.println("2. Add Customers");
		System.out.println("3. Show Restring Required Customers");
		System.out.println("4. Quit");
		System.out.println("Enter a Number: ");
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		while (quit == false){
			printMenu();
			switch (input.nextInt()){
				case 4: quit = true;
				break;
				default: System.out.println("Invalid Command");
			}
		}
	}

}
