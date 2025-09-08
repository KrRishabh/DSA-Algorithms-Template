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
}