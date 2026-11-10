package part2;
import common.Unfinished;

public final class Greedy implements CoinSolver {
    @Override public CoinResult solve(int[] denominations, int target) {
        return solve(denominations, target, new SolverMetrics());
    }
    public CoinResult solve(int[] denominations, int target, SolverMetrics metrics) {
        // TODO: Implement largest-feasible-coin selection and return its chosen coins.
        // Apply the shared input/output contract; independently validate reconstruction.
        // Instrument applicable counters. For recursion enforce the documented call
        // budget using SearchLimitExceeded, never IMPOSSIBLE. Treat stack exhaustion
        // as a resource limit, not as a mathematical answer.
        throw new Unfinished("Part 2A: Greedy");
    }
}
