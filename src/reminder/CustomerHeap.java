package reminder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerHeap {
	private ArrayList<Customer> customers;
	protected int size;

	public CustomerHeap() {
		customers = new ArrayList<>();
	}

	public Customer get(int index) {
		if (isEmpty()) {
			throw new HeapException("No Customer");
		} else {
			return this.customers.get(index);
		}
	}

	public boolean isEmpty() {
		return (customers.size() == 0);
	}

	private int getLeftChildIndex(int index) {
		return index * 2 + 1;
	}

	private int getRightChildIndex(int index) {
		return index * 2 + 2;
	}

	private int getParentIndex(int index) {
		return (index - 1) / 2;
	}

	public void add(Customer newCustomer) {
		customers.add(newCustomer);
		heapifyUp(customers.size() - 1);
		size++;
	}

	public void remove(int index) {
		Collections.swap(customers, index, customers.size() - 1);
		customers.remove(customers.size() - 1);
		heapifyDown(index);
		size--;
	}

	public void heapifyUp(int index) {
		int parentIndex;
		if (index != 0) {
			parentIndex = getParentIndex(index);
			if (customers.get(parentIndex).getDate2Return() > customers.get(index).getDate2Return()) {
				Collections.swap(customers, parentIndex, index);
				heapifyUp(parentIndex);
			}
		}
	}

	public void heapifyDown(int index) {
		int leftIndex, rightIndex, minIndex;
		leftIndex = getLeftChildIndex(index);
		rightIndex = getRightChildIndex(index);
		if (rightIndex >= customers.size()) {
			if (leftIndex >= customers.size())
				return;
			else
				minIndex = leftIndex;
		} else {
			if (customers.get(leftIndex).getDate2Return() <= customers.get(rightIndex).getDate2Return())
				minIndex = leftIndex;
			else
				minIndex = rightIndex;
		}
		if (customers.get(index).getDate2Return() > customers.get(minIndex).getDate2Return()) {
			Collections.swap(customers, minIndex, index);
			heapifyDown(minIndex);
		}
	}

	public void showAllCustomers(){
		for (Customer c : customers){
			c.printContact();
		}
	}

	public int calculateDate(String mains, String crosses, double mTension, double xTension, Database db, Calendar date) {
		int days = 0;
		int returnDate = 0;
		if(mains.equalsIgnoreCase(crosses) || db.findString(mains).getTensionLoss() < db.findString(crosses).getTensionLoss()){
			days = (int) (Math.pow(db.findString(mains).getTensionLoss(), 25) - 1);
		} else {
			days = (int) (Math.pow(db.findString(crosses).getTensionLoss(), 25) - 1);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		date.add(Calendar.DATE, days);
		returnDate = Integer.parseInt(dateFormat.format(date.getTime()));
		return returnDate;
	}

	public void addCustomer(Database db, Calendar date) {
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
		Customer newCustomer = new Customer(name, mains, crosses, mTension, xTension, date2Return, contact);
		add(newCustomer);
		db.customers.add(newCustomer);
	}

	public void printCustomers(int date) {
		Scanner ps = new Scanner(System.in);
		boolean theEnd = false;
		int index = 0;
		if (get(index).getDate2Return() > date) {
			return;
		} else {
			while (theEnd == false) {
				get(index).printContact();
				index++;
				if (get(index).getDate2Return() > date || index >= size) {
					theEnd = true;
				}
			}
			System.out.print("Remove Contacted Customers?(Y/N): ");
			String remove = ps.nextLine();
			if (remove.equalsIgnoreCase("Y")) {
				for (int i = 0; i < index; i++) {
					remove(0);
				}
			}
		}
	}

	public class HeapException extends RuntimeException {
		public HeapException(String message) {
			super(message);
		}
	}
}
