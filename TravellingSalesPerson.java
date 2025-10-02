public class TravellingSalesPerson{

// A large value to represent "infinity" (no path yet).
    // Choose something bigger than any possible path sum.
    static final int INF = 1_000_000_000;

    /**
     * Held-Karp DP for TSP.
     * dist: n x n matrix of non-negative edge costs. Use INF (or large number)
     *       to represent missing edges if the graph isn't complete.
     *
     * Returns the minimal cost to start at node 0, visit every node exactly once,
     * and return to node 0. Returns -1 if no Hamiltonian cycle exists.
     */
    public static int heldKarp(int[][] dist) {
        int n = dist.length;               // number of nodes
        int FULL = 1 << n;                 // number of possible masks (2^n)

        // dp[mask][last] = minimum cost to start at 0, visit nodes in 'mask', and end at 'last'
        // Note: we require that bit 'last' is set in 'mask'
        int[][] dp = new int[FULL][n];

        // Fill dp with INF initially (unreachable)
        for (int i = 0; i < FULL; ++i) {
            Arrays.fill(dp[i], INF);
        }

        // Base case: mask with only node 0 visited, and we are at node 0 => cost 0
        dp[1 << 0][0] = 0;  // (mask = 1, binary 000..001)

        // Iterate over all masks (0 .. 2^n - 1)
        // Each mask represents a set of visited nodes.
        for (int mask = 0; mask < FULL; ++mask) {
            // For each possible node 'last' that might be the endpoint for this mask
            for (int last = 0; last < n; ++last) {
                // If 'last' is not in mask, skip — dp[mask][last] is invalid.
                if ((mask & (1 << last)) == 0) continue;

                int curCost = dp[mask][last];
                // If we haven't reached this state (still INF), skip it.
                if (curCost >= INF) continue;

                // Try to extend the tour: go to a node 'nxt' not yet visited in 'mask'
                for (int nxt = 0; nxt < n; ++nxt) {
                    // Skip if 'nxt' already visited in mask
                    if ((mask & (1 << nxt)) != 0) continue;

                    // new mask after visiting nxt
                    int nextMask = mask | (1 << nxt);

                    // candidate cost to reach state (nextMask, nxt)
                    int cand = curCost + dist[last][nxt];

                    // relax: keep minimum cost
                    if (cand < dp[nextMask][nxt]) {
                        dp[nextMask][nxt] = cand;
                    }
                }
            }
        }

        // After filling dp, compute answer by closing the tour to node 0.
        int fullMask = FULL - 1;  // mask with all n bits set (all nodes visited)
        int answer = INF;

        // Try finishing at any 'last' and return to 0 (if return edge exists)
        for (int last = 0; last < n; ++last) {
            // Only consider reachable states
            if (dp[fullMask][last] < INF && dist[last][0] < INF) {
                answer = Math.min(answer, dp[fullMask][last] + dist[last][0]);
            }
        }

        // if answer is still INF, there's no Hamiltonian cycle
        return answer == INF ? -1 : answer;
    }

    // small test harness
    public static void main(String[] args) {
        // Example cost matrix (symmetric here, but it doesn't have to be)
        int[][] dist = {
            {INF, 10, 15},
            {10, INF, 20},
            {15, 20, INF}
        };
        int ans = heldKarp(dist);
        System.out.println("TSP min cycle cost = " + ans); // expected 45 for this example
    }
}