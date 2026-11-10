package common;

/** Explicit marker: an unfinished assignment must never return fabricated results. */
public final class Unfinished extends UnsupportedOperationException {
    private static final long serialVersionUID = 1L;
    public Unfinished(String task) { super("TODO: " + task); }
}
