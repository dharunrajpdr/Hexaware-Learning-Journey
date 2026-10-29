public class PatientSearch {

    static int linearSearch(Patient[] patients, int target) {
        for (int i = 0; i < patients.length; i++) {
            if (patients[i].getPatientId() == target) {
                return i;
            }
        }
        return -1;
    }

    static int binarySearch(Patient[] patients, int target) {
        int low = 0;
        int high = patients.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int id = patients[mid].getPatientId();

            if (id == target) {
                return mid;
            } else if (id < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Patient[] patients = new Patient[20000];

        for (int i = 0; i < patients.length; i++) {
            patients[i] = new Patient(i + 1001, "Patient" + (i + 1));
        }

        int lookups = 1000;
        long start, end;

        start = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            int target = 1001 + (i * 17) % patients.length;
            linearSearch(patients, target);
        }
        end = System.nanoTime();
        long linearTime = end - start;

        start = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            int target = 1001 + (i * 17) % patients.length;
            binarySearch(patients, target);
        }
        end = System.nanoTime();
        long binaryTime = end - start;

        System.out.println("Total Patients: " + patients.length);
        System.out.println("Total Lookups: " + lookups);
        System.out.println("Linear Search: " + linearTime + " ns");
        System.out.println("Binary Search: " + binaryTime + " ns");

        int index = binarySearch(patients, 1010);
        if (index != -1) {
            System.out.println("Found: " + patients[index]);
        } else {
            System.out.println("Patient not found");
        }
    }
}