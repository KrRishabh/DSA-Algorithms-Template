import java.util.*;

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

    int[][] lcs;


    // recursion method, top-down appraoch
    public int longestCommonSubsequence(String text1, String text2){

        int m = text1.length();
        int n = text2.length();

        lcs = new int[m][n];

        for(int row[]: lcs) Arrays.fill(row, -1);

        return solve(text1, text2, m-1, n-1);
    }

    public int solve(String s1, String s2, int i, int j){

        if(Math.min(i,j) < 0) return 0;

        if(lcs[i][j]!=-1)  return lcs[i][j];

        if(s1.charAt(i) == s2.charAt(j)){
            lcs[i][j] = solve(s1, s2, i-1, j-1) + 1;
            return lcs[i][j];
        }        

        lcs[i][j] = Math.max( solve(s1,s2, i-1, j) , solve(s1, s2, i, j-1) );
        return lcs[i][j];
    }
    // bottom-up tabulation method
    public int longestCommonSubsequenceTabulation(String text1, String text2){

        int n = text1.length();
        int m = text2.length();

        int[][] dp = new int[n+1][m+1];

        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){

                if(text1.charAt(i-1) == text2.charAt(j-1))  dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max( dp[i-1][j] ,  dp[i][j-1]);

            }
        }

        return dp[n][m];
    }


    public long countWays(int[] nums, int target) {
        // code for counting number of ways any subset can make K from an array
         
         if (target < 0) return 0; 

         long[] ways = new long[target + 1]; 
         ways[0] = 1L; 

         for (int x : nums) 
         { 
               if (x == 0) {
                // This also works naturally via the loop below, 
                // // but explicitly noting doesn't hurt understanding. 
            } 
            for (int s = target; s >= x; s--) { 
                ways[s] += ways[s - x]; 
            } 
         } 
         return ways[target]; 
    }

    public boolean canMakeSum(int[] nums, int target) {
        // code for checking if it's possible to make a sum K from subsets in an array
        
        if (target < 0) return false;
        
        boolean[] possible = new boolean[target + 1];
        possible[0] = true; // empty subset makes sum 0
        
        for (int x : nums) 
        {
            if (x == 0) {
                // Zero doesn't change any possibilities, handled naturally by loop below
            }
            for (int s = target; s >= x; s--) {
                possible[s] = possible[s] || possible[s - x];
            }
        }
        return possible[target];
    }

    public int matrixChainMultiplication(int[] p){
        // You are given dimensions of matrices A1, A2, …, An. Matrix Ai has dimension p[i-1] x p[i]. 
        // Find the minimum cost of multiplying all matrices (the order of multiplication can change, but sequence cannot).

        // INTERVAL DP
        int len = p.length;
        int n = len-1;
        int[][] dp = new int[n][n];

        for(int len=2; len<=n; len++){
            for(int i=0; i+len-1<n; i++){
                int j = i+len-1;
                dp[i][j] = Integer.MAX_VALUE/2;
                
                for(int k=i; k<j; k++){
                    int cost = dp[i][k] + dp[k+1][j] + p[i]*p[k+1]*p[j+1]
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n-1];


    }
}


