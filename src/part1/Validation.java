package part1;

import common.ExperimentConfig;
import common.Instances;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/** Independent output checks and reproducible baseline cross-checks. */
public final class Validation {
    private Validation() {}
    public record Summary(int handCases, int randomCases) {
        public int casesPassed() { return handCases + randomCases; }
    }
    private record HandCase(int[] input, long expected) {}

    public static void run() { validate(); }

    public static Summary validate() {
        return validate(BruteForce::count, DivideAndConquer::sortAndCount);
    }

    // Package-private injection supports testing the checker with deliberately bad solvers.
    static Summary validate(ToLongFunction<int[]> baseline,
                            Function<int[], InversionResult> optimized) {
        HandCase[] cases = {
            new HandCase(new int[]{}, 0),
            new HandCase(new int[]{7}, 0),
            new HandCase(new int[]{1, 2}, 0),
            new HandCase(new int[]{2, 1}, 1),
            new HandCase(new int[]{1, 2, 3, 4, 5}, 0),
            new HandCase(new int[]{5, 4, 3, 2, 1}, 10),
            new HandCase(new int[]{1, 2, 4, 3, 5}, 1),
            new HandCase(new int[]{2, 3, 1}, 2),
            new HandCase(new int[]{3, 1, 4, 2}, 3),
            new HandCase(new int[]{4, 1, 3, 2}, 4),
            new HandCase(new int[]{-1, -3, -2}, 2),
            new HandCase(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}, 3)
        };
        for (int i = 0; i < cases.length; i++) {
            check(cases[i].input(), cases[i].expected(), "hand case " + i, baseline, optimized);
        }
        Random random = new Random(ExperimentConfig.SEED);
        for (int i = 0; i < ExperimentConfig.INVERSION_RANDOM_CASES; i++) {
            int[] input = Instances.distinctArray(random.nextInt(65), random);
            check(input, null, "random case " + i + ", seed=" + ExperimentConfig.SEED,
                  baseline, optimized);
        }
        Summary summary = new Summary(cases.length, ExperimentConfig.INVERSION_RANDOM_CASES);
        System.out.printf("Inversion validation passed: %d hand cases, %d random cases (seed=%d).%n",
                          summary.handCases(), summary.randomCases(), ExperimentConfig.SEED);
        return summary;
    }

    static void check(int[] input, Long expected, String context,
                      ToLongFunction<int[]> baseline, Function<int[], InversionResult> optimized) {
        int[] baselineInput = input.clone();
        long count = baseline.applyAsLong(baselineInput);
        require(Arrays.equals(input, baselineInput), context, input, "baseline mutated input");
        if (expected != null) {
            require(count == expected, context, input,
                    "baseline expected=" + expected + ", actual=" + count);
        }
        int[] optimizedInput = input.clone();
        InversionResult result = optimized.apply(optimizedInput);
        require(Arrays.equals(input, optimizedInput), context, input, "optimized solver mutated input");
        verifyResult(input, count, result, context);
    }

    static void verifyResult(int[] input, long expected, InversionResult result, String context) {
        require(result != null, context, input, "optimized solver returned null");
        require(result.count() == expected, context, input,
                "count expected=" + expected + ", actual=" + result.count());
        int[] sorted = input.clone();
        Arrays.sort(sorted); // Library sort independently checks both order and multiset.
        require(Arrays.equals(sorted, result.sorted()), context, input,
                "sorted output expected=" + Arrays.toString(sorted)
                + ", actual=" + Arrays.toString(result.sorted()));
    }

    static void require(boolean condition, String context, int[] input, String detail) {
        if (!condition) throw new AssertionError(context + ": " + detail
                                                + "; input=" + Arrays.toString(input));
    }
}
