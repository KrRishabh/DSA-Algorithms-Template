// Link for all the study plan topics - https://chatgpt.com/s/t_68ac36f433208191914065495d5a421b


public class BinarySearch{

    public static int findElement(int[] array, int target){
        int left = 0;
        int right = array.length - 1;
        
        while(left <= right){
            System.out.println("left = "+left);
            int mid = left + (right-left)/2;
            if(array[mid]<target){
                left = mid+1;
            }
            else if(array[mid]>target){
                right = mid-1;
            }
            else return mid;
        }
        return -1;
    }

    public static int lowerBound(int[] array, int target){
        int left = 0;
        int right = array.length;
        int mid = right;
        while(left < right){
            mid = left + (right-left)/2;
            if(array[mid]>=target){
                right = mid;
            }else{
                left = mid + 1;
            }
        }
        return mid;

    }

    public static int upperBound(int[] array, int target){
        int left = 0;
        int right = array.length;
        int mid=-1;

        while(left<right){
            mid = left + (right-left)/2;

            if(array[mid]<=target){
                left = mid+1;
            }else{
                right = mid; 
            }
        }
        return mid;
    }

    public static void main (String[] args){
        // Array with 20 distinct numbers
        int[] distinctArray = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39};
        
        // Array with 30 elements having many repetitive numbers
        int[] repetitiveArray = {5, 5, 5, 5, 5, 10, 10, 10, 10, 15, 15, 15, 15, 15, 15, 20, 20, 20, 25, 25, 25, 25, 30, 30, 35, 35, 40, 45, 50, 55};
        
        System.out.println("Distinct array length: " + distinctArray.length);
        System.out.println("Repetitive array length: " + repetitiveArray.length);

        System.out.println("target 7 found at -> "+findElement(distinctArray, 3));
        System.out.println("target 6 for lower bound ->"+lowerBound(repetitiveArray, 10));
        System.out.println("target 6 for upper bound ->"+upperBound(repetitiveArray, 10));
    }

    
}