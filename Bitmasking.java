import java.util.ArrayList;

public class Bitmasking{

    public static ArrayList<ArrayList<String>> generateAllSubsets(){

        String[] items = {"A","B","C"};
        int n = items.length;
        ArrayList<ArrayList<String>> res = new ArrayList<>();

        for(int mask=0; mask < (1<<n); mask++){

            ArrayList<String> curr = new ArrayList<String>();

            for(int i=0; i<n; i++){

                if((mask & (1<<i)) != 0) curr.add(items[i]); 
            }
            res.add(curr);
        }

        System.out.println(res);
        return res;
    }

    public static ArrayList<ArrayList<String>> generateAllSubmasks(){
        //         If you want “all subsets of a specific mask”

        //         A submask of a mask is any bit pattern that:

        // has 1s only in positions where mask already has 1, and

        // may have some of those 1s turned off.

        // In other words, submask ⊆ mask (as subsets).
        // Example:
        // mask = 0b10110 ({1,2,4})
        // Its submasks are every subset of {1,2,4}:

        int mask = 0b1011; // means items {0,1,3}

        for(int submask=mask; submask!=0; submask = mask & (submask-1)){
            
            List<Integer> subset = new List<Integer>();
            for(int i=0; i < 4; i++){
                if((mask & (1<<i)) != 0) subset.add(i);
            }

            System.out.println(subset);
        }

    }
}