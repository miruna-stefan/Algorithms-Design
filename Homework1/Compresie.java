import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Solution3 {
	int n;
	ArrayList<Integer> a;
	int m;
	ArrayList<Integer> b;

	public Solution3(int n, ArrayList<Integer> a, int m, ArrayList<Integer> b) {
		this.n = n;
		this.a = a;
		this.m = m;
		this.b = b;
	}

	public int getResult() {
		/* we do not need to display the whole result array, just its number of elements, so
		it is not necessary to actually build the result array, just to increment the elements
		counter everytime we would have added an element in the result array */
		int countResultElements = 0; // this variable counts the elements of the result array

		int i = 0; // index for a
		int j = 0; // index for b
		int partialSumA = 0;
		int partialSumB = 0;
		while (i < n && j < m) {
			if (a.get(i) + partialSumA == b.get(j) + partialSumB) {
				// "add" the element to the result array, so increment the counter
				countResultElements++;
				// reset the partial sums
				partialSumA = 0;
				partialSumB = 0;
				// advance in both arrays
				i++;
				j++;
				continue;
			}

			if (a.get(i) + partialSumA < b.get(j) + partialSumB) {
				partialSumA += a.get(i);
				// advance only in the first array
				i++;
			} else {
				partialSumB += b.get(j);
				// advance only in the second array
				j++;
			}
		}

		// if we have elements left in a or b, we cannot compress them
		if (i < n) {
			return -1;
		}
		if (j < m) {
			return -1;
		}
		if (countResultElements == 0) {
			return -1;
		}

		return countResultElements;
	}
}

public class Compresie {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("compresie.in"));
		int n = Integer.parseInt(bufferedReader.readLine());

		// read next line as a string and split it into numbers
		String[] firstArrayLine = bufferedReader.readLine().split(" ");

		// store the numbers from the first line in an arrayList of integers
		ArrayList<Integer> a = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			a.add(Integer.parseInt(firstArrayLine[i]));
		}

		// do the exact same thing for the second array
		int m = Integer.parseInt(bufferedReader.readLine());
		String[] secondArrayLine = bufferedReader.readLine().split(" ");

		ArrayList<Integer> b = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			b.add(Integer.parseInt(secondArrayLine[i]));
		}
		bufferedReader.close();


		// instantiate the solution class and call the getResult() method
		Solution3 solution3 = new Solution3(n, a, m, b);
		int result = solution3.getResult();


		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("compresie.out"));
		bufferedWriter.write(result + "\n");

		bufferedWriter.close();
	}
}
