package searchAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class NodeState {

	private List<Integer> seating;
	private double cost, gCost, hCost;

	public NodeState(List<Integer> seatingArrangement,double gcost,double hcost) {
		this.seating = new ArrayList<>(seatingArrangement);
		this.gCost = gcost;
		this.hCost = hcost;
		this.cost = gcost + hcost;

	}
	
	public NodeState() {
		
		
	}

	public NodeState(List<Integer> seatingArrangement, double cost) {
		this.seating = new ArrayList<>(seatingArrangement);
		this.cost = cost;
	}

	public List<Integer> getSeating() {
		return seating;
	}

	public void setSeating(List<Integer> seating) {
		this.seating = seating;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getgCost() {
		return gCost;
	}

	public void setgCost(double gCost) {
		this.gCost = gCost;
	}

	public double gethCost() {
		return hCost;
	}

	public void sethCost(double hCost) {
		this.hCost = hCost;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub

		List<Integer> l1 = ((NodeState) obj).getSeating();

		if (l1.size() != this.seating.size())
			return false;

		for (int i = 0; i < l1.size(); i++) {
			if (l1.get(i) != this.seating.get(i))
				return false;
		}

		return true;
	}

}
