package searchAlgorithms;

public class DislikeTable {

	private double[][] dislikeMatrix = { { 0, 0.68, 0.55, 0.30, 0.82, 0.48, 0.33, 0.10, 0.76, 0.43 },
			{ 0.68, 0, 0.90, 0.11, 0.76, 0.20, 0.55, 0.17, 0.62, 0.99 },
			{ 0.55, 0.90, 0, 0.70, 0.63, 0.96, 0.51, 0.90, 0.88, 0.64 },
			{ 0.30, 0.11, 0.70, 0, 0.91, 0.86, 0.78, 0.99, 0.53, 0.92 },
			{ 0.82, 0.76, 0.63, 0.91, 0, 0.43, 0.88, 0.53, 0.42, 0.75 },
			{ 0.48, 0.20, 0.96, 0.86, 0.43, 0, 0.63, 0.97, 0.37, 0.26 },
			{ 0.33, 0.55, 0.51, 0.78, 0.88, 0.63, 0, 0.92, 0.87, 0.81 },
			{ 0.10, 0.17, 0.90, 0.99, 0.53, 0.97, 0.92, 0, 0.81, 0.78 },
			{ 0.76, 0.62, 0.88, 0.53, 0.42, 0.37, 0.87, 0.81, 0, 0.45 },
			{ 0.43, 0.99, 0.64, 0.92, 0.75, 0.26, 0.81, 0.78, 0.45, 0 } };
	private String[] names = { "Ahmad", "Salem", "Ayman", "Hani", "Kamal", "Samir", "Hakam", "Fuad", "Ibrahim",
			"Khalid" };

	// retrieve the dislike value between two people
	public double getDislike(int p1, int p2) {
		return dislikeMatrix[p1][p2];
	}

	public String getNameById(int id) {
		return names[id];

	}

	// number of people being seated
	public int getNumPeople() {
		return dislikeMatrix.length;
	}



}
