import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Solution1 {
	int n;
	ArrayList<Integer> p;
	ArrayList<Integer> c;
	ArrayList<Integer> differences;
	ArrayList<Integer> sums;
	public static final double PRECISION = 1e-4;
	public static final double MIN_VALUE_FOR_POWER = -10e9;
	public static final double MAX_VALUE_FOR_POWER = 10e9;

	public Solution1(int n, ArrayList<Integer> p, ArrayList<Integer> c) {
		this.n = n;
		this.p = p;
		this.c = c;
	}

	private double getMinPower() {
		double minPower = MAX_VALUE_FOR_POWER;
		differences = new ArrayList<>(); // ci - pi
		sums = new ArrayList<>(); // ci + pi

		/* while parsing the n powers and alimentation limits, update the
		differences and sums arrays and find the minimal power */
		for (int i = 0; i < n; i++) {
			differences.add(c.get(i) - p.get(i));
			sums.add(c.get(i) + p.get(i));
			if (p.get(i) < minPower) {
				minPower = (double)p.get(i);
			}
		}

		return minPower;
	}

	/* function that checks if there is any Cx that satisfies the inequality
	Ci - Pi + rez <= Cx <= Ci + Pi - rez
	(i have explained in readme where this inequality comes from) */
	private boolean isInequalityValid(double leftSideEqualityMax, double rightSideEqualityMin,
										double mid) {
		/* parse the array of differences and of sums in order to find the
		maximum of the left side and the minimum of the right side */
		int i = 0; // the index in the array
		while (i < n) {
			/* leftSideEqualityMax should store the maximum value of the left side
			of the inequality
			Ci - Pi + rez <= Cx <= Ci + Pi - rez */
			if (leftSideEqualityMax < mid + differences.get(i)) {
				leftSideEqualityMax = mid + differences.get(i);
			}
			/* rightSideEqualityMin should store the minimal value of the right side
			of the inequality
			Ci - Pi + rez <= Cx <= Ci + Pi - rez */
			if (rightSideEqualityMin > sums.get(i) - mid) {
				rightSideEqualityMin = sums.get(i) - mid;
			}
			i++;
		}

		/* if the maximum value of the left side exceeds the minimal value
		of the right side, it means that there is no Cx that could satisfy
		the inequality */
		if (leftSideEqualityMax > rightSideEqualityMin) {
			return false;
		}

		/* in order for Cx to exist, the maximum of the right side should
		be smaller than the minimum of the left side*/
		return true;
	}

	public double getResult() {
		/* we need the minimal power in order to establish the range in which
		we search for the result-power */
		double minPower = getMinPower();

		double left = MIN_VALUE_FOR_POWER;
		double right = minPower;
		double mid = (left + right) / 2.0;
		double leftSideEqualityMax, rightSideEqualityMin;

		/* start a binary search for the result-power in the range [-10e9; minPower],
		where minPower is the minimal power in the powers array */
		while (left < right && mid - left > PRECISION) {

			leftSideEqualityMax = Double.MIN_VALUE;
			rightSideEqualityMin = Double.MAX_VALUE;

			/* if the inequality is invalid, look for a smaller resulting power (referred
			as "rez" in readme) in the left interval, so change the right end of
			the interval */
			if (!isInequalityValid(leftSideEqualityMax, rightSideEqualityMin, mid)) {
				right = mid;
			} else {
				/* if the inequality is valid, try to find a bigger power that satisfies the
				inequality, so look in the right interval by moving the left end on the
				position of the current middle */
				left = mid;
			}

			mid = (left + right) / 2.0;
		}

		return left;
	}


}

public class Servere {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("servere.in"));
		// read the number of servers
		int n = Integer.parseInt(bufferedReader.readLine());

		// build the array of powers
		String[] firstArrayLine = bufferedReader.readLine().split(" ");
		ArrayList<Integer> p = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			p.add(Integer.parseInt(firstArrayLine[i]));
		}

		// build the array of alimentation limits
		String[] secondArrayLine = bufferedReader.readLine().split(" ");
		ArrayList<Integer> c = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			c.add(Integer.parseInt(secondArrayLine[i]));
		}

		bufferedReader.close();


		/* instantiate solution class, call the getResult() method and display
		result with only one decimal */
		Solution1 solution1 = new Solution1(n, p, c);
		double result = solution1.getResult();
		String formattedNumber = String.format("%.1f", result);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("servere.out"));

		bufferedWriter.write(formattedNumber + "\n");

		bufferedWriter.close();
	}
}
