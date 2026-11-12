package part2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CounterexampleSearch {
    private CounterexampleSearch() {}
    public static void main(String[] args) throws Exception {
        run(Path.of(args.length == 0 ? "results" : args[0]));
    }
    public static void run(Path outputDirectory) throws Exception {
        Validation.Summary validation = Validation.validate();
        List<String> rows = new ArrayList<>();
        rows.add("denominations,target,greedy_count,greedy_coins,optimal_count,optimal_coins");
        boolean sanityFound = false;
        int systemsFound = 0;
        // Exhaustively search {1,a,b}, 2 <= a < b <= 8, and targets 1..12.
        for (int a = 2; a <= 7; a++) {
            for (int b = a + 1; b <= 8; b++) {
                int[] denominations = {1, a, b};
                boolean systemFound = false;
                for (int target = 1; target <= 12; target++) {
                    Validation.check(denominations, target, null, "counterexample search");
                    CoinResult greedyResult = new Greedy().solve(denominations, target);
                    CoinResult exactResult = new BottomUp().solve(denominations, target);
                    SolutionValidator.validate(denominations, target, greedyResult);
                    SolutionValidator.validate(denominations, target, exactResult);
                    if (greedyResult instanceof CoinResult.Solution greedy
                            && exactResult instanceof CoinResult.Solution exact
                            && greedy.coinCount() > exact.coinCount()) {
                        rows.add("1;" + a + ";" + b + "," + target + "," + greedy.coinCount()
                                + "," + coins(greedy.chosenCoins()) + "," + exact.coinCount()
                                + "," + coins(exact.chosenCoins()));
                        System.out.println("D=" + Arrays.toString(denominations) + ", A=" + target
                                + ": greedy=" + greedy.chosenCoins() + ", optimum=" + exact.chosenCoins());
                        systemFound = true;
                        if (a == 3 && b == 4 && target == 6) {
                            sanityFound = greedy.coinCount() == 3 && exact.coinCount() == 2;
                        }
                    }
                }
                if (systemFound) systemsFound++;
            }
        }
        Validation.require(sanityFound, "search", "required {1,3,4}, A=6 counterexample missing");
        Validation.require(systemsFound >= 3, "search", "fewer than three distinct coin systems");
        Files.createDirectories(outputDirectory);
        Files.write(outputDirectory.resolve("greedy_counterexamples.csv"), rows);
        Files.writeString(outputDirectory.resolve("part2b_validation.csv"),
                "part,seed,hand_cases,random_cases,checks_passed,status\n2B,"
                + common.ExperimentConfig.SEED + "," + validation.handCases() + ","
                + validation.randomCases() + "," + validation.casesPassed() + ",ok\n");
        System.out.printf("Found %d counterexamples across %d distinct systems. Wrote %s%n",
                          rows.size() - 1, systemsFound, outputDirectory);
    }
    private static String coins(List<Integer> values) {
        List<String> text = new ArrayList<>();
        for (int value : values) text.add(Integer.toString(value));
        return String.join(";", text);
    }
}
