package common;

import java.util.Random;

/** Reproducible input generators using the supplied seeded RNG. */
public final class Instances {
    private Instances() {}
    public static int[] distinctArray(int size, Random random) {
        if (size < 0) throw new IllegalArgumentException("size must be nonnegative");
        java.util.Objects.requireNonNull(random, "random");
        int[] values = new int[size];
        for (int i = 0; i < size; i++) values[i] = i;
        // Fisher-Yates: uniform permutation of 0..size-1 for a uniform RNG.
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int value = values[i];
            values[i] = values[j];
            values[j] = value;
        }
        return values;
    }
    public static int[] denominations(int count, int maximum, boolean includeOne, Random random) {
        // TODO: Generate unique positive denominations; normally include 1.
        // Validate that requested count is possible before generating.
        throw new Unfinished("Part 2B: denomination generator");
    }
}
