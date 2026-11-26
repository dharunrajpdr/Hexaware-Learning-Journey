
package com.crm.utility;

public final class StringUtil {

    // Static final constant
    public static final String COUNTRY_CODE = "+91";

    private StringUtil() {
        // Prevent object creation
    }

    // Remove leading and trailing spaces
    public static String clean(String value) {

        return value.trim();
    }

    // Convert email to lowercase
    public static String normalize(String email) {

        return email.trim().toLowerCase();
    }

    // Split full name
    public static String[] splitName(String fullName) {

        return fullName.trim().split("\\s+");
    }

    // Mask phone number
    public static String maskPhone(String phone) {

        String cleaned = phone.replaceAll("\\s+", "");

        if (cleaned.length() < 4) {
            throw new IllegalArgumentException(
                    "Phone number must contain at least 4 digits."
            );
        }

        return "*".repeat(cleaned.length() - 4)
                + cleaned.substring(cleaned.length() - 4);
    }

    // Demonstrate String immutability
    public static void demonstrateImmutability() {

        String original = "  Java Programming  ";

        String cleaned = original.trim();

        System.out.println("Original String : [" + original + "]");
        System.out.println("Cleaned String  : [" + cleaned + "]");
    }

    // Demonstrate String constant pool
    public static void demonstrateStringPool() {

        String first = "Java";
        String second = "Java";

        String third = new String("Java");

        System.out.println("first == second       : " + (first == second));
        System.out.println("first == third        : " + (first == third));
        System.out.println("first.equals(third)   : " + first.equals(third));
    }
}
