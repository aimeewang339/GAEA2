package part1;

import common.ExperimentConfig;
import common.Instances;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToLongFunction;

public final class RuntimeExperiment {
    private RuntimeExperiment() {}
    private record Measurement(long bruteNs, long optimizedNs, long count) {}

    public static void run(Path outputDirectory) throws Exception {
        run(outputDirectory, BruteForce::count, DivideAndConquer::sortAndCount);
    }

    static Path run(Path outputDirectory, ToLongFunction<int[]> baseline,
                    Function<int[], InversionResult> optimized) throws Exception {
        // Gate direct callers as well as the CLI. No output is written on validation failure.
        Validation.Summary validation = Validation.validate(baseline, optimized);
        int repetitions = ExperimentConfig.TIMING_REPETITIONS;
        if (repetitions < 1 || ExperimentConfig.WARMUP_REPETITIONS < 0)
            throw new IllegalArgumentException("Invalid repetition settings");
        Random random = new Random(ExperimentConfig.SEED);
        List<String> trials = new ArrayList<>();
        List<String> summaries = new ArrayList<>();
        trials.add("seed,family,n,algorithm,trial,runtime_ns,inversion_count,status");
        summaries.add("seed,family,n,algorithm,repetitions,median_ns");
        System.out.println("n,brute_force_median_ns,divide_and_conquer_median_ns");
        for (int n : ExperimentConfig.INVERSION_SIZES) {
            // Each trial gets a new uniformly shuffled permutation of 0..n-1.
            int[][] inputs = new int[ExperimentConfig.WARMUP_REPETITIONS + repetitions][];
            for (int i = 0; i < inputs.length; i++) inputs[i] = Instances.distinctArray(n, random);
            for (int i = 0; i < ExperimentConfig.WARMUP_REPETITIONS; i++)
                measure(inputs[i], i % 2 == 0, baseline, optimized);
            long[] bruteTimes = new long[repetitions];
            long[] optimizedTimes = new long[repetitions];
            for (int i = 0; i < repetitions; i++) {
                Measurement m = measure(inputs[ExperimentConfig.WARMUP_REPETITIONS + i],
                                        i % 2 == 0, baseline, optimized);
                bruteTimes[i] = m.bruteNs();
                optimizedTimes[i] = m.optimizedNs();
                trials.add(row(n, "brute_force", i, m.bruteNs(), m.count()));
                trials.add(row(n, "divide_and_conquer", i, m.optimizedNs(), m.count()));
            }
            double bruteMedian = median(bruteTimes);
            double optimizedMedian = median(optimizedTimes);
            String prefix = ExperimentConfig.SEED + ",random_permutation," + n + ",";
            summaries.add(prefix + "brute_force," + repetitions + "," + bruteMedian);
            summaries.add(prefix + "divide_and_conquer," + repetitions + "," + optimizedMedian);
            System.out.println(n + "," + bruteMedian + "," + optimizedMedian);
        }
        Files.createDirectories(outputDirectory);
        Path runDirectory = Files.createTempDirectory(outputDirectory, "inversions-");
        Files.write(runDirectory.resolve("inversion_trials.csv"), trials, StandardCharsets.UTF_8);
        Files.write(runDirectory.resolve("inversion_summary.csv"), summaries, StandardCharsets.UTF_8);
        Files.writeString(runDirectory.resolve("validation.csv"),
                "part,seed,hand_cases,random_cases,checks_passed,status\n1,"
                + ExperimentConfig.SEED + "," + validation.handCases() + ","
                + validation.randomCases() + "," + validation.casesPassed() + ",ok\n");
        Files.writeString(runDirectory.resolve("environment.md"), environment());
        System.out.println("Results written to " + runDirectory.toAbsolutePath());
        return runDirectory;
    }

    private static Measurement measure(int[] input, boolean bruteFirst,
            ToLongFunction<int[]> baseline, Function<int[], InversionResult> optimized) {
        int[] bruteInput = input.clone();
        int[] optimizedInput = input.clone();
        long bruteNs, optimizedNs, count, start;
        InversionResult result;
        // Alternate order to reduce systematic first/second-run bias.
        if (bruteFirst) {
            start = System.nanoTime();
            count = baseline.applyAsLong(bruteInput);
            bruteNs = System.nanoTime() - start;
            start = System.nanoTime();
            result = optimized.apply(optimizedInput);
            optimizedNs = System.nanoTime() - start;
        } else {
            start = System.nanoTime();
            result = optimized.apply(optimizedInput);
            optimizedNs = System.nanoTime() - start;
            start = System.nanoTime();
            count = baseline.applyAsLong(bruteInput);
            bruteNs = System.nanoTime() - start;
        }
        // Consume and check all results outside timing, including warmup runs.
        String context = "benchmark n=" + input.length + ", seed=" + ExperimentConfig.SEED;
        Validation.require(Arrays.equals(input, bruteInput), context, input, "baseline mutated input");
        Validation.require(Arrays.equals(input, optimizedInput), context, input, "optimized mutated input");
        Validation.verifyResult(input, count, result, context);
        return new Measurement(bruteNs, optimizedNs, count);
    }

    private static String row(int n, String algorithm, int trial, long ns, long count) {
        return ExperimentConfig.SEED + ",random_permutation," + n + "," + algorithm
                + "," + trial + "," + ns + "," + count + ",ok";
    }

    static double median(long[] samples) {
        if (samples.length == 0) throw new IllegalArgumentException("No timing samples");
        long[] sorted = samples.clone();
        Arrays.sort(sorted);
        int middle = sorted.length / 2;
        return sorted.length % 2 == 1 ? sorted[middle]
                : sorted[middle - 1] / 2.0 + sorted[middle] / 2.0;
    }

    private static String environment() {
        return "# Inversion experiment environment\n\n"
                + "- Timestamp (UTC): " + Instant.now() + "\n"
                + "- Java: " + System.getProperty("java.runtime.version") + " / "
                + System.getProperty("java.vm.name") + "\n"
                + "- OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version")
                + " / " + System.getProperty("os.arch") + "\n"
                + "- CPU: " + System.getenv().getOrDefault("PROCESSOR_IDENTIFIER", "record CPU model manually")
                + "; logical processors=" + Runtime.getRuntime().availableProcessors() + "\n"
                + "- Command: " + System.getProperty("sun.java.command", "unknown") + "\n"
                + "- Git commit: " + git("rev-parse", "HEAD").strip() + "\n"
                + "- Git status (empty means clean):\n```text\n" + git("status", "--short") + "\n```\n"
                + "- Seed: " + ExperimentConfig.SEED + "; family: uniform random permutations of 0..n-1\n"
                + "- Sizes: " + Arrays.toString(ExperimentConfig.INVERSION_SIZES) + "\n"
                + "- Warmups per size: " + ExperimentConfig.WARMUP_REPETITIONS
                + "; measured repetitions: " + ExperimentConfig.TIMING_REPETITIONS + "\n"
                + "- Identical independent input copies per algorithm; alternating execution order.\n"
                + "- Timed region: complete solver invocation (including its allocations/result construction).\n"
                + "- Excluded: generation, caller input cloning, validation, formatting and file I/O.\n"
                + "- Median: central sample, or mean of the two central samples for even counts.\n"
                + "- No automatic size extension: configured maximum bounds quadratic baseline work.\n"
                + "- checks_passed in validation.csv counts complete passing cases, not individual assertions.\n"
                + "- Plot summary n versus median_ns grouped by algorithm; retain raw trials.\n"
                + "- Interpret crossover from measured medians; short runs and JVM noise limit precision.\n";
    }

    private static String git(String... arguments) {
        List<String> command = new ArrayList<>(List.of("git"));
        command.addAll(List.of(arguments));
        try {
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
            // Drain output concurrently so a large status cannot fill the pipe.
            var output = new java.io.ByteArrayOutputStream();
            Thread reader = Thread.startVirtualThread(() -> {
                try (var stream = process.getInputStream()) { stream.transferTo(output); }
                catch (java.io.IOException ignored) { /* Main thread records process failure. */ }
            });
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                return "unavailable (git timed out); record manually";
            }
            reader.join();
            return process.exitValue() == 0 ? output.toString(StandardCharsets.UTF_8)
                    : "unavailable; record manually";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "unavailable (interrupted); record manually";
        } catch (java.io.IOException e) {
            return "unavailable; record manually";
        }
    }
}
