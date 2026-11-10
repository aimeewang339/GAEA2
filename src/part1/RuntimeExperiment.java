package part1;
import common.Unfinished;
import java.nio.file.Path;

public final class RuntimeExperiment {
    private RuntimeExperiment() {}
    public static void run(Path outputDirectory) throws Exception {
        // TODO: Validate first, then benchmark both counters at increasing n.
        // Pre-generate matching input per trial, warm up JVM, repeat measurements,
        // consume returned results, and keep generation/validation/CSV outside timing.
        // Use System.nanoTime, report medians, preserve raw trials, and extend sizes
        // if needed to reveal scaling. Record environment, seed, and input family.
        // See docs/RESULT_SCHEMAS.md; do not invent crossover or timing results.
        throw new Unfinished("Part 1B: inversion timing experiment");
    }
}
