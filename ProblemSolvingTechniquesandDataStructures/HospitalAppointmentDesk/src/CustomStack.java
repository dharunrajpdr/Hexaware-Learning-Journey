public class CustomStack {
    private int[] stack;
    private int top;

    public CustomStack(int capacity) {
        stack = new int[Math.max(1, capacity)];
        top = -1;
    }

    public void push(int patientId) {
        if (top == stack.length - 1) {
            resize();
        }

        stack[++top] = patientId;
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return stack[top--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    private void resize() {
        int[] newStack = new int[stack.length * 2];

        for (int i = 0; i <= top; i++) {
            newStack[i] = stack[i];
        }

        stack = newStack;
    }

    public static void main(String[] args) {
        CustomStack stack = new CustomStack(2);

        stack.push(101);
        stack.push(102);
        stack.push(103);

        System.out.println("Recently viewed: " + stack.peek());
        System.out.println("Removed patient: " + stack.pop());
        System.out.println("Removed patient: " + stack.pop());
        System.out.println("Stack empty: " + stack.isEmpty());
    }
}