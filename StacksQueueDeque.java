// Given an array nums and window size k, find the maximum in every window of size k as it slides from left to right.

// Example:
// nums = [1,3,-1,-3,5,3,6,7], k = 3
// Output: [3,3,5,5,6,7]

// Naive way: For each window, scan all k elements → O(n·k).
// We want: O(n).


// 🔹 Trick: Monotonic Deque

// We maintain a deque of indices, such that:

// It always stores elements in decreasing order of their values (nums[dq[0]] ≥ nums[dq[1]] ≥ ...).

// The front of deque (dq[0]) always holds the index of the current maximum in the window.


class StacksQueueDeque{

    public int[] slidingWindowMax(int[] nums, k){

        int len = nums.length;
        Deque<Integer> dq = new ArrayDeque<Integer>();
        int[] ans = new int[len-k+1]

        for(int i=0; i<nums.length; i++){

            int curr = nums[i];

            while(!dq.isEmpty() && dq.peekFirst() < i-k+1 ) dq.removeFirst();

            while(!dq.isEmpty() && nums[dq.peekLast] < curr ) dq.removeLast();

            dq.addLast(i);

            if(i-k+1>=0) ans[i-k+1] = nums[dq.peekFirst()];

        }
    }
}