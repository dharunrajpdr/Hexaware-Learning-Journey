
public class TrafficAnalysis {

    public static void findBusiestWindow(int[] requests) {

        int windowSize = 180;

        if (requests == null || requests.length != 1440) {
            throw new IllegalArgumentException(
                "Traffic data must contain exactly 1440 minutes."
            );
        }

        // Calculate first 3-hour window
        int windowSum = 0;

        for (int i = 0; i < windowSize; i++) {
            windowSum += requests[i];
        }

        int maxSum = windowSum;
        int maxStart = 0;

        // Sliding Window
        for (int i = windowSize; i < requests.length; i++) {

            windowSum += requests[i];
            windowSum -= requests[i - windowSize];

            if (windowSum > maxSum) {

                maxSum = windowSum;

                maxStart = i - windowSize + 1;
            }
        }

        // Convert minutes into hours and minutes
        int startHour = maxStart / 60;
        int startMinute = maxStart % 60;

        int endTime = maxStart + windowSize;

        int endHour = endTime / 60;
        int endMinute = endTime % 60;

        System.out.println("\nPEAK TRAFFIC ANALYSIS");

        System.out.printf(
            "Busiest Window: %02d:%02d - %02d:%02d%n",
            startHour, startMinute, endHour, endMinute
        );

        System.out.println("Total Requests: " + maxSum);

        System.out.println("Duration: 3 Hours");
    }
}
