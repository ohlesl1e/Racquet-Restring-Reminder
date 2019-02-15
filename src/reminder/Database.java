package reminder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;

public class Database {
	protected ArrayList<StringType> strings = new ArrayList<>();
	protected static HashMap<String, StringType> stringTypeHashMap = new HashMap<>();
	protected ArrayList<Customer> customers = new ArrayList<>();

	public Database() throws FileNotFoundException {
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader("./Data/Database.json"));
		JsonParser jp = new JsonParser();
		JsonObject obj = jp.parse(br).getAsJsonObject();

		StringType[] stringArray = gson.fromJson(obj.get("strings"), StringType[].class);
		Customer[] customersArray = gson.fromJson(obj.get("customers"), Customer[].class);

		for (StringType s : stringArray) {
			String stringName = s.getName();
			this.strings.add(s);
			stringTypeHashMap.put(stringName, s);
		}

		for (Customer c : customersArray) {
			this.customers.add(c);
		}
	}

	public StringType findString(String s) {
		return stringTypeHashMap.get(s);
	}

	public double calculateTensionLoss(double x) {
		double tensionLoss = Math.pow(Math.E, (Math.log(31) / (100 * (x / 62))));
		return tensionLoss;
	}

	public StringType addString(PreAddStr preAdd) {
		String name = preAdd.name;
		String material = preAdd.material;
		double tensionloss = calculateTensionLoss(preAdd.tensionLoss);
		StringType newString = new StringType(name, material, tensionloss);
		stringTypeHashMap.put(name, newString);
		strings.add(newString);
		return newString;
	}

	public void printAllStrings() {
		for (StringType s : strings) {
			System.out.println(s.getName());
		}
	}

	public void saveDatabase(CustomerHeap heap) {
		//clear duplicate strings just in case
		Set<StringType> noDuplicates = new LinkedHashSet<>(strings);
		strings.clear();
		strings.addAll(noDuplicates);
		Collections.sort(strings, StringType.nameComparator);

		PrintWriter pw = null;
		try {
			pw = new PrintWriter("./Data/Database.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		StringBuilder sb = new StringBuilder();

		//saving strings
		sb.append("{\n \"strings\":[ \n");
		for (StringType s : strings) {
			sb.append(gson.toJson(s));
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append("],\n");

		//saving customers
		sb.append("\"customers\":[ \n");
		for (int i = 0; i < heap.size; i++) {
			sb.append(gson.toJson(heap.get(i)));
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append("]\n}");

		//print to file
		pw.println(sb);
		pw.close();
	}
}
