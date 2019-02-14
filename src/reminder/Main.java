package reminder;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.lang.*;

import static spark.Spark.*;

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
		port(1234);

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
		/*
		while (quit == false) {
			printMenu();
			switch (input.nextInt()) {
				case 1:
					System.out.println();
					database.addString();
					break;
				case 2:
					System.out.println();
					customerHeap.addCustomer(database, currentDate);
					break;
				case 3:
					System.out.println();
					if (customerHeap.printCustomers(today, 0)) {
						System.out.print("\nRemove Contacted Customers?(Y/N): ");
						String remove = input.next();
						if (remove.equalsIgnoreCase("y")) {
							customerHeap.removePrintedCustomers(today, 0);
						}
					}
					break;
				case 4:
					System.out.println("\nCustomers:");
					customerHeap.showAllCustomers();
					break;
				case 5:
					System.out.println("\nInventory:");
					database.printAllStrings();
					break;
				case 6:
					quit = true;
					break;
				default:
					System.out.println("Invalid Command");
			}
			System.out.println("=====================================================================================================\n");
		}
		*/

		post("/api/add",(request, response) -> {
			return "kool";
		});

		post("/api/remove", (request, response) -> {
			return "fatality";
		});

		path("/api", () -> {
			get("/allcustomers", (request, response) -> {
				return gson.toJson(customerHeap);
			});
			get("/returning", (request, response) -> {
				return customerHeap.printCustomers(today, 0);
			});
		});

		database.saveDatabase(customerHeap);
	}
}
