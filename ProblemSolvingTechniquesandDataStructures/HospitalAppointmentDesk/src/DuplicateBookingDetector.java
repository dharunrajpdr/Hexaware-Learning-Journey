import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class DuplicateBookingDetector {

    static boolean detectDuplicates(Appointment[] appointments) {
        Set<String> bookings = new HashSet<>();
        boolean foundDuplicate = false;

        for (Appointment a : appointments) {
            String key = a.getPatientId() + "|"
                    + a.getDate() + "|"
                    + a.getStartTime();

            if (!bookings.add(key)) {
                System.out.println("Duplicate booking: " + a);
                foundDuplicate = true;
            } else {
                System.out.println("Booking accepted: " + a);
            }
        }

        if (!foundDuplicate) {
            System.out.println("No duplicate bookings found.");
        }

        return foundDuplicate;
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2026, 10, 29);

        Appointment[] appointments = {
            new Appointment(101, "Arun", 2,
                LocalDateTime.of(date, LocalTime.of(9, 0)),
                date, LocalTime.of(10, 0), LocalTime.of(10, 20)),

            new Appointment(102, "Bala", 1,
                LocalDateTime.of(date, LocalTime.of(9, 5)),
                date, LocalTime.of(10, 30), LocalTime.of(10, 50)),

            new Appointment(101, "Arun", 2,
                LocalDateTime.of(date, LocalTime.of(9, 10)),
                date, LocalTime.of(10, 0), LocalTime.of(10, 20)),

            new Appointment(101, "Arun", 2,
                LocalDateTime.of(date, LocalTime.of(9, 15)),
                date, LocalTime.of(11, 0), LocalTime.of(11, 20))
        };

        detectDuplicates(appointments);
    }
}