# GAEA2 engineering report - working outline

Group members: TODO. Source commit: TODO. Run directory: TODO.
Target length: approximately 6-8 pages for the finished report.
Every section below requires the group's own work and evidence.

## 1. Methods and reproducibility
TODO: language/version, hardware/OS, input contracts, seeds/distributions, exact
commands, trial/warmup counts, timing boundaries, safety cutoffs, and data paths.

## 2. Part 1A: inversion algorithms
TODO: describe both implementations. Give a correctness argument for the baseline
and for left-half, right-half, and cross inversions in sort-and-count. Explain why
each inversion is counted exactly once. Analyze actual runtime/space and derive
the divide-and-conquer recurrence and asymptotic conclusion.

## 3. Part 1B: validation and runtime scaling
TODO: list >=10 hand cases and independently derived counts; document >=200 random
cross-checks and sorted-output checks. Report actual totals/failures. Add table/plot
from repeated timings and explain growth and observed crossover on your machine.

## 4. Part 2A: coin-change implementations
TODO: describe all four solvers, impossible states, base cases, reconstruction,
deterministic tie handling, and time/auxiliary-space analysis for each.

## 5. Part 2B: validation and greedy failure
TODO: >=10 hand cases; >=100 random small exact-solver comparisons; independent
solution validity checks. Explain how DP was validated before being used as oracle.
Describe automated search bounds and show >=3 distinct counterexamples with both
coin lists/counts. Include the required {1,3,4}, target 6 sanity check. Explain the
structural cause of greedy failure for at least one actual counterexample.

## 6. Part 2C: memoization and pseudo-polynomial behavior
TODO: report recursive calls, distinct states, cache hits, runtime, and cutoff
outcomes as A grows. Explain your instrumentation definitions and overhead.
Compare the recurrence and observed work of naive recursion and memoization.
For fixed k and geometric A, tabulate A, bit length, measured DP states, and runtime.
Explain why dependence on numeric A differs from dependence on its encoding length.

## 7. Part 3: cross-paradigm reflection

### 1. What work does divide and conquer avoid compared with checking every pair?
TODO: answer and connect to your Part 1 implementation/results.

### 2. Why are inversion subproblems independent before merging, while coin-change subproblems overlap?
TODO: answer with specific examples from your implementations.

### 3. What role does sorting play in the divide-and-conquer improvement?
TODO: answer using the merge/count behavior.

### 4. Why can naive and memoized coin-change runtimes differ despite the same recurrence?
TODO: answer with your call/state/cache measurements.

### 5. How did baselines/exact solvers help validate faster algorithms in both halves?
TODO: cite your validation evidence and explain its limitations.

### 6. What evidence would make you try divide and conquer versus dynamic programming on a new problem?
TODO: compare the structural evidence and connect to both experiments.

## 8. Limitations, contributions, and AI use
TODO: distinguish tests from general correctness arguments; discuss measurement
limits. Incorporate CONTRIBUTIONS.md and AI_USE.md into the final submission.
