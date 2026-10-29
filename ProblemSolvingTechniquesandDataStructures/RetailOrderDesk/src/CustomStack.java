import java.util.ArrayList;
import java.util.EmptyStackException;

public class CustomStack<T> {
    private final ArrayList<T> items = new ArrayList<>();

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return items.remove(items.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public static void main(String[] args) {
        CustomStack<String> undoStack = new CustomStack<>();

        undoStack.push("Change address");
        undoStack.push("Update quantity");
        undoStack.push("Apply discount");

        System.out.println("Undo: " + undoStack.pop());
        System.out.println("Latest remaining action: "
                + undoStack.peek());
    }
}