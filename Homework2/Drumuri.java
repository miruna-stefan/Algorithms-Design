import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

// create a class Pair that will be used in the adjacency list
/* the first member of the pair will store the destination node
and the second member will store the cost of the edge */
class Pair implements Comparable<Pair> {
	int destinationNode;
	long cost;

	public Pair(int destinationNode, long cost) {
		this.destinationNode = destinationNode;
		this.cost = cost;
	}

	@Override
	public int compareTo(Pair other) {
		return Long.compare(this.cost, other.cost);
	}
}

class Graph {
	// define infinity (distance greater than any possible distance in graph)
	public static final long INF = Long.MAX_VALUE;

	// define maximum number of nodes
	public static final int NMAX = 100005;

	int N; // number of nodes
	int M; // number of edges
	ArrayList<Pair>[] adjList; // adjacency list

	public Graph(int n, int m) {
		N = n;
		M = m;
		adjList = new ArrayList[NMAX];
		for (int i = 0; i <= N; i++) {
			adjList[i] = new ArrayList<>();
		}
	}

	public void addEdge(int source, int destination, long cost) {
		adjList[source].add(new Pair(destination, cost));
	}

	/* function that applies the dijkstra algorithm on the graph and returns
	an array of minimal distances from the source node to all other nodes */
	public long[] dijkstra(int source) {
		// create the vector storing the minimal distances from the source node to all other nodes
		long[] dist = new long[NMAX];
		// initialize the distances to infinity
		for (int i = 0; i <= N; i++) {
			dist[i] = INF;
		}
		// the distance from source to source is 0
		dist[source] = 0;

		// set up the priority queue in which we will store pairs of (neighbour node, cost)
		PriorityQueue<Pair> pq = new PriorityQueue<>();

		// add source pair to the priority queue
		pq.add(new Pair(source, 0));

		while (!pq.isEmpty()) {
			// get the pair with the smallest cost
			Pair head = pq.poll();
			int node = head.destinationNode;
			long nodeCost = head.cost;

			/* we need to store the minimal distances from the source node to all other nodes,
			so if the current cost is grater than the cost stored in the dist array,
			we should skip the current node */
			if (nodeCost > dist[node]) {
				continue;
			}

			// parse all the neighbours of the current node
			for (Pair neighbor : adjList[node]) {
				/* check if we could replace the distance stored for the destination node with
				the distance from the source to the current node + the cost of the edge between
				the current node to the destination node */
				if (dist[node] + neighbor.cost < dist[neighbor.destinationNode]) {
					dist[neighbor.destinationNode] = dist[node] + neighbor.cost;
					pq.add(new Pair(neighbor.destinationNode, dist[neighbor.destinationNode]));
				}
			}
		}

		return dist;
	}
}

class Solution {
	int x, y, z;
	Graph graph;
	int N, M;

	public Solution(int x, int y, int z, Graph graph) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.graph = graph;
		this.N = graph.N;
		this.M = graph.M;
	}

	/* function that contains the main flow logic of the program and returns
	the minimal sum of the costs of the edges respecting the conditions */
	long getResult() {
		/* create inverted graph, by parsing all nodes and adding
		to the inverted graph the reversed edges */
		Graph invertedGraph = new Graph(N, M);
		for (int i = 1; i <= N; i++) {
			for (Pair neighbor : graph.adjList[i]) {
				invertedGraph.addEdge(neighbor.destinationNode, i, neighbor.cost);
			}
		}

		/* apply dijkstra algorithm on the original graph to find the
		shortest path from x to all other nodes */
		long[] distX = graph.dijkstra(x);

		/* apply dijkstra algorithm on the original graph to find the
		shortest path from y to all other nodes */
		long[] distY = graph.dijkstra(y);

		/* apply dijkstra algorithm on the inverted graph to find the
		shortest path from z to all other nodes */
		long[] distZ = invertedGraph.dijkstra(z);

		/* parse all nodes of the graph, identify the intermediate nodes between
		the path from x to z and the path from y to z and calculate the minimum cost */
		long minCost = Graph.INF;
		for (int i = 1; i <= N; i++) {
			/* we can identify an intermediate node by checking if the distance is different
			than the default infinite distance in all arrays of distances returned by dijkstra */
			if (distX[i] != Graph.INF && distY[i] != Graph.INF && distZ[i] != Graph.INF) {
				long currentCost = distX[i] + distY[i] + distZ[i];
				minCost = Math.min(minCost, currentCost);
			}
		}

		return minCost;
	}

}

public class Drumuri {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("drumuri.in"));

		// read first line and split it by space
		String[] firstLine = bufferedReader.readLine().split(" ");

		// the number of nodes will be the first number on the line
		int N = Integer.parseInt(firstLine[0]);

		// the number of edges will be the second number on the line
		int M = Integer.parseInt(firstLine[1]);

		// create a graph with N nodes and M edges
		Graph graph = new Graph(N, M);

		// from the following M lines, read the edges of the graph
		for (int i = 0; i < M; i++) {
			// read the edge from the file
			String[] edge = bufferedReader.readLine().split(" ");

			// the source node will be the first number on the line
			int source = Integer.parseInt(edge[0]);

			// the destination node will be the second number on the line
			int destination = Integer.parseInt(edge[1]);

			// the cost of the edge will be the third number on the line
			int cost = Integer.parseInt(edge[2]);

			// add the edge to the graph
			graph.addEdge(source, destination, cost);
		}

		// read nodes x, y, z from the last line of the file
		String[] lastLine = bufferedReader.readLine().split(" ");
		int x = Integer.parseInt(lastLine[0]);
		int y = Integer.parseInt(lastLine[1]);
		int z = Integer.parseInt(lastLine[2]);

		bufferedReader.close();

		Solution solution = new Solution(x, y, z, graph);
		long result = solution.getResult();

		// write the result to the file
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("drumuri.out"));
		bufferedWriter.write(String.valueOf(result));

		bufferedWriter.close();
	}
}
