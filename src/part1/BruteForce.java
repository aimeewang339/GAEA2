package part1;
import java.util.*;
import common.Unfinished;

public final class BruteForce {
    private BruteForce() {}
    public static long count(int[] values) {
        long count = 0;
        /* brute force using two for loops in n^2 time */
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
