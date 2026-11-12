# Part 2B - validation and greedy counterexamples

Run from the repository root using JDK 21 or newer:

```sh
javac --release 21 -d out "@sources.txt"
java -cp out part2.Validation
java -cp out part2.CounterexampleSearch results/part2b
```

## Exact-solver cross-validation (4 points)

The validation follows Part 1's HandCase, Summary, check, and require structure.
Each case runs naive recursion, memoized DP, bottom-up DP, and greedy. All three
exact solvers must agree on optimal count or IMPOSSIBLE. Hand expectations are
checked separately against each exact solver. Optimal coin lists may differ.

| D | A | Expected optimum | Hand justification |
| --- | --- | --- | --- |
| {1,3,4} | 0 | 0 | Empty list sums to zero. |
| {2,4} | 3 | IMPOSSIBLE | Every sum is even. |
| {5} | 10 | 2 | 5+5, and one coin is too small. |
| {5} | 3 | IMPOSSIBLE | Smallest coin exceeds the target. |
| {1,3,4} | 6 | 2 | 3+3, and no denomination equals 6. |
| {1,2,3} | 4 | 2 | Both 1+3 and 2+2 are optimal. No coin equals 4. |
| {4,1,3} | 6 | 2 | 3+3 also tests arbitrary input order. |
| {1,5,10} | 10 | 1 | One 10 coin attains the positive-target lower bound. |
| {2,3} | 7 | 3 | 2+2+3. Two coins sum to at most 6. |
| {3,4} | 6 | 2 | 3+3. Greedy takes 4 and fails on remainder 2. |

The recorded run passed all 10 hand cases and 100 random cases with seed 40502026.
Random cases use 1-4 unique denominations and targets uniformly drawn from 0-12.
Eighty cases include denomination 1. Every fifth case deliberately excludes 1,
using denominations 2-8 to exercise systems that can have unreachable targets.
The generated denomination order is retained. These bounds keep naive recursion
practical. All three exact solvers completed every case without a cutoff.

## Independent solution validator (2 points)

SolutionValidator checks nonnegative count, list length equal to count, positive
coins belonging to D, and a long-integer sum equal to A. It checks every returned
solution including greedy, independently of solver reconstruction. Validation also
checks that each solver preserves its input. IMPOSSIBLE is compared across exact
solvers and with hand expectations. Greedy is allowed to fail on feasible targets.
Failures include the case, solver, input, target, and expected/actual discrepancy.
Finite testing provides implementation evidence, not a general correctness proof.

## Automated search (4 points)

Before using DP as the oracle, the search runs the validation suite. It then
enumerates all 21 systems {1,a,b} with 2 <= a < b <= 8 and targets 1-12, for
252 additional exact-solver cross-checks. Every returned solution is independently
checked. A counterexample must have a feasible greedy solution with strictly more
coins than the exact optimum. Each (D,A) pair is visited once. The search asserts
at least three distinct coin systems and detection of the required {1,3,4}, A=6 case.
No randomness is used in the exhaustive search.

## Counterexamples and interpretation (5 points)

The run found 14 counterexamples across eight distinct systems. Three examples are:

| D | A | Greedy | Optimum |
| --- | --- | --- | --- |
| {1,3,4} | 6 | 4+1+1 (3 coins) | 3+3 (2 coins) |
| {1,4,5} | 8 | 5+1+1+1 (4 coins) | 4+4 (2 coins) |
| {1,4,6} | 8 | 6+1+1 (3 coins) | 4+4 (2 coins) |

For {1,3,4} and target 6, choosing 4 leaves remainder 2. Only two 1 coins can
complete that choice, requiring three coins total. Choosing 3 instead leaves
another 3 and finishes in two coins. One coin cannot reach 6, so two is optimal.
The largest local choice prevents using the better pair of medium denominations.
The same structure appears when choosing 5 or 6 blocks the pair 4+4 for target 8.

Machine-readable evidence is saved in results/part2b/part2b_validation.csv and
results/part2b/greedy_counterexamples.csv. The validation total counts complete
passing hand/random cases. The exhaustive search's 252 cases are additional.
The search rewrites these two files when rerun with the same output directory.
