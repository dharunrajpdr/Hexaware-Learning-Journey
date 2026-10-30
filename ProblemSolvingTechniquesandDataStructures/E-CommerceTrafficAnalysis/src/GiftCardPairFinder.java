
public class GiftCardPairFinder {

    public static void findPairs(int[] orders, int target) {

        int left = 0;
        int right = orders.length - 1;

        boolean found = false;

        System.out.println("\nGIFT CARD PAIR MATCHING");

        System.out.println("Target Amount: " + target);

        while (left < right) {

            int sum = orders[left] + orders[right];

            if (sum == target) {

                System.out.println(
                    "(" + orders[left] + ", " + orders[right] + ")"
                );

                found = true;

                left++;
                right--;

            } else if (sum < target) {

                left++;

            } else {

                right--;
            }
        }

        if (!found) {
            System.out.println("No matching pairs found.");
        }
    }
}
