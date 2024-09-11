import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// class for a graph that stores integers in its nodes
class IntGraph {
	int N; // the number of nodes
	ArrayList<ArrayList<Integer>> adjList; // adjacency list

	public static int MOD = 1000000007;

	public IntGraph(int N) {
		this.N = N;
		adjList = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			adjList.add(new ArrayList<>());
		}
	}

	public void addEdge(int source, int destination) {
		adjList.get(source).add(destination);
	}

	// method that returns a new graph, representing the intersection of
	// the current graph with another graph
	public IntGraph intersectWithAnotherGraph(IntGraph otherGraph) {
		IntGraph newGraph = new IntGraph(N);

		// parse all the nodes in the current graph
		for (int i = 1; i <= N; i++) {
			// parse all the neighbours of the current node
			for (int j = 0; j < adjList.get(i).size(); j++) {
				// check if the current edge also exists in the other graph
				if (otherGraph.adjList.get(i).contains(adjList.get(i).get(j))) {
					newGraph.addEdge(i, adjList.get(i).get(j));
				}
			}
		}
		return newGraph;
	}

	// method that returns the total number of paths from node 1 to node N
	int getNumberOfPathsToN() {
		// store in dp the number of paths from 1 to the current index
		// so dp[i] = number of paths from 1 to i
		int[] dp = new int[N + 1];

		// set all elements of dp to 0
		for (int i = 0; i <= N; i++) {
			dp[i] = 0;
		}

		dp[1] = 1;

		// iterate through all nodes and update the number of paths to each node
		for (int i = 1; i <= N; i++) {
			// iterate through all neighbours of the current node
			for (int j = 0; j < adjList.get(i).size(); j++) {
				// update the number of paths to the neighbour
				// for each neighbour, add to its value the number of paths to the current node
				dp[adjList.get(i).get(j)] += dp[i];
				dp[adjList.get(i).get(j)] %= MOD;
			}
		}
		return dp[N];
	}

}

public class Numarare {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("numarare.in"));

		// read N (number of nodes) and M (number of edges) from the first line
		String firstLine = bufferedReader.readLine();
		int N = Integer.parseInt(firstLine.split(" ")[0]);
		int M = Integer.parseInt(firstLine.split(" ")[1]);

		// read the next M lines and populate first graph
		IntGraph graph1 = new IntGraph(N);
		for (int i = 0; i < M; i++) {
			String line = bufferedReader.readLine();
			int source = Integer.parseInt(line.split(" ")[0]);
			int destination = Integer.parseInt(line.split(" ")[1]);
			graph1.addEdge(source, destination);
		}

		// read the next M lines and populate second graph
		IntGraph graph2 = new IntGraph(N);
		for (int i = 0; i < M; i++) {
			String line = bufferedReader.readLine();
			int source = Integer.parseInt(line.split(" ")[0]);
			int destination = Integer.parseInt(line.split(" ")[1]);
			graph2.addEdge(source, destination);
		}

		bufferedReader.close();

		// create a new graph, which will represent the intersection of the 2 graphs
		IntGraph intersectedGraph = graph1.intersectWithAnotherGraph(graph2);
		int result = intersectedGraph.getNumberOfPathsToN();

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("numarare.out"));
		bufferedWriter.write(String.valueOf(result));

		bufferedWriter.close();
	}
}
