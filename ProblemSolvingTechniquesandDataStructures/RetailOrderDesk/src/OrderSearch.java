import java.util.Arrays;
import java.util.Comparator;

public class OrderSearch {

    public static int linearSearch(int[] orders, int target) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] orders, int target) {
        int left = 0;
        int right = orders.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (orders[mid] == target) {
                return mid;
            } else if (orders[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] orders = {105, 101, 104, 102, 103};
        int target = 104;

        int linearIndex = linearSearch(orders, target);
        System.out.println("Linear Search Index: " + linearIndex);

        Arrays.sort(orders);
        int binaryIndex = binarySearch(orders, target);
        System.out.println("Sorted Orders: " + Arrays.toString(orders));
        System.out.println("Binary Search Index: " + binaryIndex);
    }
}