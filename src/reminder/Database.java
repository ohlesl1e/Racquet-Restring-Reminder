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

	public Database() throws FileNotFoundException{
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

		for (Customer c : customersArray){
			this.customers.add(c);
		}
	}

	public StringType findString(String s){
		return stringTypeHashMap.get(s);
	}

	public void addString() {
		Scanner i = new Scanner(System.in);
		System.out.print("Enter String Name: ");
		String name = i.nextLine();
		System.out.print("Enter String Material: ");
		String material = i.nextLine();
		System.out.print("Enter String Tensionloss: ");
		double tensionloss = i.nextDouble();
		StringType newString = new StringType(name, material, tensionloss);
		stringTypeHashMap.put(name, newString);
		strings.add(newString);
	}

	public void printAllStrings(){
		for(StringType s : strings){
			System.out.println(s.getName());
		}
	}

	public void saveDatabase() {
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
		for (Customer s : customers) {
			sb.append(gson.toJson(s));
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append("]\n}");

		//print to file
		pw.println(sb);
		pw.close();
	}
}
