package part2;
import java.util.HashSet;
import java.util.Set;

/** Fresh instance for each measured run. Students place the instrumentation. */
public final class SolverMetrics {
    public long recursiveCalls;
    public long cacheHits;
    public long dpStatesProcessed;
    public final Set<Integer> distinctTargetsSolved = new HashSet<>();
    // TODO in solvers: define/document when a state counts as solved (including bases).
    // The set counts distinct solved targets, not total repeated evaluations.
}
