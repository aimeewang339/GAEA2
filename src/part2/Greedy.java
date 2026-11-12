package part2;
import common.Unfinished;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public final class Greedy implements CoinSolver {
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
        List<Integer> coins = new ArrayList<>();
        int remaining = target;
        for (int i = sorted.length - 1; i >= 0; i--) {   // largest to smallest
            while (remaining >= sorted[i]) {             // use largest coin
                remaining -= sorted[i];
                coins.add(sorted[i]);//add to list   
            }
        }

        if (remaining != 0){
            return CoinResult.IMPOSSIBLE;   // greedy failed
        }
        return new CoinResult.Solution(coins.size(), coins);
        // throw new Unfinished("Part 2A: Greedy"); COMPLETED
    }
}
