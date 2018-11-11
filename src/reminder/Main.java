package reminder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

	public static CustomerHeap createHeap(){
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
		for(Customer c : customerArray){
			heap.add(heap.root, c);
		}

		return heap;
	}

	public static int calculateDate(String mains, String crosses, double mTension, double xTension, StringDatabase db){
		return 0;
	}

	public static void addCustomer(CustomerHeap heap, StringDatabase db){
		Scanner in = new Scanner(System.in);
		System.out.print("Enter Customer Name: ");
		String name = in.nextLine();
		System.out.print("Enter Contact Number:");
		int contact = in.nextInt();
		System.out.print("Enter Main String: ");
		String mains = in.nextLine();
		System.out.print("Enter Cross String: ");
		String crosses = in.nextLine();
		System.out.print("Enter Main Tension: ");
		double mTension = in.nextDouble();
		System.out.print("Enter Cross Tension:");
		double xTension = in.nextDouble();
		int date2Return = calculateDate(mains, crosses, mTension, xTension, db);
		heap.add(heap.root, new Customer(name, mains, crosses, mTension, xTension, date2Return, contact));
	}

	public static void printCustomers(CustomerHeap heap){

	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		CustomerHeap customerHeap = createHeap();
		StringDatabase stringDatabase = null;
		try {
			stringDatabase = new StringDatabase();
		}catch (FileNotFoundException | UnsupportedEncodingException e){
			e.printStackTrace();
		}
		while (quit == false){
			printMenu();
			switch (input.nextInt()){
				case 1: stringDatabase.addString();
				break;
				case 2: addCustomer(customerHeap, stringDatabase);
				break;
				case 3: printCustomers(customerHeap);
				break;
				case 4: quit = true;
				break;
				default: System.out.println("Invalid Command");
			}
		}
		stringDatabase.saveDatabase();
	}

}
