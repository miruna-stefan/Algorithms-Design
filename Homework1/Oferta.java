import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Solution5 {
	int n;
	int k;
	int[] prices;

	public Solution5(int n, int k, int[] prices) {
		this.n = n;
		this.k = k;
		this.prices = prices;
	}

	public double getResult() {
		// if we have only one product, the minimum price is the price of the product itself
		if (n == 1) {
			return (double)prices[1];
		}

		double[] dp = new double[n + 1];
		// we need to store in dp the minim possible prices of the products up until that index
		// index starts from 1

		/* when adding a new product, we have three possibilities to calculate the smallest price:
		* 1. buy it as single product
		* 2. pair it with another product and buy it in a 2-product offer
		* 3. pair it with another 2 products and buy it in a 3-product offer */

		/* the first product cannot be paired with anyone in front of it
		=> the minimum price is the first product's price in itself, as there
		is no possibility to include it in any offer */
		dp[1] = prices[1];

		/* for the second product, we have to choose the minimum price between adding the product on
		its own and pairing the second product with the first one and making a 1+1 offer. Obviously,
		the minimum will always be the second option, so we can write it directly */
		dp[2] = (double)(Math.max(prices[1], prices[2]))
				+ (double)(Math.min(prices[1], prices[2])) / 2.0;

		for (int i = 3; i <= n; i++) {
			/* calculate the price in each of the 3 cases and keep the minimum result */

			// initialize price with the price of the product itself (case 1)
			double only1Prod = prices[i];
			only1Prod += dp[i - 1];

			// case 2: pair the product with the previous one
			double offer2Prod = (double)(Math.max(prices[i], prices[i - 1]))
					+ (double)(Math.min(prices[i], prices[i - 1])) / 2.0;
			offer2Prod += dp[i - 2];

			// case3: pair the product with the previous 2 products
			double offer3Prod = (double)(prices[i] + prices[i - 1] + prices[i - 2]);
			double mini = (double)Math.min(prices[i], prices[i - 1]);
			mini = Math.min(mini, (double)prices[i - 2]);
			offer3Prod = offer3Prod - mini;

			if (i != 3) {
				offer3Prod += dp[i - 3];
			}

			// keep the minimum price
			dp[i] = Math.min(Math.min(only1Prod, offer2Prod), offer3Prod);
		}
		return dp[n];
	}
}

public class Oferta {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("oferta.in"));
		int n;
		int k;
		/* split the first line in order to store the variables n and k separately*/
		String[] firstLine = bufferedReader.readLine().split(" ");
		n = Integer.parseInt(firstLine[0]);
		k = Integer.parseInt(firstLine[1]);

		/* store in an array the prices of the products from the second line */
		String[] secondLine = bufferedReader.readLine().split(" ");
		int[] prices = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			prices[i] = Integer.parseInt(secondLine[i - 1]);
		}

		bufferedReader.close();

		/* instantiate the solution class and call the method that generates the result*/
		Solution5 solution5 = new Solution5(n, k, prices);
		double result = solution5.getResult();
		String formattedNumber = String.format("%.1f", result);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("oferta.out"));
		bufferedWriter.write(formattedNumber + "\n");
		bufferedWriter.close();
	}
}
