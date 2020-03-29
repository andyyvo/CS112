// Removing Duplicates Items in an Array
// For the sake of the problem, we will be considering int[]
// Andy Vo

import java.util.Arrays;

public class Problem5 {

    /**
     * removeDups - takes a sorted array of integers and removes
     * whatever elements are necessary to ensure that no item
     * appears more than once.
     * 
     * Method uses O(1) additional memory - no secondary copy array.
     * 
     * Remaining elements will still be sorted, and occupy the leftmost
     * positions of the array. The array locations that are unused after
     * the duplicates are removed are filled with zeroes.
     * 
     * The method returns an integer specifying the number of unique
     * values in the array.
     */
    public static int removeDups(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Invalid int[] array inserted.");
        }
        if (arr.length == 1) {
            return 1;
        }
        int currentIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[currentIndex]) {
                arr[i] = 0;
            } else {
                currentIndex++;
                arr[currentIndex] = arr[i];
                if (i != currentIndex) {
                    arr[i] = 0;
                }
            }
        }
        currentIndex++;
        return currentIndex;
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 5, 5, 10, 12, 12};
        int ret = removeDups(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(ret);
    }
}