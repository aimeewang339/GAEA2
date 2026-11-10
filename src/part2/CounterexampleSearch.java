package part2;
import common.Unfinished;
import java.nio.file.Path;

public final class CounterexampleSearch {
    private CounterexampleSearch() {}
    public static void run(Path outputDirectory) throws Exception {
        // TODO: Validate exact solvers before treating DP as the oracle.
        // Search coin systems and targets programmatically. Report >=3 distinct cases
        // where greedy is feasible but has strictly more coins than the optimum.
        // Detect the supplied D={1,3,4}, A=6 sanity case. Check every returned list.
        // Store both reconstructions, counts, seed/search bounds, and distinctness rule.
        // CSV contracts: docs/RESULT_SCHEMAS.md.
        throw new Unfinished("Part 2: CounterexampleSearch");
    }
}
