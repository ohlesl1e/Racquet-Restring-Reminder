package reminder;

public class Customer {
	private String name;
	private String mainString;
	private String crossString;
	private double mainTension;
	private double crossTension;
	private int date2Return;
	private String contact;

	public Customer(String name, String mainString, String crossString, double mainTension, double crossTension, int date2Return, String contact) {
		this.name = name;
		this.mainString = mainString;
		this.crossString = crossString;
		this.mainTension = mainTension;
		this.crossTension = crossTension;
		this.date2Return = date2Return;
		this.contact = contact;
	}

	public int getDate2Return() {
		return this.date2Return;
	}

	public void printContact() {
		System.out.println(this.name + ", " + this.contact + ", " + this.mainString + "/" + this.crossString + " @ " + this.mainTension + "/" + this.crossTension);
	}
}
