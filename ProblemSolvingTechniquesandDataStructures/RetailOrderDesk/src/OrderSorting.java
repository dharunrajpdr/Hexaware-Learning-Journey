import java.util.Arrays;
import java.util.Comparator;

public class OrderSorting {

    public static void bubbleSort(double[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (i < 3) {
                System.out.println("Pass " + (i + 1) + ": "
                        + Arrays.toString(arr));
            }
        }
    }

    public static void mergeSort(double[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(double[] arr, int left, int mid, int right) {
        double[] temp = new double[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] >= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    public static void main(String[] args) {
        double[] amounts = {2500, 1200, 4500, 800, 3200};

        double[] bubbleData = amounts.clone();
        System.out.println("Bubble Sort:");
        bubbleSort(bubbleData);
        System.out.println("Result: " + Arrays.toString(bubbleData));

        double[] mergeData = amounts.clone();
        mergeSort(mergeData, 0, mergeData.length - 1);
        System.out.println("\nMerge Sort: " + Arrays.toString(mergeData));

        Double[] builtInData = Arrays.stream(amounts)
                .boxed()
                .toArray(Double[]::new);

        Arrays.sort(builtInData, Comparator.reverseOrder());
        System.out.println("Built-in Sort: " + Arrays.toString(builtInData));
    }
}