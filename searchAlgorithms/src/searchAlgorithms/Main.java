package searchAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
	private static DislikeTable dislikeTable = new DislikeTable();

	public static void main(String[] args) {

		// variables to track execution times
		long startTime = 0;
		long endTime = 0;
		long duration = 0;

		/////////////////
		// * <Greedy> *//
		/////////////////
		startTime = System.nanoTime();// start counting
		GreedySearch(0);// invoke greedy search method with Ahmad as the start
		endTime = System.nanoTime();// stop counting
		duration = (endTime - startTime) / 1000000; // convert to milliseconds
		System.out.println("Execution time: " + duration + " milliseconds\n\n\n");// print result

		//////////////
		// * <UCS> *//
		//////////////
		startTime = System.nanoTime();// start counting
		UCSAlgorithm(0);// invoke greedy search method with Ahmad as the start
		endTime = System.nanoTime();// stop counting
		duration = (endTime - startTime) / 1000000; // convert to milliseconds
		System.out.println("Execution time: " + duration + " milliseconds\n\n\n");// print result

		/////////////
		// * <A*> *//
		/////////////
		startTime = System.nanoTime();// start counting
		AStarAlgorithm(0);// invoke greedy search method with Ahmad as the start
		endTime = System.nanoTime();// stop counting
		duration = (endTime - startTime) / 1000000; // convert to milliseconds
		System.out.println("Execution time: " + duration + " milliseconds\n\n\n");// print result

	}

	public static void GreedySearch(int startState) {

		List<Integer> table = new ArrayList<>();// keep track seating arrangement
		table.add(startState);// seat the first person

		double totalCost = 0;// keep track of total cost of seating arrangement

		// loop until the number of people seated is equal to the total number of people
		while (table.size() < dislikeTable.getNumPeople()) {

			double leastCost = Double.MAX_VALUE;// setting to max to use in finding the min cost
			int nextPerson = 0;// to store the id of the next person to be sited
			int lastPerson = table.get(table.size() - 1);// the last seated person

			for (int i = 0; i < dislikeTable.getNumPeople(); i++) {// loop all people

				// checking if the current person i has a the lowest cost with the last person
				// seated, this person must not be already seated
				if (dislikeTable.getDislike(lastPerson, i) < leastCost && !table.contains(i)) {

					leastCost = dislikeTable.getDislike(lastPerson, i);// save the cost needed to seat this person
					nextPerson = i;// save person
				}
			}

			table.add(nextPerson);// add person to seating
			totalCost += leastCost;// add the cost to total

		}

		// since the table is circular, we must add to the total cost the dislike
		// between the last and first person seated at the table
		int lastSeated = table.get(table.size() - 1);// last person in array
		totalCost += dislikeTable.getDislike(startState, lastSeated);// adding cost

		printPeople(table, totalCost, "**Greedy Algorithm**");// print

	}

	public static void UCSAlgorithm(int startState) {

		List<Integer> table = new ArrayList<>();// table to seat people
		table.add(startState);// add first person to seating arrangement

		// minimum heap holding different seating arrangements and costs
		PriorityQueue<NodeState> minHeap = new PriorityQueue<>(new NodeStateComparator());

		// adding the initial table state (one person sitting alone with cost equal to
		// 0) to the min heap
		minHeap.add(new NodeState(table, 0));

		// enter loop until priority queue is empty or execution is stopped
		while (!minHeap.isEmpty()) {

			// get the minimum seating arrangement in the heap
			NodeState currentState = minHeap.poll();

			// when everyone is seated (done), print solution
			if (currentState.getSeating().size() == dislikeTable.getNumPeople()) {

				double cost = calculateCost(currentState.getSeating(), 1);// calculate total cost
				printPeople(currentState.getSeating(), cost, "**UCS Algorithm**");// print

				return;
			}

			// loop in the people not sitting yet
			for (int i = 0; i < dislikeTable.getNumPeople(); i++) {

				// making sure table doesn't already have the person i sitting
				// equivalent to checking if already explored
				if (!currentState.getSeating().contains(i)) {

					// adding person i to the end of the current table arrangement
					// this forms a new seating arrangement
					List<Integer> newArrangement = new ArrayList<>(currentState.getSeating());
					newArrangement.add(i);

					// getting the last seated person before adding i
					int last = currentState.getSeating().get(currentState.getSeating().size() - 1);

					// calculating accumulative cost of dislike between seated people
					double newCost = currentState.getCost() + Math.pow(dislikeTable.getDislike(last, i), 2);

					// adding the new arrangement to the heap
					minHeap.add(new NodeState(newArrangement, newCost));
				}
			}
		}

		System.out.println("Problem?");

	}

	public static void AStarAlgorithm(int startState) {

		List<Integer> table = new ArrayList<>();// table to seat people
		table.add(startState);// add first person to seating arrangement

		// heap to hold the state nodes containing the seat arrangements and costs
		PriorityQueue<NodeState> minHeap = new PriorityQueue<>(new NodeStateComparator());

		// creating first node state with h-cost and g-cost as 0 (initial state)
		NodeState first = new NodeState(table, 0, 0);

		// adding initial state to heap
		minHeap.add(first);

		// until priority queue is empty or terminated by another line of code
		while (!minHeap.isEmpty()) {

			// get node with the minimum cost seating arrangement inside heap
			NodeState currentState = minHeap.poll();

			// everyone is seated, print solution
			if (currentState.getSeating().size() == dislikeTable.getNumPeople()) {

				// calculate the final seating arrangement cost
				double cost = calculateCost(currentState.getSeating(), 2);
				printPeople(currentState.getSeating(), cost, "**A* Algorithm**");// print

				return;
			}

			// loop through everyone not yet seated
			for (int i = 0; i < dislikeTable.getNumPeople(); i++) {
				if (!currentState.getSeating().contains(i)) {// not seated

					// adding i to the new seating arrangement
					List<Integer> newArrangement = new ArrayList<>(currentState.getSeating());
					newArrangement.add(i);

					// last person seated before i
					int last = currentState.getSeating().get(currentState.getSeating().size() - 1);

					// getting the real dislike cost between i and the previously last person added
					// adding this cost to the previous accumulated one associated with this seating
					double gCost = currentState.getgCost() + Math.pow(dislikeTable.getDislike(last, i), 2);

					// the heuristic cost is what gets us closer to a better goal (least conflict)
					// by adding the heuristic dislike between the last two people
					double hCost = dislikeTable.getDislike(last, i);

					// pushing this state into the min heap
					minHeap.add(new NodeState(newArrangement, gCost, hCost));
				}
			}
		}

		System.out.println("Problem?");

	}

	public static double calculateCost(List<Integer> seatingArrangement, int mode) {

		double totalCost = 0;// end cost to return
		int numPeople = seatingArrangement.size();

		// looping through all of the seated people
		// saving them as pairs to calculated the dislike between them
		for (int i = 0; i < numPeople; i++) {
			int personA = seatingArrangement.get(i);// first person

			/*
			 * setting index as: ((i + 1) % numPeople) wraps around list to include the last
			 * person making sure the index never exceeds the list size. In other words, it
			 * connects back to the beginning of the list.
			 */
			int personB = seatingArrangement.get((i + 1) % numPeople);// second person

			// obtaining the dislike cost between the two selected people
			double costAB = dislikeTable.getDislike(personA, personB);

			if (mode == 0)// heuristic cost for greedy
				totalCost += costAB;// h(x) = x

			else if (mode == 1)// real cost for UCS
				totalCost += Math.pow(costAB, 2);// g(x) = x^2

			else// real + heuristic costs for A*
				totalCost += costAB + Math.pow(costAB, 2);// f(n) = h(x) + g(x)
		}

		return totalCost;
	}

	public static void printPeople(List<Integer> node, double cost, String searchType) {

		System.out.print(searchType + "\nSeating Arrangement:\n[");

		// printing peoples names
		for (Integer i : node)
			System.out.print(dislikeTable.getNameById(i) + ",");

		System.out.print("]\nSearch Cost: " + cost + "\nConflict Cost: " + calculateCost(node,1) + "\n");

	}


}
