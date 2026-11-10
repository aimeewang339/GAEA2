package part1;

import common.Instances;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

/** Tests the harness with a library-sort test double; not a divide-and-conquer implementation. */
public final class HarnessTest {
    private static InversionResult reference(int[] input) {
        int[] sorted = input.clone();
        Arrays.sort(sorted);
        return new InversionResult(BruteForce.count(input), sorted);
    }
    private static void rejects(Runnable action) {
        try { action.run(); }
        catch (AssertionError expected) { return; }
        throw new AssertionError("Checker accepted deliberately invalid output");
    }
    public static void main(String[] args) throws Exception {
        Validation.Summary summary = Validation.validate(BruteForce::count, HarnessTest::reference);
        if (summary.handCases() < 10 || summary.randomCases() < 200) throw new AssertionError();
        int[] input = {3, 1, 2};
        rejects(() -> Validation.check(input, 2L, "wrong count", BruteForce::count,
                a -> new InversionResult(0, new int[]{1, 2, 3})));
        rejects(() -> Validation.check(input, 2L, "wrong multiset", BruteForce::count,
                a -> new InversionResult(2, new int[]{1, 2, 4})));
        rejects(() -> Validation.check(input, 2L, "unsorted", BruteForce::count,
                a -> new InversionResult(2, a)));
        rejects(() -> Validation.check(input, 2L, "mutation", BruteForce::count,
                a -> { Arrays.sort(a); return new InversionResult(2, a); }));
        rejects(() -> Validation.check(input, 2L, "baseline mutation",
                a -> { Arrays.sort(a); return 2; }, HarnessTest::reference));
        rejects(() -> Validation.check(input, 2L, "wrong baseline", a -> 0, HarnessTest::reference));
        rejects(() -> Validation.check(input, 2L, "null result", BruteForce::count, a -> null));
        int[] first = Instances.distinctArray(100, new Random(42));
        if (!Arrays.equals(first, Instances.distinctArray(100, new Random(42)))) throw new AssertionError();
        Arrays.sort(first);
        for (int i = 0; i < first.length; i++) if (first[i] != i) throw new AssertionError();
        if (Instances.distinctArray(0, new Random(42)).length != 0) throw new AssertionError();
        try { Instances.distinctArray(-1, new Random(42)); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { /* expected */ }
        if (RuntimeExperiment.median(new long[]{9, 1, 3}) != 3
                || RuntimeExperiment.median(new long[]{4, 1, 3, 2}) != 2.5) throw new AssertionError();
        Path temp = Files.createTempDirectory("gaea2-harness-test-");
        Path blocked = temp.resolve("blocked");
        rejects(() -> {
            try { RuntimeExperiment.run(blocked, BruteForce::count, a -> null); }
            catch (RuntimeException | Error e) { throw e; }
            catch (Exception e) { throw new RuntimeException(e); }
        });
        if (Files.exists(blocked)) throw new AssertionError("Failed validation wrote output");
        // Synthetic test-double timings are temporary test artifacts, never assignment evidence.
        Path result = RuntimeExperiment.run(temp, BruteForce::count, HarnessTest::reference);
        int sizes = common.ExperimentConfig.INVERSION_SIZES.length;
        int reps = common.ExperimentConfig.TIMING_REPETITIONS;
        var trials = Files.readAllLines(result.resolve("inversion_trials.csv"));
        if (trials.size() != 1 + sizes * reps * 2) throw new AssertionError("Trial rows missing");
        if (Files.readAllLines(result.resolve("inversion_summary.csv")).size() != 1 + sizes * 2)
            throw new AssertionError("Summary rows missing");
        if (!Files.readString(result.resolve("validation.csv")).contains(",12,200,212,ok"))
            throw new AssertionError("Validation totals missing");
        if (!Files.exists(result.resolve("environment.md"))) throw new AssertionError();
        for (int i = 1; i < trials.size(); i++) {
            String[] cells = trials.get(i).split(",");
            if (cells.length != 8 || Long.parseLong(cells[5]) < 0 || !cells[7].equals("ok"))
                throw new AssertionError("Invalid trial row");
        }
        System.out.println("Harness tests passed; temporary test-double data only: " + temp);
    }
}
