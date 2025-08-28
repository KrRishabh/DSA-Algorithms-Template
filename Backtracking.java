public class Backtracking{

    public static void main (String args[]){
        
        int nums[] = new nums[]{1,2,3,4,5,6};

        System.out.println(generateSubsets(nums));
    }

    public ArrayList<ArrayList<Integer>> generateSubsets(int[] nums){

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        ArrayList<Integer> temp = new ArrayList<>();

        backtrack(res, temp, nums, i);
    }

    public void backtrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> curr, int[] nums, int i){

        if(i == nums.length){
            res.add(curr);
            return;
        }

        curr.add(nums[i]);
        backtrack(res, curr, nums, i+1);
        curr.remove(curr.size()-1);
        backtrack(res, curr, nums, i+1);
    }
}