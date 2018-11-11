package reminder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StringDatabase {
    ArrayList<StringType> strings = new ArrayList<>();
    protected static HashMap<String, StringType> stringTypeHashMap = new HashMap<>();

    public StringDatabase() throws FileNotFoundException, UnsupportedEncodingException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("./Data/StringDB.json"));
        JsonParser jp = new JsonParser();
        JsonObject obj = jp.parse(br).getAsJsonObject();

        StringType[] stringArray = gson.fromJson(obj.get("strings"), StringType[].class);

        for (StringType s : stringArray){
            String stringName = s.getName();
            this.strings.add(s);
            stringTypeHashMap.put(stringName, s);
        }
    }

    public void addString(){
        Scanner i = new Scanner(System.in);
        System.out.println("Enter String Name: ");
        String name = i.nextLine();
        System.out.println("Enter String Material: ");
        String material = i.nextLine();
        System.out.println("Enter String Tensionloss: ");
        double tensionloss = i.nextDouble();
        StringType newString = new StringType(name, material, tensionloss);
        stringTypeHashMap.put(name, newString);
        strings.add(newString);
    }

    public void saveDatabase(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("./Data/StringDB.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        sb.append("{\n \"strings\":[ \n");
        for (StringType s : strings){
            sb.append(gson.toJson(s));
            sb.append(",\n");
        }
        sb.deleteCharAt(sb.length()-2);
        sb.append("]\n}");
        pw.println(sb);
        pw.close();
    }
}
