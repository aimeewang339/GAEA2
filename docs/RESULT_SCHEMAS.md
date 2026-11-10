# Machine-readable output contracts to implement

Use UTF-8 CSV with a header. All durations are nanoseconds. Fields containing commas,
quotes, or newlines must be CSV-escaped. Use semicolons inside array/list cells.
These are proposed schemas, not measured results. Runners must create output directories.

| File | Header |
| --- | --- |
| inversion_trials.csv | seed,family,n,algorithm,trial,runtime_ns,inversion_count,status |
| inversion_summary.csv | seed,family,n,algorithm,repetitions,median_ns |
| validation.csv | part,seed,hand_cases,random_cases,checks_passed,status |
| greedy_counterexamples.csv | denominations,target,greedy_count,greedy_coins,optimal_count,optimal_coins |
| memoization_trials.csv | seed,denominations,target,algorithm,trial,recursive_calls,distinct_targets_solved,cache_hits,runtime_ns,status |
| memoization_summary.csv | denominations,target,algorithm,repetitions,median_ns,status |
| scaling_trials.csv | denominations,k,target,bit_length,dp_states,trial,runtime_ns,status |
| scaling_summary.csv | denominations,k,target,bit_length,dp_states,repetitions,median_ns |

Use status values such as `ok`, `target_cutoff`, `call_cutoff`, `depth_limit`.
Do not include truncated runs in success medians or invent values for skipped work.
For a cutoff, counters may reflect partial work; leave absent durations/results blank
and explain the convention. A cutoff does not mean an instance is impossible.
On validation failure, throw with a reproducible diagnostic and stop experiments.
Keep trials and summaries in the same run directory with an environment.md containing:

- OS, CPU, JDK version, command, date, source commit, and any uncommitted changes.
- Seed, input distributions/search bounds, sizes, denominations, warmups/repetitions.
- Safety limits, state/call/cache-hit definitions, and whether counters affect timings.
- Timing boundaries, median convention, observed limits, and plotting/table procedure.

Derive figures/tables from these actual files. Keep plot source or exact spreadsheet
instructions beside the figures so teammates can reproduce them.
