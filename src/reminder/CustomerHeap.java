package reminder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

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

	public void saveDatabase() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("./Data/UserDB.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		StringBuilder sb = new StringBuilder();
		sb.append("{\n \"customers\":[ \n");
		for (Customer s : customers) {
			sb.append(gson.toJson(s));
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append("]\n}");
		pw.println(sb);
		pw.close();
	}

	public class HeapException extends RuntimeException {
		public HeapException(String message) {
			super(message);
		}
	}
}
