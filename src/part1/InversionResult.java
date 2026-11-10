package part1;

/** Count uses long because inversion counts can exceed the int range. */
public record InversionResult(long count, int[] sorted) {
    public InversionResult { sorted = sorted.clone(); }
    @Override public int[] sorted() { return sorted.clone(); }
}
