# Group work plan and acceptance checklist

Assign owners and reviewers before starting. Each checkbox below is unfinished.

| Area | Owner | Reviewer | Dependencies |
| --- | --- | --- | --- |
| Part 1 algorithms | TODO | TODO | Agree on result/input contract |
| Part 1 validation/timing | TODO | TODO | Baseline and optimized counter |
| Part 2 solvers | TODO | TODO | Common interface, metrics definitions |
| Part 2 validation/search | TODO | TODO | Three independent exact solvers |
| Part 2 experiments | TODO | TODO | Validated solvers and safety limits |
| Part 3/report | TODO | TODO | Real results from both halves |

## Part 1A - 25 points
- [ ] Independent brute-force counter; divide-and-conquer counter and sorted output.
- [ ] Group-written correctness argument covering left/right/cross inversions exactly once.
- [ ] Analyze actual baseline code and derive the optimized recurrence and bound.

## Part 1B - 10 points
- [ ] At least 10 independently hand-checked arrays, including sorted/reverse/nearly/random.
- [ ] At least 200 seeded random small arrays; exact baseline/optimized agreement.
- [ ] Independently check sorted output and input preservation.
- [ ] Increasing sizes, repeated fair timings, medians, raw CSV, and readable graph/table.
- [ ] Explain observed growth and machine-dependent crossover (or report if not observed).

## Part 2A - 25 points
- [ ] All four solvers implement the shared result contract and reconstruction.
- [ ] Greedy follows its heuristic; naive recursion has no memoization.
- [ ] Memoized and bottom-up DP implemented independently.
- [ ] Zero/impossible inputs and deterministic choices handled; time/space analyzed.

## Part 2B - 15 points
- [ ] At least 10 hand cases, including zero, impossible, and multiple optimal solutions.
- [ ] At least 100 seeded small cases with all three exact solvers agreeing when practical.
- [ ] Independent sum, denomination-membership, and list/count verification for solutions.
- [ ] Automated search produces at least three distinct feasible-but-suboptimal greedy cases.
- [ ] Required sanity case: D={1,3,4}, A=6; greedy 4+1+1 versus optimal 3+3.
- [ ] Explain structurally why a largest-coin local choice fails for at least one case.

## Part 2C - 15 points
- [ ] Measure calls, distinct targets solved, and cache hits with documented definitions.
- [ ] Increase targets, enforce/document naive cutoff, record limit outcomes separately.
- [ ] Explain repeated-subproblem results using actual collected evidence.
- [ ] Fixed small k; geometrically increasing A; record A, bit length, DP states, runtime.
- [ ] Explain pseudo-polynomial behavior using the measured data and an argument.

## Part 3 - 10 points
- [ ] Answer all six prompts in report/REPORT.md using specific group evidence.

## Submission
- [ ] All TODOs completed; compile and execute every documented command from a clean checkout.
- [ ] Code, scripts, reproducible seeds/configs, raw and summarized machine-readable results.
- [ ] Approximately 6-8 page engineering report with readable figures/tables and citations as needed.
- [ ] Contribution statement and accurate AI-use disclosure with independent validation details.
- [ ] Check the course submission system for authoritative deadline and packaging instructions.
