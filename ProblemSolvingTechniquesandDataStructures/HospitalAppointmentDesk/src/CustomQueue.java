public class CustomQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int size;

    public CustomQueue(int capacity) {
        queue = new int[Math.max(1, capacity)];
        front = 0;
        rear = 0;
        size = 0;
    }

    public void enqueue(int token) {
        if (size == queue.length) {
            resize();
        }

        queue[rear] = token;
        rear = (rear + 1) % queue.length;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        int token = queue[front];
        front = (front + 1) % queue.length;
        size--;

        return token;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return queue[front];
    }

    private void resize() {
        int[] newQueue = new int[queue.length * 2];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }

        queue = newQueue;
        front = 0;
        rear = size;
    }

    public static void main(String[] args) {
        CustomQueue queue = new CustomQueue(3);

        queue.enqueue(101);
        queue.enqueue(102);
        queue.enqueue(103);
        queue.enqueue(104);

        System.out.println("Next token: " + queue.peek());
        System.out.println("Served token: " + queue.dequeue());
        System.out.println("Served token: " + queue.dequeue());
        System.out.println("Queue empty: " + queue.isEmpty());
    }
}