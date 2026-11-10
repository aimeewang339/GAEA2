package common;

/** A safety cutoff is not proof that an instance is impossible. */
public final class SearchLimitExceeded extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SearchLimitExceeded(String reason) { super(reason); }
}
