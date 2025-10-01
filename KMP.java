import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * KMP (Knuth-Morris-Pratt) string search implementation.
 * - computeLPSArray builds the prefix-function (LPS) in O(m)
 * - kmpSearch finds all start indices where pattern occurs in text in O(n + m)
 *
 * Returns 0-based indices of matches.
 */
public class KMP {

    /**
     * Compute LPS (Longest Proper Prefix which is also Suffix) array for pattern.
     * lps[i] = length of the longest proper prefix of pat[0..i] which is also a suffix of pat[0..i].
     *
     * Time: O(m), Space: O(m)
     */
    public static int[] computeLPSArray(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        if (m == 0) return lps; // empty pattern -> empty lps

        // length of the previous longest prefix suffix
        int len = 0;
        lps[0] = 0; // lps[0] is always 0
        int i = 1;

        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                // characters match: extend current border
                len++;
                lps[i] = len;
                i++;
            } else { // mismatch
                if (len != 0) {
                    // try shorter border (do not increment i here)
                    len = lps[len - 1];
                } else {
                    // no border possible -> lps[i] = 0
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * KMP search: find all occurrences of pattern 'pat' in text 'txt'.
     *
     * Returns a list of starting indices (0-based) where 'pat' occurs in 'txt'.
     * If pattern is empty or longer than text, returns empty list.
     *
     * Time: O(n + m), Space: O(m)
     */
    public static List<Integer> kmpSearch(String txt, String pat) {
        if (pat == null || txt == null) {
            throw new IllegalArgumentException("Text and pattern must be non-null");
        }
        int n = txt.length();
        int m = pat.length();

        // Edge cases
        if (m == 0) return Collections.emptyList(); // define: empty pattern => no matches returned
        if (m > n) return Collections.emptyList();

        int[] lps = computeLPSArray(pat);
        List<Integer> occurrences = new ArrayList<>();

        int i = 0; // index for txt
        int j = 0; // index for pat

        while (i < n) {
            if (txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    // match found; append starting index
                    occurrences.add(i - j);
                    // continue searching for next (possibly overlapping) matches
                    j = lps[j - 1];
                }
            } else { // mismatch
                if (j != 0) {
                    // slide the pattern using lps (don't advance i)
                    j = lps[j - 1];
                } else {
                    // no partial match, advance in text
                    i++;
                }
            }
        }

        return occurrences;
    }

    // Small demo
    public static void main(String[] args) {
        String text = "ABABABA";
        String pattern = "ABA";

        List<Integer> occ = kmpSearch(text, pattern);
        System.out.println("Text:    " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("Occurrences (0-based indices): " + occ); // expected [0, 2, 4]

        // Example: check rotation using KMP
        String s1 = "rotationexample";
        String s2 = "tionexamplerota"; // s2 is rotation of s1
        boolean isRotation = isRotationUsingKMP(s1, s2);
        System.out.println("Is rotation? " + isRotation);
    }

    /**
     * Example helper: check if s2 is rotation of s1 using KMP (s2 must be substring of s1+s1)
     */
    public static boolean isRotationUsingKMP(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        String doubled = s1 + s1;
        return !kmpSearch(doubled, s2).isEmpty();
    }
}
