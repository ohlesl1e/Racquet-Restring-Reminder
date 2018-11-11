package reminder;

public class CustomerNode {
	public Customer customer = null;
	public CustomerNode left = null;
	public CustomerNode right = null;

	public CustomerNode(Customer newCustomer){
		this.customer = newCustomer;
	}
}
