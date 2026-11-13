package part1;

public final class DivideAndConquer {
    private DivideAndConquer() {}

    /** Input: non-null array of distinct integers, including empty arrays. */
    public static InversionResult sortAndCount(int[] values) {
        //copy the array so the original is not changed
        int[] copy = values.clone();
        
        //sort the copy and count inversions
        long count = mergeSortAndCount(copy, 0, copy.length - 1);

       //return the count and sorted array``
       return new InversionResult(count, copy);
    }
    private static long mergeSortAndCount(int[] arr, int left, int right) {
        if(left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;

        long leftCount = mergeSortAndCount(arr, left, mid);

        long rightCount = mergeSortAndCount(arr, mid + 1, right);

        long crossCount = mergeAndCount(arr, left, mid, right);

        return leftCount + rightCount + crossCount;
    }

    private static long mergeAndCount(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        long count = 0;

        while(i <= mid && j <= right) {
            if(arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];

                count += mid - i + 1;
            }
        }

        while(i <= mid) {
            temp[k++] = arr[i++];
        }

        while(j <= right) {
            temp[k++] = arr[j++];
        }

        for(int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }

        return count;
    }
}
