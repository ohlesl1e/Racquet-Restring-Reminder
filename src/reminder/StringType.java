package reminder;

import java.util.Comparator;

public class StringType {
	private String name;
	private String material;
	private double tensionLoss;

	public StringType(String name, String material, double tensionLoss) {
		this.name = name;
		this.material = material;
		this.tensionLoss = tensionLoss;
	}

	public String getName() {
		return this.name;
	}

	public String getMaterial() {
		return this.material;
	}

	public double getTensionLoss() {
		return this.tensionLoss;
	}

	public void setTensionLoss(double tensionLoss) {
		this.tensionLoss = tensionLoss;
	}

	public static Comparator<StringType> nameComparator = new Comparator<StringType>() {

		public int compare(StringType s1, StringType s2) {
			String stringName1 = s1.getName();
			String stringName2 = s2.getName();

			return stringName1.compareToIgnoreCase(stringName2);
		}
	};
}
