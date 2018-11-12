package reminder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		System.out.println("4. Quit");
		System.out.println("Enter a Number: ");
	}

	public static CustomerHeap createHeap() {
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./Data/UserDB.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JsonParser jp = new JsonParser();
		JsonObject obj = jp.parse(br).getAsJsonObject();

		Customer[] customerArray = gson.fromJson(obj.get("customers"), Customer[].class);
		CustomerHeap heap = new CustomerHeap();
		for (Customer c : customerArray) {
			heap.add(c);
		}

		return heap;
	}

	public static int calculateDate(String mains, String crosses, double mTension, double xTension, StringDatabase db, int date) {
		Scanner x = new Scanner(System.in);
		System.out.print("Enter Days to Return: ");
		int days = x.nextInt();
		int returnDate = date;
		returnDate += 100 * (days / 30);
		returnDate += days % 30;
		return returnDate;
	}

	public static void addCustomer(CustomerHeap heap, StringDatabase db, int date) {
		Scanner in = new Scanner(System.in);
		String name, contact, mains, crosses;
		double mTension, xTension;
		System.out.print("Enter Customer Name: ");
		name = in.nextLine();
		System.out.print("Enter Contact Number: ");
		contact = in.nextLine();
		System.out.print("Enter Main String: ");
		mains = in.nextLine();
		System.out.print("Enter Cross String: ");
		crosses = in.nextLine();
		System.out.print("Enter Main Tension: ");
		mTension = in.nextDouble();
		System.out.print("Enter Cross Tension: ");
		xTension = in.nextDouble();
		int date2Return = calculateDate(mains, crosses, mTension, xTension, db, date);
		heap.add(new Customer(name, mains, crosses, mTension, xTension, date2Return, contact));
	}

	public static void printCustomers(CustomerHeap heap, int date) {
		Scanner ps = new Scanner(System.in);
		boolean theEnd = false;
		int index = 0;
		if (heap.get(index).getDate2Return() > date) {
			theEnd = true;
		} else {
			while (theEnd == false) {
				heap.get(index).printContact();
				index++;
				if (heap.get(index).getDate2Return() > date || index >= heap.size) {
					theEnd = true;
				}
			}
			System.out.print("Remove Contacted Customers?(Y/N): ");
			String remove = ps.next();
			if (remove.equalsIgnoreCase("Y")) {
				for (int i = 0; i < index; i++) {
					heap.remove(0);
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		CustomerHeap customerHeap = createHeap();
		StringDatabase stringDatabase = null;
		try {
			stringDatabase = new StringDatabase();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String string = dateFormat.format(currentDate);
		int today = Integer.parseInt(dateFormat.format(currentDate));
		while (quit == false) {
			printMenu();
			switch (input.nextInt()) {
				case 1:
					stringDatabase.addString();
					break;
				case 2:
					addCustomer(customerHeap, stringDatabase, today);
					break;
				case 3:
					printCustomers(customerHeap, today);
					break;
				case 4:
					quit = true;
					break;
				default:
					System.out.println("Invalid Command");
			}
		}
		stringDatabase.saveDatabase();
		customerHeap.saveDatabase();
	}
}
