# GAEA2 - Group Algorithm Engineering starter

Java 21+, standard library only. Both algorithm suites and experiment commands are implemented.
The report and contribution files must be completed before submission.

## Quick start

Open this inner `GAEA2` repository (the directory containing `src` and `.git`).
Check `java -version` and `javac -version`; both must be 21 or newer.
From this directory in PowerShell, Command Prompt, or a Unix shell:

```sh
javac --release 21 -d out "@sources.txt"
java -cp out common.Run help
```

When adding Java files, add their paths to `sources.txt`. No Maven/Gradle or external
dependencies are required. VS Code Java source/output paths and build/run tasks
are included; use the Java extension's debugger or the supplied tasks.

## Reproduction commands

```sh
java -cp out common.Run validate-inversions
java -cp out common.Run benchmark-inversions results
java -cp out common.Run validate-coins
java -cp out common.Run search-greedy results
java -cp out common.Run memoization results
java -cp out common.Run pseudo-polynomial results
java -cp out common.Run all results
```

Experiment commands gate execution on the relevant validation suite.
Use a different output directory for independent runs, e.g. `results/run-02`.
Part 1 creates a unique `inversions-*` subdirectory with raw/summary CSV, validation
totals, and environment metadata. Part 2 writes greedy_counterexamples.csv, part2b_validation.csv, memoization.csv,
pseudo_polynomial_raw.csv, and pseudo_polynomial_summary.csv.

## Layout and work ownership

| Location | Work to complete |
| --- | --- |
| `src/part1/BruteForce.java`, `DivideAndConquer.java` | Part 1A counters and merge/count helper |
| `src/part1/Validation.java`, `RuntimeExperiment.java` | Part 1B hand/random validation and scaling |
| `src/part2/Greedy.java`, `NaiveRecursive.java`, `Memoized.java`, `BottomUp.java` | Part 2A common-interface solvers |
| `src/part2/SolutionValidator.java`, `Validation.java`, `CounterexampleSearch.java` | Part 2B independent checks and automated search |
| `src/part2/SolverMetrics.java`, `MemoizationExperiment.java`, `PseudoPolynomialExperiment.java` | Part 2C instrumentation and scaling |
| `src/common/Instances.java`, `ExperimentConfig.java` | Shared reproducible inputs and settings |
| `report/REPORT.md` | Parts 1/2 analysis and all six Part 3 reflection prompts |
| `report/CONTRIBUTIONS.md`, `AI_USE.md` | Required disclosure and contributions |
| `docs/WORK_PLAN.md`, `RESULT_SCHEMAS.md` | Acceptance checklist and output contracts |

Agree on interfaces before splitting work. Implement independent baselines and
hand expectations before optimized solutions; keep validation independent from
solver internals. Review each other's work and record contributors. Part III is
written reflection using your actual measurements, so it has no algorithm module.

## Contracts and experiment discipline

- Inversion inputs contain distinct integers; neither counter mutates its input.
  Counts use `long`; divide-and-conquer returns an `InversionResult` with sorted data.
- Coin solvers share `CoinSolver` and return `CoinResult.Solution` or
  `CoinResult.IMPOSSIBLE`. Different optimal reconstructions are valid.
- Input validation, safety-budget enforcement, instrumentation placement, and
  tie-breaking are student work. Document deterministic choices.
- `ExperimentConfig` supplies initial seed, counts, sizes, warmups, repetitions,
  and naive safety limits. These are starting values, not measured safe bounds.
  Enforce both the target bound in experiments and the call budget in recursion.
  `SearchLimitExceeded` must never be interpreted as `IMPOSSIBLE`.
- Generate inputs outside timing, use identical inputs across comparisons, warm
  up the JVM, use fresh caches, repeat runs, and retain raw trials plus medians.
- Record OS/CPU/JDK, seed, configurations, cutoff outcomes, and timing boundaries.
  Extend input sizes as needed to reveal scaling without unbounded runs.
- Commit actual CSV results and figures after measurement; no sample performance
  numbers or prewritten analysis are included. Finite tests are not a proof.

See `docs/WORK_PLAN.md` for the full completion and submission checklist.

## Part 1 harness checks

Validation includes 12 hand cases and 200 seeded random arrays of length 0-64.
It checks exact counts, sorted output, preservation of values, and input immutability.
The benchmark validates first, warms up each size, alternates algorithm order, and
records seven trials per algorithm/size plus medians. Input preparation and output
checks are outside timing; complete solver calls and their allocations are included.

```sh
javac --release 21 -d out "@sources.txt"
javac --release 21 -cp out -d out tests/part1/HarnessTest.java
java -cp out part1.HarnessTest
```

Harness tests inject a library-sort test double to exercise success and deliberate
failure paths. Their temporary timing files are synthetic test artifacts.
Use common.Run all for real solver validation and experiments.
