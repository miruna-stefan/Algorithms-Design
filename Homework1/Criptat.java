import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

class Solution4 {
	int n;
	ArrayList<String> words;
	HashSet<Character> letterSet;
	ArrayList<HashMap<Character, Integer>> frequencies;
	int totalLetters;

	public Solution4(int n, ArrayList<String> words) {
		this.n = n;
		this.words = words;
	}

	/* function that, while parsing the array of words, populates the hashset of
	all letters in the input and populates the array of frequency hashmaps */
	private void populateFrequencies() {
		// create the set of letters
		letterSet = new HashSet<>();
		frequencies = new ArrayList<>();

		totalLetters = 0; // total number of letters in all words
		for (int i = 0; i < n; i++) {
			// create a hashmap for each word that stores the frequency of each letter in that word
			HashMap<Character, Integer> lettersFreq = new HashMap<>();
			for (int j = 0; j < words.get(i).length(); j++) {
				letterSet.add(words.get(i).charAt(j));
				if (lettersFreq.containsKey(words.get(i).charAt(j))) {
					lettersFreq.put(words.get(i).charAt(j),
							lettersFreq.get(words.get(i).charAt(j)) + 1);
				} else {
					lettersFreq.put(words.get(i).charAt(j), 1);
				}
			}
			totalLetters += words.get(i).length();

			// add the hashmap to the list of hashmaps
			frequencies.add(lettersFreq);
		}
	}

	public int getResult() {
		populateFrequencies();
		/* for each letter, create a dp matrix: each line will correspond to a
		 word and each column to a possible total length of the password

		 It is an analogy with the 0/1 knapsack problem, but here the profit[i] will represent
		 the frequency of the current letter in the word on position i and the weight[i]
		 will represent the length of the current word.

		 The dp matrix will be filled in a bottom-up manner. */

		int maxFreq = 0;
		int passwdLength = 0;
		for (Character letter : letterSet) {
			// the maximum number of occurrences of the current letter in the password
			int maxFreqForCurrLetter = 0;

			/* the maximum length of the password that could be formed with the
			current letter as dominant letter */
			int passwdLengthForCurrLetter = 0;

			// create a dp matrix for each letter in the hashset
			int[][] dp = new int[n][totalLetters + 1];

			/* for each word, parse all of its letters and populate the dp matrix */
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < totalLetters + 1; j++) {
					// check if this is the first row of the matrix or not
					if (i != 0) {
						// this is NOT the first row of the matrix
						/* if the length of the whole password is lower than the length of
						the current word in itself or if the current word does not contain
						the current letter (for which we are
						building the matrix), just copy the value on the row before */
						if (j < words.get(i).length() || !frequencies.get(i).containsKey(letter)) {
							dp[i][j] = dp[i - 1][j];
						} else {
							/* take the maximum between the value on the row before
							(the frequency of the letter without including the current word) and the
							sum between the frequency of the letter in the current word and the
							frequency that we could still add in the remaining length of the
							password (without this current word*/
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - words.get(i).length()]
									+ frequencies.get(i).get(letter));

							// check if we need to update the maximum frequency of the letter
							if (j > 0 && dp[i][j] != dp[i][j - 1] && 2 * dp[i][j] > j) {
								if (dp[i][j] > maxFreqForCurrLetter) {
									maxFreqForCurrLetter = dp[i][j];
									passwdLengthForCurrLetter = j;
								}
							}
						}
					} else {
						// populate the first row of the matrix
						if (j >= words.get(i).length()) {
							if (!frequencies.get(i).containsKey(letter)) {
								dp[i][j] = 0;
							} else {
								dp[i][j] = frequencies.get(i).get(letter);
								// check if we need to update the maximum frequency of the letter
								if (j == words.get(i).length() && 2 * dp[i][j] > j) {
									maxFreqForCurrLetter = dp[i][j];
									passwdLengthForCurrLetter = j;
								}
							}
						} else {
							dp[i][j] = 0;
						}
					}
				}
			}

			/* check if we can add some dummy words (words that were not taken into consideration
			when building the dp matrix because they do NOT contain the dominant
			letter to the password */
			for (int i = 0; i < n; i++) {
				if (frequencies.get(i).containsKey(letter)) {
					continue;
				}
				if (passwdLengthForCurrLetter + words.get(i).length() < 2 * maxFreqForCurrLetter) {
					passwdLengthForCurrLetter += words.get(i).length();
				}
			}

			// update maximum frequency
			if (maxFreqForCurrLetter > maxFreq) {
				maxFreq = maxFreqForCurrLetter;
				passwdLength = passwdLengthForCurrLetter;
			}
		}
		return passwdLength;
	}
}

public class Criptat {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("criptat.in"));
		// build an array of strings with n words read from the input file
		int n = Integer.parseInt(bufferedReader.readLine());
		ArrayList<String> words = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			String[] line = bufferedReader.readLine().split(" ");
			words.add(line[0]);
		}

		// sort the words by length in descending order
		words.sort(Comparator.comparing(String::length).reversed());

		bufferedReader.close();

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("criptat.out"));

		// instantiate solution class, call the getResult() method and display result
		Solution4 solution4 = new Solution4(n, words);
		int result = solution4.getResult();
		bufferedWriter.write(result + "\n");

		bufferedWriter.close();
	}
}
