import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Solution2 {
	int k;
	int[] zones;
	char[] orientations;
	private static final int MOD = 1000000007;

	public Solution2(int k, int[] zones, char[] orientations) {
		this.k = k;
		this.zones = zones;
		this.orientations = orientations;
	}

	public long performPower(long base, long exponent) {
		if (exponent == 0) {
			return 1;
		}

		/* if the exponent is even, divide it by 2 and only compute the power
		of the base to the power of the half of the exponent */
		if (exponent % 2 == 0) {
			long halfPower = performPower(base, exponent / 2);
			return (halfPower * halfPower) % MOD;
		}

		/* if we have reached this point, it means that the exponent is odd, so
		we can do the same as in the even case, but also multiply the result
		by the base one more time */
		long halfPower = performPower(base, exponent / 2);
		long halfPowerSquared = (halfPower * halfPower) % MOD;
		return (halfPowerSquared * base) % MOD;
	}

	public long getResult() {
		/* construct an array of k integers that will be populated
		with the number of possible combination for each zone */
		long[] dp = new long[k];

		if (orientations[0] == 'H') {
			// we have 6 possibilities to paint the 2 rectangles (3 for each)
			dp[0] = 6;
		} else {
			// we need to place only one rectangle in vertical position
			dp[0] = 3;
		}
		dp[0] = dp[0] % MOD;

		if (zones[0] > 1) {
			if (orientations[0] == 'H') {
				// adjacency H-H
				dp[0] = dp[0] * performPower(3, zones[0] - 1);
			} else {
				// adjacency V-V
				dp[0] = dp[0] * performPower(2, zones[0] - 1);
			}
		}
		dp[0] = dp[0] % MOD;

		for (int i = 1; i < k; i++) {
			if (orientations[i] == 'H') {
				if (orientations[i - 1] == 'H') {
					// adjacency H-H
					dp[i] = dp[i - 1] * performPower(3, zones[i]);
					dp[i] = dp[i] % MOD;
				} else {
					// adjacency V-H
					/* only the first adjacency will be V-H. After placing the first
					horizontal rectangle pair next to a vertical rectangle, the rest of rectangles
					will be in H-H adjacency */
					dp[i] = dp[i - 1] * 2;
					dp[i] = dp[i] % MOD;

					if (zones[i] > 1) {
						dp[i] = dp[i] * performPower(3, zones[i] - 1);
						dp[i] = dp[i] % MOD;
					}
				}
			} else {
				if (orientations[i - 1] == 'H') {
					// adjacency H-V
					dp[i] = dp[i - 1];
					dp[i] = dp[i] % MOD;
					if (zones[i] > 1) {
						/* only the first adjacency will be H-V. After placing the first vertical
						rectangle near an horizontal pair, the rest of rectangles will
						be in V-V type of adjacency */
						dp[i] = dp[i] * performPower(2, zones[i] - 1);
						dp[i] = dp[i] % MOD;
					}
				} else {
					// adjacency V-V
					dp[i] = dp[i - 1] * performPower(2, zones[i]);
					dp[i] = dp[i] % MOD;
				}
			}
			System.out.println(dp[i]);
		}

		return dp[k - 1];
	}
}

public class Colorare {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("colorare.in"));
		// read the number of groups from the input
		int k = Integer.parseInt(bufferedReader.readLine());

		/* the second line will be split into 2 arrays, whose indexes will be interconnected:
		* the first one (zones) will store the numbers of rectangles that will be added in
		* the corresponding position the second one (orientations) will store the
		* orientations of the rectangles (H - horizontal; V - vertical) */
		int[] zones = new int[k];
		char[] orientations = new char[k];

		String[] secondLine = bufferedReader.readLine().split(" ");
		int j = 0;
		for (int i = 0; i < 2 * k; i = i + 2) {
			zones[j] = Integer.parseInt(secondLine[i]);
			orientations[j] = secondLine[i + 1].charAt(0);
			j++;
		}

		bufferedReader.close();

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("colorare.out"));

		Solution2 solution2 = new Solution2(k, zones, orientations);
		long result = solution2.getResult();
		bufferedWriter.write(String.valueOf(result));

		bufferedWriter.close();
	}
}
