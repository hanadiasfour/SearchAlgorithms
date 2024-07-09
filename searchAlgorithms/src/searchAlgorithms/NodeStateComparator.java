package searchAlgorithms;

import java.util.Comparator;

//Define a Comparator class
class NodeStateComparator implements Comparator<NodeState> {
	@Override
	public int compare(NodeState node1, NodeState node2) {
		return Double.compare(node1.getCost(), node2.getCost());
	}
}