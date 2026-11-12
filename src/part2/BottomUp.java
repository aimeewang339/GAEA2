package part2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
public final class BottomUp implements CoinSolver {
    private static final int NO_SOLUTION = -1;  // amount cannot be made
 
    @Override public CoinResult solve(int[] denominations, int target) {
        return solve(denominations, target, new SolverMetrics());
    }
    public CoinResult solve(int[] denominations, int target, SolverMetrics metrics) {
        if (denominations == null) throw new IllegalArgumentException("denominations is null");
        if (target < 0) throw new IllegalArgumentException("target must be >= 0");
        int[] sorted = denominations.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] <= 0) throw new IllegalArgumentException("coins must be positive");
            if (i > 0 && sorted[i] == sorted[i - 1]) throw new IllegalArgumentException("duplicate coin");
        }
        //base cases

        // create rows
        int[] minCount = new int[target + 1];   // best choice of row
        int[] bestCoin = new int[target + 1];   // rows for memoization of each possible amount
 
        // base case
        minCount[0] = 0;
        metrics.dpStatesProcessed++;
        metrics.distinctTargetsSolved.add(0);
 
        // fill the table
        for (int amount = 1; amount <= target; amount++) {
            minCount[amount] = NO_SOLUTION;
            for (int coin : sorted) { // try all coins and store
                if (coin > amount) break;            
                int sub = minCount[amount - coin];
                if (sub == NO_SOLUTION) continue;  
                if (minCount[amount] == NO_SOLUTION || sub + 1 < minCount[amount]) {
                    minCount[amount] = sub + 1;
                    bestCoin[amount] = coin;
                }
            }
            metrics.dpStatesProcessed++;
            metrics.distinctTargetsSolved.add(amount);
        }
 
        if (minCount[target] == NO_SOLUTION) { // no solution found
            return CoinResult.IMPOSSIBLE;
        }
 
        // rebuild the coin list by following the chosen coin for each amount
        List<Integer> coins = new ArrayList<>();
        int remaining = target;
        while (remaining > 0) {
            coins.add(bestCoin[remaining]);
            remaining -= bestCoin[remaining];
        }
        return new CoinResult.Solution(coins.size(), coins);
    }
}
 