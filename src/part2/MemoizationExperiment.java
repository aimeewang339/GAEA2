package part2;
import common.Unfinished;
import java.nio.file.Path;

public final class MemoizationExperiment {
    private MemoizationExperiment() {}
    public static void run(Path outputDirectory) throws Exception {
        // TODO: Compare naive recursion and top-down memoization as target increases.
        // Record recursive calls, distinct solved targets, cache hits, and runtime.
        // Enforce MAX_NAIVE_TARGET before a run and MAX_RECURSIVE_CALLS within recursion.
        // Record cutoff rows explicitly; no incomplete answer is an optimum.
        // Use fresh caches/metrics per trial; document instrumentation overhead and
        // recursive depth limits. Extend targets only after assessing practical costs.
        // CSV contracts: docs/RESULT_SCHEMAS.md.
        throw new Unfinished("Part 2: MemoizationExperiment");
    }
}
