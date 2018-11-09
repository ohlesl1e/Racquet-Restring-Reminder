package reminder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
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

        for (StringType s : strings){
            String stringName = s.getName();
            strings.add(s);
            stringTypeHashMap.put(stringName, s);
        }
    }

    public void addString(){
        Scanner i = new Scanner(System.in);
        System.out.println("Enter String Name: ");
        String name = i.nextLine();
        System.out.println("Enter String Material: ");
        String material = i.next();
        System.out.println("Enter String Tensionloss: ");
        double tensionloss = i.nextDouble();
        StringType newString = new StringType(name, material, tensionloss);
        stringTypeHashMap.put(name, newString);
        strings.add(newString);
    }
}
