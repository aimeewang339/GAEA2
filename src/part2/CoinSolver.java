package part2;

public interface CoinSolver {
    /**
     * Positive, unique denominations (order arbitrary); target >= 0; unlimited coins.
     * Do not mutate denominations. Invalid arguments: IllegalArgumentException.
     * Target zero: Solution(0, empty list). Impossible: CoinResult.IMPOSSIBLE.
     * Exact solvers return a minimum count and one reconstruction, not all optima.
     * Greedy may be suboptimal or fail even when an exact solution exists.
     */
    CoinResult solve(int[] denominations, int target);
}
