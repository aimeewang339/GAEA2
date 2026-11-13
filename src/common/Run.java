package common;
import java.nio.file.Path;

/** Dispatcher only; algorithm/validation/experiment implementations are student work. */
public final class Run {
    private Run() {}
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || args[0].equals("help")) {
            System.out.println("Usage: java -cp out common.Run TASK [results-directory]");
            System.out.println("Tasks: validate-inversions, benchmark-inversions, validate-coins,");
            System.out.println("       search-greedy, memoization, pseudo-polynomial, all");
            System.out.println("Run all to validate both parts and write experiment CSV files.");
            return;
        }
        if (args.length > 2) throw new IllegalArgumentException("Expected TASK [results-directory]");
        Path output = Path.of(args.length == 2 ? args[1] : "results");
        try {
            switch (args[0]) {
                case "validate-inversions" -> part1.Validation.run();
                case "benchmark-inversions" -> part1.RuntimeExperiment.run(output);
                case "validate-coins" -> part2.Validation.run();
                case "search-greedy" -> { part2.Validation.run(); part2.CounterexampleSearch.run(output); }
                case "memoization" -> { part2.Validation.run(); part2.MemoizationExperiment.run(output); }
                case "pseudo-polynomial" -> { part2.Validation.run(); part2.PseudoPolynomialExperiment.run(output); }
                case "all" -> {
                    part1.Validation.run();
                    part2.Validation.run();
                    part1.RuntimeExperiment.run(output);
                    part2.CounterexampleSearch.run(output);
                    part2.MemoizationExperiment.run(output);
                    part2.PseudoPolynomialExperiment.run(output);
                }
                default -> throw new IllegalArgumentException("Unknown task: " + args[0]);
            }
        } catch (Unfinished unfinished) {
            System.err.println(unfinished.getMessage());
            System.exit(2);
        }
    }
}
