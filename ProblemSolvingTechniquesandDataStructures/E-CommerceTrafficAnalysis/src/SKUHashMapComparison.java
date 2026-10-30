
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class SKUHashMapComparison {

    private static class SKUHashTable {
        private static class Entry {
            private final String key;
            private String value;

            private Entry(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }

        private final List<Entry>[] buckets;

        @SuppressWarnings("unchecked")
        private SKUHashTable(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            buckets = (List<Entry>[]) new List<?>[capacity];
        }

        private int getBucketIndex(String key) {
            if (key == null) {
                throw new IllegalArgumentException("SKU cannot be null");
            }
            return Math.floorMod(key.hashCode(), buckets.length);
        }

        private void put(String key, String value) {
            int index = getBucketIndex(key);
            if (buckets[index] == null) {
                buckets[index] = new ArrayList<>();
            }
            for (Entry entry : buckets[index]) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
            }
            buckets[index].add(new Entry(key, value));
        }

        private String get(String key) {
            int index = getBucketIndex(key);
            if (buckets[index] != null) {
                for (Entry entry : buckets[index]) {
                    if (entry.key.equals(key)) {
                        return entry.value;
                    }
                }
            }
            return null;
        }

        private void displayBucket(int index) {
            if (index < 0 || index >= buckets.length) {
                throw new IndexOutOfBoundsException("Invalid bucket index: " + index);
            }
            if (buckets[index] != null) {
                for (Entry entry : buckets[index]) {
                    System.out.println(entry.key + " -> " + entry.value);
                }
            }
        }
    }

    public static void run() {

        // Demonstrate collision
        SKUHashTable customTable = new SKUHashTable(10);

        String sku1 = "SKU-001";
        String sku2 = "SKU-011";

        customTable.put(sku1, "Laptop");
        customTable.put(sku2, "Mobile");

        int index = customTable.getBucketIndex(sku1);

        System.out.println("CUSTOM HASH TABLE");
        System.out.println("Bucket Index: " + index);

        customTable.displayBucket(index);

        System.out.println("Collision handled using chaining.");

        // Create data structures
        int size = 100000;

        SKUHashTable custom = new SKUHashTable(200003);

        HashMap<String, String> map = new HashMap<>();

        // Insert 100,000 SKUs
        for (int i = 0; i < size; i++) {

            String sku = "SKU-" + i;
            String product = "Product-" + i;

            custom.put(sku, product);
            map.put(sku, product);
        }

        // Warm-up both implementations
        for (int i = 0; i < size; i++) {
            custom.get("SKU-" + i);
            map.get("SKU-" + i);
        }

        // Custom hash table lookup
        long start1 = System.nanoTime();

        for (int i = 0; i < size; i++) {
            custom.get("SKU-" + i);
        }

        long end1 = System.nanoTime();

        // HashMap lookup
        long start2 = System.nanoTime();

        for (int i = 0; i < size; i++) {
            map.get("SKU-" + i);
        }

        long end2 = System.nanoTime();

        double customTime = (end1 - start1) / 1_000_000.0;
        double mapTime = (end2 - start2) / 1_000_000.0;

        System.out.println("\nLOOKUP PERFORMANCE");
        System.out.println("Total SKUs: " + size);

        System.out.printf(
            "Custom Hash Table: %.3f ms%n", customTime
        );

        System.out.printf(
            "Java HashMap: %.3f ms%n", mapTime
        );

        System.out.println("\nLOOKUP VERIFICATION");

        System.out.println("Custom: " + custom.get("SKU-500"));
        System.out.println("HashMap: " + map.get("SKU-500"));
    }
}
