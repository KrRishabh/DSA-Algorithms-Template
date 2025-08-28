
class DynamicProgramming{

    public static void main(String args[]){
        
        // Sample data for 0/1 Knapsack problem
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        System.out.println("Knapsack Problem:");
        System.out.println("Weights: " + java.util.Arrays.toString(weights));
        System.out.println("Values: " + java.util.Arrays.toString(values));
        System.out.println("Capacity: " + capacity);
        
        int maxValue = knapsack(capacity, weights, values);
        System.out.println("Maximum value that can be obtained: " + maxValue);
    }

    public static int knapsack(int W, int[] wt, int[] val){
        // 0/1 Knapsack (pick each item at most once)

        // Given n items with wt[i], val[i], and capacity W, maximize value.
        // State: dp[i][w] = best value using first i items within capacity w.
        // Transition:

        // Skip item i-1: dp[i-1][w]

        // Take item i-1 (if fits): val[i-1] + dp[i-1][w - wt[i-1]]
        // dp[i][w] = max(skip, take).

        // 2D Tabulation (clear & easy to debug)

        int n = wt.length;
        int[][] dp = new int[n+1][W+1];

        for(int i=1; i<=n; i++){

            for(int w=0; w<=W; w++){

                dp[i][w] = dp[i-1][w];
                if( wt[i-1] <= w){
                    dp[i][w] = Math.max( dp[i][w], dp[i-1][w-wt[i-1]] + val[i-1] );
                }
            }
        }

        //backtrack to recover

        List<Integer> take = new ArrayList<>();
        int w = W;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) { // item i-1 taken
                take.add(i - 1);
                w -= wt[i - 1];
            }
        }
        Collections.reverse(take);        

        return dp[n][W];


    }
}


