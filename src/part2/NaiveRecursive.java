package part2;
import common.Unfinished;

public final class NaiveRecursive implements CoinSolver {
    @Override public CoinResult solve(int[] denominations, int target) {
        return solve(denominations, target, new SolverMetrics());
    }
    public CoinResult solve(int[] denominations, int target, SolverMetrics metrics) {
        // TODO: Implement exact recursion without memoization and reconstruct a solution.
        // Apply the shared input/output contract; independently validate reconstruction.
        // Instrument applicable counters. For recursion enforce the documented call
        // budget using SearchLimitExceeded, never IMPOSSIBLE. Treat stack exhaustion
        // as a resource limit, not as a mathematical answer.
        throw new Unfinished("Part 2A: NaiveRecursive");
    }
}
