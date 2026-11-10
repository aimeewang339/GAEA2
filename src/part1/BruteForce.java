package part1;
import java.util.*;
import common.Unfinished;

public final class BruteForce {
    private BruteForce() {}
    /** Input: non-null array of distinct integers, including empty arrays. */
    public static long count(int[] values) {
        // TODO: Implement an independent pair-counting baseline. Do not mutate values.
        long count = 0;
        
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] > values[j]) {
                    count++;
                }
            }
        }
        return count;
    }
}
