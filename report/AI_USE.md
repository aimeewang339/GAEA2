# AI use statement - update before submission

OpenAI Codex was used to read the supplied assignment and create this Java starter
structure: interfaces/result types, TODO method stubs, command dispatcher, initial
configuration constants, editor setup, output schemas, and documentation outlines.
In a later request, Codex implemented Part 1 validation, the random permutation
generator, the runtime experiment, and harness regression tests. It did not implement
the original inversion counters or coin-change solvers. Harness tests use a
library-sort test double; the real solvers are checked through the validation commands.

AI output is not evidence of correctness. Compilation and checking the starter's
unfinished-state behavior do not validate the future algorithms.

TODO (group): record any later AI tools/prompts and the specific assistance received.
TODO (group): explain independent validation actually performed, including hand
derivations, baseline comparisons, reconstruction checks, review, and experiment audits.
TODO (group): verify all generated interfaces, comments, schemas, and requirement mappings.

OpenAI Codex implemented Part 2B's hand/random validation, independent solution checker, and automated greedy counterexample search. It ran 10 hand cases, 100 seeded random cases, and 252 search cases, and drafted report/PART2B.md from the resulting output. The group should independently review the hand derivations and recorded evidence.

During final review, Codex corrected the right-half merge loop, moved solution validation outside memoization timing, removed the Git subprocess, and ran the complete project. Fresh outputs are in results/review.
