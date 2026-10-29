import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentSorting {

    static int selectionSwaps;
    static int quickSwaps;

    static int compare(Appointment a, Appointment b) {
        if (a.getUrgency() != b.getUrgency()) {
            return Integer.compare(a.getUrgency(), b.getUrgency());
        }

        return a.getArrivalTime().compareTo(b.getArrivalTime());
    }

    static void selectionSort(Appointment[] arr) {
        selectionSwaps = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }

            if (min != i) {
                Appointment temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
                selectionSwaps++;
            }
        }
    }

    static void quickSort(Appointment[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    static int partition(Appointment[] arr, int low, int high) {
        Appointment pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(Appointment[] arr, int i, int j) {
        if (i != j) {
            Appointment temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            quickSwaps++;
        }
    }

    static Appointment[] sampleAppointments() {
        LocalDate date = LocalDate.of(2026, 10, 29);

        return new Appointment[] {
            new Appointment(101, "Arun", 3,
                LocalDateTime.of(date, LocalTime.of(9, 15)),
                date, LocalTime.of(10, 0), LocalTime.of(10, 20)),

            new Appointment(102, "Bala", 1,
                LocalDateTime.of(date, LocalTime.of(9, 5)),
                date, LocalTime.of(9, 30), LocalTime.of(9, 50)),

            new Appointment(103, "Cathy", 2,
                LocalDateTime.of(date, LocalTime.of(9, 10)),
                date, LocalTime.of(10, 30), LocalTime.of(10, 50)),

            new Appointment(104, "Deepa", 1,
                LocalDateTime.of(date, LocalTime.of(9, 2)),
                date, LocalTime.of(9, 0), LocalTime.of(9, 20))
        };
    }

    static void display(Appointment[] arr) {
        for (Appointment a : arr) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        Appointment[] selectionData = sampleAppointments();
        Appointment[] quickData = sampleAppointments();

        long start = System.nanoTime();
        selectionSort(selectionData);
        long selectionTime = System.nanoTime() - start;

        quickSwaps = 0;
        start = System.nanoTime();
        quickSort(quickData, 0, quickData.length - 1);
        long quickTime = System.nanoTime() - start;

        System.out.println("Selection Sort Results:");
        display(selectionData);
        System.out.println("Swaps: " + selectionSwaps);
        System.out.println("Time: " + selectionTime + " ns");

        System.out.println("\nQuick Sort Results:");
        display(quickData);
        System.out.println("Swaps: " + quickSwaps);
        System.out.println("Time: " + quickTime + " ns");
    }
}