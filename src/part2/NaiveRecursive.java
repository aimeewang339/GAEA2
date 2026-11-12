package part2;
import common.ExperimentConfig;
import common.SearchLimitExceeded;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
public final class NaiveRecursive implements CoinSolver {
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
        List<Integer> coins = best(sorted, target, metrics); // call to helper function that recursively calls the function
        if (coins == null) {
            return CoinResult.IMPOSSIBLE;
        }
        return new CoinResult.Solution(coins.size(), coins);
    }
    private static List<Integer> best(int[] coins, int amount, SolverMetrics metrics) {
        metrics.recursiveCalls++;
        if (metrics.recursiveCalls > ExperimentConfig.MAX_RECURSIVE_CALLS) { // prevent infinite recursive calls 
            throw new SearchLimitExceeded("naive recursion passed " + ExperimentConfig.MAX_RECURSIVE_CALLS + " calls");
        }
 
        if (amount == 0) { // base amount for testing  
            metrics.distinctTargetsSolved.add(0);
            return new ArrayList<>();
        }
 
        List<Integer> bestList = null; // best case
        for (int coin : coins) { // attempt all coins then branch to complete them individually
            if (coin > amount) continue;         // check if coin fits  
            //take the best solution from the branches of adding coins
            List<Integer> sub = best(coins, amount - coin, metrics);
            if (sub == null) continue;
            if (bestList == null || sub.size() + 1 < bestList.size()) {
                sub.add(coin); 
                bestList = sub;
            }
        }
        //return solutions
        metrics.distinctTargetsSolved.add(amount);
        return bestList;
    }
}