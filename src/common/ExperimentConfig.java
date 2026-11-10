package common;

/** Starting settings only; document any changes and justify practical limits. */
public final class ExperimentConfig {
    private ExperimentConfig() {}
    public static final long SEED = 40502026L;
    public static final int INVERSION_RANDOM_CASES = 200;
    public static final int COIN_RANDOM_CASES = 100;
    public static final int TIMING_REPETITIONS = 7;
    public static final int WARMUP_REPETITIONS = 3;
    public static final int MAX_NAIVE_TARGET = 18;
    public static final long MAX_RECURSIVE_CALLS = 1_000_000L;
    public static final int[] INVERSION_SIZES = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
    public static final int[] RECURSION_TARGETS = {0, 3, 6, 9, 12, 15, 18};
    public static final int[] SCALING_TARGETS = {256, 512, 1024, 2048, 4096, 8192, 16384};
    public static final int[] FIXED_DENOMINATIONS = {1, 3, 4};
}
