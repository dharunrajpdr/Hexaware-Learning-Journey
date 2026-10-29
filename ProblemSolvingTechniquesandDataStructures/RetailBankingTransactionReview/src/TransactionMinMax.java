public class TransactionMinMax {

    static class MinMax {
        double min;
        double max;

        MinMax(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }

    public static MinMax bruteForce(double[] amounts) {
        if (amounts == null || amounts.length == 0) {
            throw new IllegalArgumentException(
                    "Transaction amounts cannot be empty");
        }

        double min = amounts[0];
        double max = amounts[0];

        for (int i = 1; i < amounts.length; i++) {
            if (amounts[i] < min) {
                min = amounts[i];
            }

            if (amounts[i] > max) {
                max = amounts[i];
            }
        }

        return new MinMax(min, max);
    }

    public static MinMax divideAndConquer(
            double[] amounts, int left, int right) {

        if (left < 0 || right >= amounts.length || left > right) {
            throw new IllegalArgumentException("Invalid array range");
        }

        if (left == right) {
            return new MinMax(amounts[left], amounts[left]);
        }

        if (right == left + 1) {
            double a = amounts[left];
            double b = amounts[right];

            return new MinMax(
                    Math.min(a, b),
                    Math.max(a, b));
        }

        int mid = left + (right - left) / 2;

        MinMax first = divideAndConquer(amounts, left, mid);
        MinMax second = divideAndConquer(amounts, mid + 1, right);

        return new MinMax(
                Math.min(first.min, second.min),
                Math.max(first.max, second.max));
    }

    public static void main(String[] args) {
        double[] amounts = {
            250.50, 1200.00, 75.25, 980.00, 450.75, 50.00
        };

        MinMax brute = bruteForce(amounts);
        MinMax divide = divideAndConquer(
                amounts, 0, amounts.length - 1);

        System.out.println("Brute Force:");
        System.out.println("Lowest Amount: " + brute.min);
        System.out.println("Highest Amount: " + brute.max);

        System.out.println("\nDivide and Conquer:");
        System.out.println("Lowest Amount: " + divide.min);
        System.out.println("Highest Amount: " + divide.max);
    }
}