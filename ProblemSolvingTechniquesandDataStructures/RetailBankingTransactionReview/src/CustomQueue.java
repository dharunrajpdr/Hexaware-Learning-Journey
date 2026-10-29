public class CustomQueue<T> {
    private Object[] items;
    private int front;
    private int size;

    public CustomQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        items = new Object[capacity];
        front = 0;
        size = 0;
    }

    public void enqueue(T item) {
        if (size == items.length) {
            resize();
        }

        int rear = (front + size) % items.length;
        items[rear] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        T item = (T) items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        size--;

        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return (T) items[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Object[] expanded = new Object[items.length * 2];

        for (int i = 0; i < size; i++) {
            expanded[i] = items[(front + i) % items.length];
        }

        items = expanded;
        front = 0;
    }

    public static void main(String[] args) {
        CustomQueue<Integer> reviewQueue = new CustomQueue<>(2);

        reviewQueue.enqueue(301);
        reviewQueue.enqueue(302);
        reviewQueue.enqueue(303);

        System.out.println("Next for review: " + reviewQueue.peek());
        System.out.println("Reviewed: " + reviewQueue.dequeue());
        System.out.println("Reviewed: " + reviewQueue.dequeue());
        System.out.println("Remaining: " + reviewQueue.size());
    }
}