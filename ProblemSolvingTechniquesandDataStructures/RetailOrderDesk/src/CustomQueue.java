import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CustomQueue<T> {
    private final LinkedList<T> items = new LinkedList<>();

    public void enqueue(T item) {
        items.addLast(item);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items.removeFirst();
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items.getFirst();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public static void main(String[] args) {
        CustomQueue<Integer> dispatchQueue = new CustomQueue<>();

        dispatchQueue.enqueue(101);
        dispatchQueue.enqueue(102);
        dispatchQueue.enqueue(103);

        System.out.println("Dispatch Order: "
                + dispatchQueue.dequeue());
        System.out.println("Next Order: " + dispatchQueue.peek());
    }
}