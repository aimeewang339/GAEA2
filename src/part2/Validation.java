package part2;
import common.Unfinished;

public final class Validation {
    private Validation() {}
    public static void run() {
        // TODO: >=10 hand cases with independently derived expected outcomes:
        // zero target, unreachable amounts, multiple optima, unsorted D, single coin.
        // TODO: >=100 seeded small random instances practical for naive recursion.
        // Default random D includes 1; deliberately add infeasible systems separately.
        // Compare all three exact counts / IMPOSSIBLE, not identical chosen lists.
        // Independently validate every returned solution, including greedy solutions.
        // Do NOT assert that greedy always agrees with exact solvers.
        // Include D={1,3,4}, A=6 as the assignment's required sanity check.
        throw new Unfinished("Part 2B: exact-solver cross-validation");
    }
}
