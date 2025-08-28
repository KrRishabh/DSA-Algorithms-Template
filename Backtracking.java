import java.util.*;

public class Backtracking{

    public static void main (String args[]){
        
        int nums[] = new int[]{1,2,3,4,5,6};

        System.out.println(generateSubsets(nums));
        System.out.println(generateSubsets(nums).size());

        System.out.println(generatePermutations(nums));
        System.out.println(generatePermutations(nums).size());
        
    }

    public static ArrayList<ArrayList<Integer>> generateSubsets(int[] nums){

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        ArrayList<Integer> temp = new ArrayList<>();

        // backtrack(res, temp, nums, 0);
        alternateBacktrack(res, temp, nums, 0);
        return res;
    }

    public static void backtrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> curr, int[] nums, int i){

        if(i == nums.length){
            res.add(new ArrayList<>(curr));
            return;
        }
        curr.add(nums[i]);
        backtrack(res, curr, nums, i+1);
        curr.remove(curr.size()-1);
        backtrack(res, curr, nums, i+1);
    }

    public static void alternateBacktrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> curr, int[] nums, int start){
    
        res.add(new ArrayList<>(curr));
        
        for(int i=start; i<nums.length; i++){
            curr.add(nums[i]);
            alternateBacktrack(res, curr, nums, i+1);
            curr.remove(curr.size()-1);
        }
    
    }

    public static ArrayList<ArrayList<Integer>> generatePermutations(int[] nums){

        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> curr = new ArrayList<Integer>();
        boolean[] used = new boolean[nums.length];
        permute(res, curr, nums, used);

        return res;
    }

    public static void permute(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> curr, int[] nums, boolean[] used){

        int size = curr.size();
        int len = nums.length;
        if(size == len){
            res.add(new ArrayList<>(curr));
            return;
        }
       

        for(int i=0; i<len; i++){
            if(used[i]) continue;

            curr.add(nums[i]);
            used[i] = true;
            permute(res, curr, nums, used);
            used[i] = false;
            curr.remove(curr.size()-1);
        }

    }
}