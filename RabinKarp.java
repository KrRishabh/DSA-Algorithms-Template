import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rabin-Karp (rolling hash) implementation in Java.
 *
 * - rabinKarpSingleHash: single-modulus rolling hash + verification on match.
 * - rabinKarpDoubleHash: safer variant using two independent hashes (reduces collisions).
 *
 * Returns 0-based indices of matches.
 */
public class RabinKarp {

    // Two large prime moduli for double-hash variant
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;

    // Bases. For general ASCII text, 256 is common. For letters, smaller bases like 31/53 are also used.
    private static final int BASE1 = 256;
    private static final int BASE2 = 257;

    // Fast modular exponentiation: computes (base^exp) % mod
    private static long modPow(long base, int exp, long mod) {
        long result = 1;
        long cur = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * cur) % mod;
            cur = (cur * cur) % mod;
            exp = exp >> 1;
        }
        return result;
    }

        /**
     * Single-modulus Rabin-Karp search.
     * - Computes rolling hash with base BASE1 and modulus MOD1.
     * - On hash match, verifies with String.regionMatches (no extra substring allocation).
     *
     * Returns list of 0-based start indices where `pattern` occurs in `text`.
     */
    public static List<Integer> rabinKarpSingleHash(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("text and pattern must be non-null");
        }
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || m > n) return Collections.emptyList(); // define: empty pattern -> no matches

        long base = BASE1;
        long mod = MOD1;

        // precompute base^(m-1) % mod used to remove leftmost char contribution
        long power = modPow(base, m - 1, mod);

        // compute initial hash for pattern and the first window of text
        long patHash = 0;
        long txtHash = 0;
        for (int i = 0; i < m; i++) {
            patHash = (patHash * base + pattern.charAt(i)) % mod;
            txtHash = (txtHash * base + text.charAt(i)) % mod;
        }

        List<Integer> occurrences = new ArrayList<>();

        // Slide over text: window end index runs from m-1 to n-1
        for (int i = m; ; i++) {
            // If hashes match, verify to avoid false positive due to collision
            if (patHash == txtHash) {
                int start = i - m;
                // regionMatches is efficient and avoids creating substrings
                if (text.regionMatches(start, pattern, 0, m)) {
                    occurrences.add(start);
                }
            }
            if (i == n) break; // finished scanning

            // Remove leftmost char contribution: text.charAt(i-m)
            long leftChar = text.charAt(i - m);
            txtHash = (txtHash - (leftChar * power) % mod + mod) % mod; // ensure non-negative
            // Multiply by base and add new char text.charAt(i)
            txtHash = (txtHash * base + text.charAt(i)) % mod;
        }

        return occurrences;
    }

/**
     * Double-hash Rabin-Karp.
     * - Maintains two independent rolling hashes (BASE1/MOD1 and BASE2/MOD2).
     * - Treats a window as a candidate only if both hashes match; then verifies.
     *
     * This makes collisions extremely unlikely.
     */
    public static List<Integer> rabinKarpDoubleHash(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("text and pattern must be non-null");
        }
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || m > n) return Collections.emptyList();

        long base1 = BASE1, base2 = BASE2;
        long mod1 = MOD1, mod2 = MOD2;

        long power1 = modPow(base1, m - 1, mod1);
        long power2 = modPow(base2, m - 1, mod2);

        long patHash1 = 0, patHash2 = 0;
        long txtHash1 = 0, txtHash2 = 0;
        for (int i = 0; i < m; i++) {
            patHash1 = (patHash1 * base1 + pattern.charAt(i)) % mod1;
            txtHash1 = (txtHash1 * base1 + text.charAt(i)) % mod1;

            patHash2 = (patHash2 * base2 + pattern.charAt(i)) % mod2;
            txtHash2 = (txtHash2 * base2 + text.charAt(i)) % mod2;
        }

        List<Integer> occurrences = new ArrayList<>();

        for (int i = m; ; i++) {
            // Candidate only if both hashes match
            if (patHash1 == txtHash1 && patHash2 == txtHash2) {
                int start = i - m;
                if (text.regionMatches(start, pattern, 0, m)) {
                    occurrences.add(start);
                }
            }
            if (i == n) break;

            // Roll first hash
            long left1 = text.charAt(i - m);
            txtHash1 = (txtHash1 - (left1 * power1) % mod1 + mod1) % mod1;
            txtHash1 = (txtHash1 * base1 + text.charAt(i)) % mod1;

            // Roll second hash
            long left2 = text.charAt(i - m);
            txtHash2 = (txtHash2 - (left2 * power2) % mod2 + mod2) % mod2;
            txtHash2 = (txtHash2 * base2 + text.charAt(i)) % mod2;
        }

        return occurrences;
    }

    // Demo main
    public static void main(String[] args) {
        String text = "ABABABA";
        String pattern = "ABA";

        System.out.println("Single-hash result: " + rabinKarpSingleHash(text, pattern));
        System.out.println("Double-hash result: " + rabinKarpDoubleHash(text, pattern));

        // Example: rotation check using single-hash (pattern must be substring of text+text)
        String s1 = "rotationexample";
        String s2 = "tionexamplerota";
        boolean isRotation = !rabinKarpSingleHash(s1 + s1, s2).isEmpty();
        System.out.println("Is rotation? " + isRotation);
    }


}