package reminder;

public class StringType {
	private String name;
	private String material;
	private double tensionLoss;

	public StringType(String name, String material, double tensionLoss){
		this.name = name;
		this.material = material;
		this.tensionLoss = tensionLoss;
	}

	public String getName(){
		return this.name;
	}

	public String getMaterial(){
		return this.material;
	}

	public double getTensionLoss(){
		return this.tensionLoss;
	}

	public void setTensionLoss(double tensionLoss){
		this.tensionLoss = tensionLoss;
	}
}
