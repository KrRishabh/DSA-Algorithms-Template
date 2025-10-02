/**
 * Manacher's algorithm - longest palindromic substring in O(n).
 *
 * Author: template code
 *
 * Key functions:
 *  - preprocess(s): transform original string into T with separators and sentinels.
 *  - longestPalindromicSubstring(s): compute P array and return the longest palindrome.
 *
 * Notes:
 *  - Transformed T layout: ^ # s[0] # s[1] # ... # s[n-1] # $
 *  - P[i] stores the radius (number of characters around i that match on both sides).
 */
public class Manacher {

    /**
     * Returns the longest palindromic substring of s.
     * If s is empty or null, returns empty string.
     */
    public static String longestPalindromicSubstring(String s) {
        if (s == null || s.length() == 0) return "";

        // Build transformed string T as a char array for speed and simple indexing.
        char[] T = preprocess(s);
        int n = T.length;
        int[] P = new int[n]; // palindrome radii array

        int C = 0; // center of the current rightmost palindrome
        int R = 0; // right boundary (exclusive index of palindrome: center + radius)

        // iterate through T (skip first sentinel '^' at index 0 and last '$' at index n-1)
        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * C - i; // mirror index of i around center C

            // If i is within current right boundary R, we can initialize P[i] at least to the mirror's value
            if (i < R) {
                // R - i is the remaining length to R; mirror's P value might be larger,
                // so we take the minimum (we cannot expand beyond R without checks).
                P[i] = Math.min(R - i, P[mirror]);
            } else {
                P[i] = 0; // outside the boundary, start with zero radius
            }

            // Try to expand palindrome centered at i
            // Compare characters one step further on both sides while they match
            while (T[i + P[i] + 1] == T[i - P[i] - 1]) {
                P[i]++;
            }

            // If palindrome centered at i expands past R, update center C and boundary R
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }
        }

        // Find the maximum element in P (radius) and its center index
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }

        // Map back to original string indices:
        // start = (centerIndex - maxLen) / 2
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    /**
     * Preprocess the original string s into the transformed array T.
     * Layout: T[0] = '^', then alternating '#' and characters, ending with '#', T[last] = '$'.
     *
     * Length = 2 * s.length() + 3.
     *
     * Example:
     *  s = "abba"
     *  T = ^ # a # b # b # a # $
     */
    private static char[] preprocess(String s) {
        int n = s.length();
        char[] T = new char[2 * n + 3];
        T[0] = '^';             // starting sentinel (prevents bounds checks)
        int idx = 1;
        for (int i = 0; i < n; i++) {
            T[idx++] = '#';     // separator
            T[idx++] = s.charAt(i); // original character
        }
        T[idx++] = '#';         // final separator
        T[idx] = '$';           // ending sentinel
        return T;
    }

    // Example usage / quick demo
    public static void main(String[] args) {
        String[] tests = {
            "babad",      // "bab" or "aba"
            "cbbd",       // "bb"
            "a",          // "a"
            "abba",       // "abba"
            "",           // ""
            "abacdfgdcaba"// "aba" (one of the palindromes)
        };

        for (String t : tests) {
            System.out.printf("s = \"%s\" -> longest palindrome = \"%s\"%n",
                t, longestPalindromicSubstring(t));
        }
    }
}
