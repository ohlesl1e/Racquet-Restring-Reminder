package reminder;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {
	public static void printMenu() {
		System.out.println("Menu");
		System.out.println("1. Add String to Database");
		System.out.println("2. Add Customers");
		System.out.println("3. Show Restring Required Customers");
		System.out.println("4. Show All Customers");
		System.out.println("5. Show All Strings");
		System.out.println("6. Quit");
		System.out.print("Enter a Number: ");
	}

	public static CustomerHeap createHeap(Database db) {
		CustomerHeap heap = new CustomerHeap();
		for (Customer c : db.customers) {
			heap.add(c);
		}
		return heap;
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		Database database = null;
		try {
			database = new Database();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CustomerHeap customerHeap = createHeap(database);
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int today = Integer.parseInt(dateFormat.format(currentDate.getTime()));
		while (quit == false) {
			printMenu();
			switch (input.nextInt()) {
				case 1:
					System.out.println();
					database.addString();
					System.out.println("=====================================================================================================\n");
					break;
				case 2:
					System.out.println();
					customerHeap.addCustomer(database, currentDate);
					System.out.println("=====================================================================================================\n");
					break;
				case 3:
					System.out.println();
					customerHeap.printCustomers(today);
					System.out.println("=====================================================================================================\n");
					break;
				case 4:
					System.out.println("\nCustomers:");
					customerHeap.showAllCustomers();
					System.out.println("=====================================================================================================\n");
					break;
				case 5:
					System.out.println("\nInventory:");
					database.printAllStrings();
					System.out.println("=====================================================================================================\n");
					break;
				case 6:
					quit = true;
					System.out.println("=====================================================================================================\n");
					break;
				default:
					System.out.println("Invalid Command");
					System.out.println("=====================================================================================================\n");
			}
		}
		database.saveDatabase();
	}
}
