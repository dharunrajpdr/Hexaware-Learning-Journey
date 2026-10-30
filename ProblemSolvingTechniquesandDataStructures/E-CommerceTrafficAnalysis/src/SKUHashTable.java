
public class SKUHashTable {

    private class Node {
        String sku;
        String product;
        Node next;

        Node(String sku, String product) {
            this.sku = sku;
            this.product = product;
        }
    }

    private Node[] table;
    private int capacity;

    public SKUHashTable(int capacity) {
        this.capacity = capacity;
        table = new Node[capacity];
    }

    private int hash(String sku) {
        return Math.floorMod(sku.hashCode(), capacity);
    }

    public void put(String sku, String product) {

        int index = hash(sku);

        Node current = table[index];

        while (current != null) {

            if (current.sku.equals(sku)) {
                current.product = product;
                return;
            }

            current = current.next;
        }

        Node newNode = new Node(sku, product);

        newNode.next = table[index];
        table[index] = newNode;
    }

    public String get(String sku) {

        int index = hash(sku);

        Node current = table[index];

        while (current != null) {

            if (current.sku.equals(sku)) {
                return current.product;
            }

            current = current.next;
        }

        return null;
    }

    public void displayBucket(int index) {

        Node current = table[index];

        while (current != null) {
            System.out.print(current.sku + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }

    public int getBucketIndex(String sku) {
        return hash(sku);
    }
}
