package part2;
import java.util.List;

/** Common result: Solution(count, coins) or the explicit IMPOSSIBLE sentinel. */
public sealed interface CoinResult permits CoinResult.Solution, CoinResult.Impossible {
    record Solution(int coinCount, List<Integer> chosenCoins) implements CoinResult {
        public Solution { chosenCoins = List.copyOf(chosenCoins); }
    }
    enum Impossible implements CoinResult { INSTANCE }
    CoinResult IMPOSSIBLE = Impossible.INSTANCE;
}
