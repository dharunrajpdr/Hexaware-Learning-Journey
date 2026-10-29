import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public class AppointmentScheduler {

    static class Slot {
        LocalTime start;
        LocalTime end;

        Slot(String start, String end) {
            this.start = LocalTime.parse(start);
            this.end = LocalTime.parse(end);

            if (!this.start.isBefore(this.end)) {
                throw new IllegalArgumentException(
                    "Appointment end must be after start");
            }
        }

        @Override
        public String toString() {
            return start + "-" + end;
        }
    }

    static Slot[] sortByEnd(Slot[] appointments) {
        Slot[] sorted = appointments.clone();

        Arrays.sort(sorted, Comparator.comparing(a -> a.end));

        return sorted;
    }

    // Greedy: select appointments by earliest finishing time.
    static int greedy(Slot[] appointments) {
        Slot[] sorted = sortByEnd(appointments);

        int count = 0;
        LocalTime lastEnd = null;

        for (Slot appointment : sorted) {
            if (lastEnd == null
                    || !appointment.start.isBefore(lastEnd)) {
                count++;
                lastEnd = appointment.end;
            }
        }

        return count;
    }

    // Find the last appointment ending before or when
    // the current appointment starts.
    static int findPrevious(Slot[] sorted, int current) {
        int low = 0;
        int high = current - 1;
        int answer = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (!sorted[mid].end.isAfter(sorted[current].start)) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    // Dynamic Programming: maximum number of compatible appointments.
    static int dynamicProgramming(Slot[] appointments) {
        Slot[] sorted = sortByEnd(appointments);
        int n = sorted.length;

        if (n == 0) {
            return 0;
        }

        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            int include = 1;
            int previous = findPrevious(sorted, i);

            if (previous != -1) {
                include += dp[previous];
            }

            int exclude = (i == 0) ? 0 : dp[i - 1];

            dp[i] = Math.max(include, exclude);
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Slot[] appointments = {
            new Slot("09:00", "10:00"),
            new Slot("09:30", "10:30"),
            new Slot("10:00", "11:00"),
            new Slot("11:00", "11:30"),
            new Slot("10:30", "12:00"),
            new Slot("12:00", "13:00")
        };

        long start = System.nanoTime();
        int greedyResult = greedy(appointments);
        long greedyTime = System.nanoTime() - start;

        start = System.nanoTime();
        int dpResult = dynamicProgramming(appointments);
        long dpTime = System.nanoTime() - start;

        System.out.println("Total appointments: " + appointments.length);
        System.out.println("Greedy maximum: " + greedyResult);
        System.out.println("Greedy time: " + greedyTime + " ns");

        System.out.println("DP maximum: " + dpResult);
        System.out.println("DP time: " + dpTime + " ns");
    }
}