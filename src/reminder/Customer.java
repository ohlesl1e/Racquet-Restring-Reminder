package reminder;

public class Customer {
    protected String name;
    protected String mainString;
    protected String crossString;
    protected double mainTension;
    protected double crossTension;
    protected int date2Return;
    protected String dueDate;
    protected String contact;
    String email;

    public Customer(String name, String mainString, String crossString, double mainTension, double crossTension, int date2Return, String dueDate, String contact, String email) {
        this.name = name;
        this.mainString = mainString;
        this.crossString = crossString;
        this.mainTension = mainTension;
        this.crossTension = crossTension;
        this.date2Return = date2Return;
        this.dueDate = dueDate;
        this.contact = contact;
        this.email = email;
    }

    public int getDate2Return() {
        return this.date2Return;
    }

    public void printContact() {
        System.out.println(this.name + ", " + this.contact + ", " + this.mainString + "/" + this.crossString + " @ " + this.mainTension + "/" + this.crossTension);
    }
}
