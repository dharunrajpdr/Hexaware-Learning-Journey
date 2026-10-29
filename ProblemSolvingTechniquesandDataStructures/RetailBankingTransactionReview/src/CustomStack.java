public class CustomStack<T> {
    private Object[] items;
    private int size;

    public CustomStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        items = new Object[capacity];
        size = 0;
    }

    public void push(T item) {
        if (size == items.length) {
            resize();
        }

        items[size++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        T item = (T) items[--size];
        items[size] = null;

        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return (T) items[size - 1];
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
            expanded[i] = items[i];
        }

        items = expanded;
    }

    public static void main(String[] args) {
        CustomStack<String> adjustments = new CustomStack<>(2);

        adjustments.push("Adjustment A: +100");
        adjustments.push("Adjustment B: -50");
        adjustments.push("Adjustment C: +25");

        System.out.println("Last adjustment to reverse: "
                + adjustments.pop());
        System.out.println("Next adjustment: " + adjustments.peek());
    }
}