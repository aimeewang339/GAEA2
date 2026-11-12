
package part2;

import common.ExperimentConfig;
import common.SearchLimitExceeded;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class MemoizationExperiment {

    private MemoizationExperiment() {}

    public static void main(String[] args) throws Exception {
        run(Path.of(args.length == 0 ? "results" : args[0]));
    }

    public static void run(Path outputDirectory) throws Exception {
        Files.createDirectories(outputDirectory);

        Path csv = outputDirectory.resolve("memoization.csv");

        List<String> lines = new ArrayList<>();

        lines.add(
            "solver,target,recursive_calls,distinct_states,cache_hits,runtime_ns,status"
        );

        int[] coins = ExperimentConfig.FIXED_DENOMINATIONS.clone();

        for (int target : ExperimentConfig.RECURSION_TARGETS) {

            for (String name : List.of("naive", "memoized")) {

                SolverMetrics m = new SolverMetrics();

                long start = System.nanoTime();
                String status = "OK";
                CoinResult result = null;

                try {
                    if (name.equals("naive")) {
                        result = new NaiveRecursive().solve(coins, target, m);
                    } else {
                        result = new Memoized().solve(coins, target, m);
                    }

                    verify(coins, target, result);

                } catch (SearchLimitExceeded e) {
                    status = "CUTOFF";
                }

                long elapsed = System.nanoTime() - start;

                lines.add(
                    name + "," + target + "," + m.recursiveCalls
                    + "," + m.distinctTargetsSolved.size()
                    + "," + m.cacheHits
                    + "," + elapsed + "," + status
                );
            }
        }

        Files.write(csv, lines, StandardCharsets.UTF_8);
        System.out.println("Wrote " + csv);
    }

    static void verify(int[] coins, int target, CoinResult result) {

        if (result instanceof CoinResult.Solution s) {
            long sum = 0;

            for (int c : s.chosenCoins()) {
                boolean exists = false;

                for (int d : coins) {
                    if (d == c) {
                        exists = true;
                    }
                }

                if (!exists) {
                    throw new AssertionError("Invalid denomination");
                }

                sum += c;
            }

            if (sum != target
                    || s.coinCount() != s.chosenCoins().size()) {

                throw new AssertionError("Invalid reconstructed solution");
            }

        } else if (result != CoinResult.IMPOSSIBLE) {
            throw new AssertionError("Unknown result type");
        }
    }
}
