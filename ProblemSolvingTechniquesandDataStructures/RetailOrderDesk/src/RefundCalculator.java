import java.util.Arrays;

public class RefundCalculator {

    public static int greedy(int[] coins, int amount) {
        Arrays.sort(coins);
        int count = 0;

        for (int i = coins.length - 1; i >= 0; i--) {
            count += amount / coins[i];
            amount %= coins[i];
        }

        return amount == 0 ? count : -1;
    }

    public static int dynamicProgramming(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int value = 1; value <= amount; value++) {
            for (int coin : coins) {
                if (coin <= value) {
                    dp[value] = Math.min(
                            dp[value],
                            dp[value - coin] + 1
                    );
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 3, 4};
        int amount = 6;

        int greedyResult = greedy(coins.clone(), amount);
        int dpResult = dynamicProgramming(coins, amount);

        System.out.println("Refund Amount: " + amount);
        System.out.println("Greedy Minimum Coins: " + greedyResult);
        System.out.println("DP Minimum Coins: " + dpResult);
    }
}