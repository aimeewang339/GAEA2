package part2;

public final class SolutionValidator {
    private SolutionValidator() {}
    public static void validate(int[] denominations, int target, CoinResult result) {
        // Feasibility of IMPOSSIBLE is checked separately against exact solvers.
        if (result == CoinResult.IMPOSSIBLE) return;
        if (!(result instanceof CoinResult.Solution solution))
            throw new AssertionError("Null or unknown solution");
        if (solution.coinCount() < 0 || solution.coinCount() != solution.chosenCoins().size())
            throw new AssertionError("Count does not match coin list");
        long sum = 0;
        for (int coin : solution.chosenCoins()) {
            boolean found = false;
            for (int denomination : denominations) {
                if (coin == denomination) found = true;
            }
            if (!found || coin <= 0) throw new AssertionError("Invalid chosen coin: " + coin);
            sum += coin;
        }
        if (sum != target) throw new AssertionError("Expected sum=" + target + ", actual=" + sum);
    }
}
