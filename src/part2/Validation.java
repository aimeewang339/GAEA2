package part2;

import common.ExperimentConfig;
import java.util.Arrays;
import java.util.Random;

/** Independent reconstruction checks and seeded exact-solver comparisons. */
public final class Validation {
    private Validation() {}
    public record Summary(int handCases, int randomCases) {
        public int casesPassed() { return handCases + randomCases; }
    }
    private record HandCase(int[] denominations, int target, int expected) {}

    public static void main(String[] args) { run(); }
    public static void run() { validate(); }

    public static Summary validate() {
        // -1 means IMPOSSIBLE. Expected counts are derived by hand in report/PART2B.md.
        HandCase[] cases = {
            new HandCase(new int[]{1, 3, 4}, 0, 0),
            new HandCase(new int[]{2, 4}, 3, -1),
            new HandCase(new int[]{5}, 10, 2),
            new HandCase(new int[]{5}, 3, -1),
            new HandCase(new int[]{1, 3, 4}, 6, 2),
            new HandCase(new int[]{1, 2, 3}, 4, 2),
            new HandCase(new int[]{4, 1, 3}, 6, 2),
            new HandCase(new int[]{1, 5, 10}, 10, 1),
            new HandCase(new int[]{2, 3}, 7, 3),
            new HandCase(new int[]{3, 4}, 6, 2)
        };
        for (int i = 0; i < cases.length; i++) {
            HandCase test = cases[i];
            check(test.denominations(), test.target(), test.expected(), "hand case " + i);
        }
        Random random = new Random(ExperimentConfig.SEED);
        int randomCases = Math.max(100, ExperimentConfig.COIN_RANDOM_CASES);
        for (int i = 0; i < randomCases; i++) {
            // Every fifth case deliberately omits 1. Other cases are always feasible.
            int count = 1 + random.nextInt(4);
            int[] denominations = new int[count];
            boolean[] used = new boolean[9];
            for (int j = 0; j < count; j++) {
                int coin;
                if (j == 0 && i % 5 != 0) coin = 1;
                else {
                    do { coin = 2 + random.nextInt(7); } while (used[coin]);
                }
                denominations[j] = coin;
                used[coin] = true;
            }
            check(denominations, random.nextInt(13), null,
                  "random case " + i + ", seed=" + ExperimentConfig.SEED);
        }
        Summary summary = new Summary(cases.length, randomCases);
        System.out.printf("Coin validation passed: %d hand cases, %d random cases (seed=%d).%n",
                          summary.handCases(), summary.randomCases(), ExperimentConfig.SEED);
        return summary;
    }

    static void check(int[] denominations, int target, Integer expected, String context) {
        CoinSolver[] solvers = {new NaiveRecursive(), new Memoized(), new BottomUp(), new Greedy()};
        int optimum = -1;
        for (int i = 0; i < solvers.length; i++) {
            String label = context + ", solver=" + solvers[i].getClass().getSimpleName()
                    + ", D=" + Arrays.toString(denominations) + ", target=" + target;
            int[] input = denominations.clone();
            CoinResult result = solvers[i].solve(input, target);
            require(Arrays.equals(input, denominations), label, "input mutated");
            try { SolutionValidator.validate(denominations, target, result); }
            catch (AssertionError error) { throw new AssertionError(label + ": " + error.getMessage(), error); }
            int count = result instanceof CoinResult.Solution solution ? solution.coinCount() : -1;
            if (i == 0) optimum = count;
            if (i < 3) {
                require(count == optimum, label, "expected exact count=" + optimum + ", actual=" + count);
                if (expected != null)
                    require(count == expected, label, "hand expected=" + expected + ", actual=" + count);
            } else if (count >= 0) {
                require(optimum >= 0 && count >= optimum, label, "greedy contradicts exact optimum");
            }
        }
    }

    static void require(boolean condition, String context, String detail) {
        if (!condition) throw new AssertionError(context + ": " + detail);
    }
}
