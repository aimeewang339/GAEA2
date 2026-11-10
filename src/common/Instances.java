package common;

import java.util.Random;

/** Group implements deterministic generators using the supplied seeded RNG. */
public final class Instances {
    private Instances() {}
    public static int[] distinctArray(int size, Random random) {
        // TODO: Generate a random ordering of distinct values; document distribution.
        throw new Unfinished("Part 1B: distinct-array generator");
    }
    public static int[] denominations(int count, int maximum, boolean includeOne, Random random) {
        // TODO: Generate unique positive denominations; normally include 1.
        // Validate that requested count is possible before generating.
        throw new Unfinished("Part 2B: denomination generator");
    }
}
