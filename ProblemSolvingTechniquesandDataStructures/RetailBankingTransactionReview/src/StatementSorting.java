import java.time.LocalDateTime;

public class StatementSorting {

    public static void insertionSort(Transaction[] transactions) {
        for (int i = 1; i < transactions.length; i++) {
            Transaction key = transactions[i];
            int j = i - 1;

            while (j >= 0
                    && transactions[j].getPostingDate()
                    .isAfter(key.getPostingDate())) {
                transactions[j + 1] = transactions[j];
                j--;
            }

            transactions[j + 1] = key;
        }
    }

    public static void mergeSort(Transaction[] transactions) {
        if (transactions.length <= 1) {
            return;
        }

        Transaction[] temp = new Transaction[transactions.length];
        mergeSort(transactions, temp, 0, transactions.length - 1);
    }

    private static void mergeSort(Transaction[] arr, Transaction[] temp,
                                  int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    private static void merge(Transaction[] arr, Transaction[] temp,
                              int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            // <= preserves the original order of equal timestamps.
            if (!arr[i].getPostingDate()
                    .isAfter(arr[j].getPostingDate())) {
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

        for (int index = left; index <= right; index++) {
            arr[index] = temp[index];
        }
    }

    public static void main(String[] args) {
        Transaction[] sample = {
            new Transaction(103, "ACC01", 500, "StoreC",
                    LocalDateTime.parse("2026-10-29T10:30:00")),
            new Transaction(101, "ACC01", 200, "StoreA",
                    LocalDateTime.parse("2026-10-29T10:15:00")),
            new Transaction(102, "ACC01", 300, "StoreB",
                    LocalDateTime.parse("2026-10-29T10:15:00"))
        };

        Transaction[] insertionData = sample.clone();
        Transaction[] mergeData = sample.clone();

        insertionSort(insertionData);
        mergeSort(mergeData);

        System.out.println("Insertion Sort:");
        for (Transaction t : insertionData) {
            System.out.println(t);
        }

        System.out.println("\nMerge Sort:");
        for (Transaction t : mergeData) {
            System.out.println(t);
        }
    }
}