package reminder;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.lang.*;

import static com.mongodb.client.model.Filters.eq;
import static spark.Spark.*;

class PreAddCust {
    String name;
    String contact;
    String email;
    String mainString;
    String crossString;
    double mainTension;
    double crossTension;
}

class PreAddStr {
    String name;
    String material;
    double tensionLoss;
}

public class Main {
    public static double calculateTensionLoss(double x) {
        double tensionLoss = Math.pow(Math.E, (Math.log(31) / (100 * (x / 62))));
        return tensionLoss;
    }

    public static StringType addString(PreAddStr preAdd) {
        String name = preAdd.name;
        String material = preAdd.material;
        double tensionloss = calculateTensionLoss(preAdd.tensionLoss);
        StringType newString = new StringType(name, material, tensionloss);
        return newString;
    }

    public static Calendar calculateDate(String mains, String crosses, MongoCollection<Document> db, Calendar date) {
        int days;
        Calendar returnDate = (Calendar) date.clone();
        Document dbMain = db.find(eq("name", mains)).first();
        Document dbCross = db.find(eq("name", crosses)).first();
        if (mains.equalsIgnoreCase(crosses) || dbMain.getDouble("tensionLoss") < dbCross.getDouble("tensionLoss")) {
            days = (int) (Math.pow(dbMain.getDouble("tensionLoss"), 25) - 1);
        } else {
            days = (int) (Math.pow(dbCross.getDouble("tensionLoss"), 25) - 1);
        }
        if (days > 365) {
            days = 365;
        }
        returnDate.add(Calendar.DATE, days);
        return returnDate;
    }

    public static Customer addCustomer(MongoCollection db, Calendar date, PreAddCust preAdd) {
        String name = preAdd.name;
        String contact = preAdd.contact;
        String mains = preAdd.mainString;
        String crosses = preAdd.crossString;
        String email = preAdd.email;
        double mTension = preAdd.mainTension;
        double xTension = preAdd.crossTension;
        Calendar date2Return = null;
        try {
            date2Return = calculateDate(mains, crosses, db, date);
        } catch (NullPointerException e) {
            System.out.println("Fail to add customer. (string not found)");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat otherFormat = new SimpleDateFormat("MM/dd/yyyy");
        Customer newCustomer = new Customer(name, mains, crosses, mTension, xTension, Integer.parseInt(dateFormat.format(date2Return.getTime())), otherFormat.format(date2Return.getTime()), contact, email);
        return newCustomer;
    }

    public static ArrayList getAll(MongoCollection collection){
        MongoCursor<Document> stringCursor = collection.find().iterator();
        ArrayList<Document> all = new ArrayList<>();
        while (stringCursor.hasNext()){
            all.add(stringCursor.next());
        }
        return all;
    }

    public static ArrayList getDue(MongoCollection collection, int date){
        MongoCursor<Document> cursor = collection.find().iterator();
        ArrayList<Document> all = new ArrayList<>();
        while (cursor.hasNext()){
            Document temp = cursor.next();
            if (temp.getInteger("date2Return") < date)
            all.add(temp);
        }
        return all;
    }

    public static void clearDue(MongoCollection collection, int date){
        MongoCursor<Document> cursor = collection.find().iterator();
        ArrayList<Document> all = new ArrayList<>();
        while (cursor.hasNext()){
            Document temp = cursor.next();
            if (temp.getInteger("date2Return") < date){
                collection.deleteOne(eq("_id", temp.get("_id")));
            }

        }
        return;
    }

    public static void main(String[] args) {
        port(1234);

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("RRS");
        MongoCollection<Document> stringCollection = mongoDatabase.getCollection("strings");
        MongoCollection<Document> customerCollection = mongoDatabase.getCollection("customers");


        Gson gson = new Gson();
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        int today = Integer.parseInt(dateFormat.format(currentDate.getTime()));

        post("/api/addcustomer", (request, response) -> {
            System.out.println(request.body());
            PreAddCust body = gson.fromJson(request.body(), PreAddCust.class);
            Customer newC = addCustomer(stringCollection, currentDate, body);
            Document newCustomer = new Document();
            newCustomer.append("name", newC.name);
            newCustomer.append("contact", newC.contact);
            newCustomer.append("email", newC.email);
            newCustomer.append("mainString", newC.mainString);
            newCustomer.append("crossString", newC.crossString);
            newCustomer.append("mainTension", newC.mainTension);
            newCustomer.append("crossTension", newC.crossTension);
            newCustomer.append("date2Return", newC.date2Return);
            newCustomer.append("dueDate", newC.dueDate);
            customerCollection.insertOne(newCustomer);
            return gson.toJson(newC);
        });

        post("/api/addstring", (request, response) -> {
            System.out.println(request.body());
            PreAddStr body = gson.fromJson(request.body(), PreAddStr.class);
            if (stringCollection.find(eq("name", body.name)).first() == null) {
                StringType newS = addString(body);

                return gson.toJson(newS);
            }
            return "String existed";
        });



        path("/api", () -> {
            get("/allcustomers", (request, response) -> {
                ArrayList<Document> customers = getAll(customerCollection);
                return gson.toJson(customers);
            });
            get("/allstrings", (request, response) -> {
                ArrayList<Document> strings = getAll(stringCollection);
                return gson.toJson(strings);
            });
            get("/returning", (request, response) -> {
                ArrayList<Customer> due = getDue(customerCollection, today);
                return gson.toJson(due);
            });
            get("/clear", (request, response) -> {
                clearDue(customerCollection, today);
                return "done";
            });
        });
    }
}
