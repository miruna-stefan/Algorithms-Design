import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class StringGraph {
	int M; // the number of paths (edges
	HashMap<String, ArrayList<String>> adjList; // the adjacency list

	public StringGraph(int m) {
		this.M = m;
		adjList = new HashMap<>();
	}

	public void addEdge(String source, String destination) {
		// if the source city is not an entry in the hashmap yet, create a new entry for it
		if (!adjList.containsKey(source)) {
			adjList.put(source, new ArrayList<>());
		}
		// add destination in the adjacency list of the source node
		adjList.get(source).add(destination);
	}

	// helper function for topological sort that will be called recursively
	void topoSortHelper(String sourceCity, HashMap<String, Boolean> visited, Stack<String> stack) {
		// mark the current city as visited
		visited.put(sourceCity, true);

		// iterate through all neighbours of the current city
		for (String neighCity : adjList.get(sourceCity)) {
			// if the neighbour has not been visited, call the topoSortHelper function recursively
			if (!visited.get(neighCity)) {
				topoSortHelper(neighCity, visited, stack);
			}
		}

		// push the current city to the stack
		stack.push(sourceCity);
	}

	/* function that should be called after the topological sort so as to create a hashmap
	in order to store the maximal distances from the source to each node and return the
	maximum distance from source to destination */
	int updateDistances(Stack<String> stack, String sourceCity, String destinationCity) {
		// create a hashmap that stores the maximal distance from the source city to each city
		HashMap<String, Integer> distance = new HashMap<>();

		// initialize the distance of the source city to the minimum possible value
		for (String city : adjList.keySet()) {
			distance.put(city, -1);
		}

		// the distance from the source city to itself is 0
		distance.put(sourceCity, 0);

		// iterate through all the cities in the stack
		while (!stack.isEmpty()) {
			// get the current city
			String currentCity = stack.pop();

			// iterate through all the neighbours of the current city
			for (String neighCity : adjList.get(currentCity)) {
				if (distance.get(neighCity) < distance.get(currentCity) + 1) {
					// update the distance
					distance.put(neighCity, distance.get(currentCity) + 1);
				}
			}
		}

		return distance.get(destinationCity) + 1;
	}

	int initTopoSortFromSource(String sourceCity, String destinationCity) {
		// create a hashmap that stores the state of the cities (visited or unvisited)
		HashMap<String, Boolean> visited = new HashMap<>();
		for (String city : adjList.keySet()) {
			visited.put(city, false);
		}

		// create a stack to store the result of the topological sort of the cities
		Stack<String> stack = new Stack<>();

		// start topological sort from the source city
		topoSortHelper(sourceCity, visited, stack);

		return updateDistances(stack, sourceCity, destinationCity);
	}
}

public class Trenuri {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("trenuri.in"));

		// read first line from the file
		String firstLine = bufferedReader.readLine();

		// split the first line by space
		// the source city will be the first word from the line
		String sourceCity = firstLine.split(" ")[0];

		// the destination city will be the second word from the first line
		String destinationCity = firstLine.split(" ")[1];

		// read the number of paths, which is the second line from the file
		int numberOfPaths = Integer.parseInt(bufferedReader.readLine());

		// create a graph with the number of paths (the number of paths
		// is equivalent to the number of edges in the graph)
		StringGraph graph = new StringGraph(numberOfPaths);

		for (int i = 0; i < numberOfPaths; i++) {
			// read path from the file
			String path = bufferedReader.readLine();

			// split the path by space
			// the first word will be the source city
			String source = path.split(" ")[0];

			// the second word will be the destination city
			String destination = path.split(" ")[1];

			// add the path to the graph
			graph.addEdge(source, destination);

			// also create an entry in the hashmap for the destination city
			// the adjacency list of the destination city will remain empty
			if (!graph.adjList.containsKey(destination)) {
				graph.adjList.put(destination, new ArrayList<>());
			}
		}

		// close the bufferedReader
		bufferedReader.close();

		int result = graph.initTopoSortFromSource(sourceCity, destinationCity);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("trenuri.out"));
		bufferedWriter.write(String.valueOf(result));

		bufferedWriter.close();
	}
}
