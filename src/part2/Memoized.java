package part2;
import common.ExperimentConfig;
import common.SearchLimitExceeded;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 // same implentation as NaiveRecursive but with memoization on the best helper function
public final class Memoized implements CoinSolver {
    private static final int UNSOLVED = -2;
    private static final int NO_SOLUTION = -1;

    @Override public CoinResult solve(int[] denominations, int target) {
        return solve(denominations, target, new SolverMetrics());
    }
    public CoinResult solve(int[] denominations, int target, SolverMetrics metrics) {
        if (denominations == null) throw new IllegalArgumentException("denominations is null");
        if (target < 0) throw new IllegalArgumentException("target must be >= 0");
        int[] sorted = denominations.clone();
        Arrays.sort(sorted);

        for (int i = 0; i < sorted.length; i++) { //further checks to confirm no issues
            if (sorted[i] <= 0) throw new IllegalArgumentException("coins must be positive");
            if (i > 0 && sorted[i] == sorted[i - 1]) throw new IllegalArgumentException("duplicate coin");
        }
        //base cases
        int[] minCount = new int[target + 1];
        int[] bestCoin = new int[target + 1];
        Arrays.fill(minCount, UNSOLVED);

        int count;
        try {
            count = best(sorted, target, minCount, bestCoin, metrics); // call to helper function that recursively calls the function
        } catch (StackOverflowError e) {
            throw new SearchLimitExceeded("memoized recursion too deep for target " + target);
        }
        if (count == NO_SOLUTION) {
            return CoinResult.IMPOSSIBLE;
        }

        List<Integer> coins = new ArrayList<>();
        int remaining = target;
        while (remaining > 0) {
            coins.add(bestCoin[remaining]);
            remaining -= bestCoin[remaining];
        }
        return new CoinResult.Solution(coins.size(), coins);
    }

    private static int best(int[] coins, int amount, int[] minCount, int[] bestCoin, SolverMetrics metrics) {
        metrics.recursiveCalls++;
        if (metrics.recursiveCalls > ExperimentConfig.MAX_RECURSIVE_CALLS) { // prevent infinite recursive calls
            throw new SearchLimitExceeded("memoized recursion passed "
                    + ExperimentConfig.MAX_RECURSIVE_CALLS + " calls");
        }

        if (minCount[amount] != UNSOLVED) {
            metrics.cacheHits++;
            return minCount[amount];
        }

        if (amount == 0) { // base case
            minCount[0] = 0;
            metrics.distinctTargetsSolved.add(0);
            return 0;
        }

        int bestCount = NO_SOLUTION;
        for (int coin : coins) {
            if (coin > amount) break; // if largest coin does not fit rest wont
            int sub = best(coins, amount - coin, minCount, bestCoin, metrics);
            if (sub == NO_SOLUTION) continue; // ignore coins that cant solve the problem anymore
            if (bestCount == NO_SOLUTION || sub + 1 < bestCount) {
                bestCount = sub + 1;
                bestCoin[amount] = coin;
            }
        }

        minCount[amount] = bestCount; // memoize the best count to reduce recomputation in future calls
        metrics.distinctTargetsSolved.add(amount);
        return bestCount;
    }
}