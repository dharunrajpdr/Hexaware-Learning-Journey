import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private int patientId;
    private String patientName;
    private int urgency;
    private LocalDateTime arrivalTime;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Appointment(int patientId, String patientName, int urgency,
                       LocalDateTime arrivalTime, LocalDate date,
                       LocalTime startTime, LocalTime endTime) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.urgency = urgency;
        this.arrivalTime = arrivalTime;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getUrgency() {
        return urgency;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Patient=" + patientName
                + ", ID=" + patientId
                + ", Urgency=" + urgency
                + ", Arrival=" + arrivalTime.toLocalTime()
                + ", Slot=" + startTime + "-" + endTime;
    }
}