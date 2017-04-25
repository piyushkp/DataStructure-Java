package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Search {
    public static void main(String [] args) {
        System.out.print("Search");
    }
    //BinarySearch
    int binarySearch(int arr[], int x){
        int l = 0, r = arr.length - 1;
        while (l <= r){
            int m = l + (r-l)/2;
            // Check if x is present at mid
            if (arr[m] == x)
                return m;
            // If x greater, ignore left half
            if (arr[m] < x)
                l = m + 1;
                // If x is smaller, ignore right half
            else
                r = m - 1;
        }
        // if we reach here, then element was not present
        return -1;
    }
   static int binarySearch(int arr[], int l, int r, int x){
        if (r>=l){
            int mid = l + (r - l)/2;
            // If the element is present at the middle itself
            if (arr[mid] == x)
                return mid;
            // If element is smaller than mid, then it can only
            // be present in left subarray
            if (arr[mid] > x)
                return binarySearch(arr, l, mid-1, x);
            // Else the element can only be present in right
            // subarray
            return binarySearch(arr, mid+1, r, x);
        }
        // We reach here when element is not present in array
        return -1;
    }
}
