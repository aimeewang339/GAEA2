
package part2;

import common.ExperimentConfig;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class PseudoPolynomialExperiment {

    private PseudoPolynomialExperiment() {}

    public static void main(String[] args) throws Exception {
        run(Path.of(args.length == 0 ? "results" : args[0]));
    }

    public static void run(Path outputDirectory) throws Exception {
        Files.createDirectories(outputDirectory);

        int[] coins = ExperimentConfig.FIXED_DENOMINATIONS.clone();
        BottomUp solver = new BottomUp();

        List<String> raw = new ArrayList<>();
        List<String> summary = new ArrayList<>();

        raw.add("target,bit_length,trial,dp_states,runtime_ns");

        summary.add("target,bit_length,dp_states,median_runtime_ns");

        for (int target : ExperimentConfig.SCALING_TARGETS) {

            int bits = Integer.SIZE - Integer.numberOfLeadingZeros(target);

            // Warmup runs
            for (int w = 0; w < ExperimentConfig.WARMUP_REPETITIONS; w++) {
                solver.solve(coins, target, new SolverMetrics());
            }

            long[] times = new long[ExperimentConfig.TIMING_REPETITIONS];
            long states = -1;

            for (int trial = 0; trial < times.length; trial++) {

                SolverMetrics m = new SolverMetrics();

                long start = System.nanoTime();

                CoinResult answer = solver.solve(coins, target, m);

                long elapsed = System.nanoTime() - start;

                MemoizationExperiment.verify(coins, target, answer);

                times[trial] = elapsed;
                states = m.dpStatesProcessed;

                raw.add(
                    target + "," + bits + "," + (trial + 1)
                    + "," + states + "," + elapsed
                );
            }

            Arrays.sort(times);

            long median = times[times.length / 2];

            summary.add(
                target + "," + bits + "," + states + "," + median
            );
        }

        Files.write(
            outputDirectory.resolve("pseudo_polynomial_raw.csv"),
            raw,
            StandardCharsets.UTF_8
        );

        Files.write(
            outputDirectory.resolve("pseudo_polynomial_summary.csv"),
            summary,
            StandardCharsets.UTF_8
        );

        System.out.println(
            "Wrote pseudo-polynomial CSV files in " + outputDirectory
        );
    }
}