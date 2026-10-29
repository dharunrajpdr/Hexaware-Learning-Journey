public class TransactionSearch {

    static class SearchResult {
        int index;
        int comparisons;

        SearchResult(int index, int comparisons) {
            this.index = index;
            this.comparisons = comparisons;
        }
    }

    public static SearchResult linearSearch(int[] ids, int target) {
        int comparisons = 0;

        for (int i = 0; i < ids.length; i++) {
            comparisons++;

            if (ids[i] == target) {
                return new SearchResult(i, comparisons);
            }
        }

        return new SearchResult(-1, comparisons);
    }

    public static SearchResult binarySearch(int[] ids, int target) {
        int left = 0;
        int right = ids.length - 1;
        int comparisons = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            comparisons++;

            if (ids[mid] == target) {
                return new SearchResult(mid, comparisons);
            } else if (ids[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new SearchResult(-1, comparisons);
    }

    public static void main(String[] args) {
        int[] ids = new int[100000];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = 100001 + i;
        }

        int target = ids[ids.length / 2];

        SearchResult linear = linearSearch(ids, target);
        SearchResult binary = binarySearch(ids, target);

        System.out.println("Transaction ID: " + target);
        System.out.println("Linear Search Index: " + linear.index);
        System.out.println("Linear Comparisons: " + linear.comparisons);
        System.out.println("Binary Search Index: " + binary.index);
        System.out.println("Binary Comparisons: " + binary.comparisons);

        System.out.println("\nBinary Search best case: 1 comparison");
        System.out.println("Binary Search worst-case bound: "
                + (int) Math.ceil(
                        Math.log(ids.length + 1) / Math.log(2)));

        System.out.println("Linear Search best case: 1 comparison");
        System.out.println("Linear Search worst case: "
                + ids.length + " comparisons");
    }
}