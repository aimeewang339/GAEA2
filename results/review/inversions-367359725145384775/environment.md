# Inversion experiment environment

- Timestamp (UTC): 2026-11-13T00:20:50.097598100Z
- Java: 27+35-2325 / Java HotSpot(TM) 64-Bit Server VM
- OS: Windows 11 10.0 / amd64
- CPU: Intel64 Family 6 Model 170 Stepping 4, GenuineIntel; logical processors=22
- Command: common.Run all results/review
- Seed: 40502026; family: uniform random permutations of 0..n-1
- Sizes: [16, 32, 64, 128, 256, 512, 1024, 2048, 4096]
- Warmups per size: 3; measured repetitions: 7
- Identical independent input copies per algorithm; alternating execution order.
- Timed region: complete solver invocation (including its allocations/result construction).
- Excluded: generation, caller input cloning, validation, formatting and file I/O.
- Median: central sample, or mean of the two central samples for even counts.
- No automatic size extension: configured maximum bounds quadratic baseline work.
- checks_passed in validation.csv counts complete passing cases, not individual assertions.
- Plot summary n versus median_ns grouped by algorithm; retain raw trials.
- Interpret crossover from measured medians; short runs and JVM noise limit precision.
