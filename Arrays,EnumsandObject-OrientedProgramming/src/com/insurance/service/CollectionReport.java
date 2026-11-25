
package com.insurance.service;

public class CollectionReport {

    // 2D array: Equal number of branches per region
    public void displayRegularCollections() {

        double[][] collections = {
            {10000, 15000, 12000},
            {20000, 18000, 22000},
            {14000, 16000, 13000}
        };

        System.out.println("\n===== REGIONAL COLLECTIONS (2D ARRAY) =====");

        double grandTotal = 0;

        for (int i = 0; i < collections.length; i++) {

            double regionTotal = 0;

            for (int j = 0; j < collections[i].length; j++) {

                regionTotal += collections[i][j];
            }

            grandTotal += regionTotal;

            System.out.printf(
                    "Region %d Total: ₹%.2f%n",
                    i + 1,
                    regionTotal
            );
        }

        System.out.printf("Grand Total: ₹%.2f%n", grandTotal);
    }

    // Jagged array: Different number of branches per region
    public void displayJaggedCollections() {

        double[][] collections = {
            {10000, 15000},
            {20000, 18000, 22000},
            {14000, 16000, 13000, 11000}
        };

        System.out.println("\n===== REGIONAL COLLECTIONS (JAGGED ARRAY) =====");

        double grandTotal = 0;

        for (int i = 0; i < collections.length; i++) {

            double regionTotal = 0;

            for (int j = 0; j < collections[i].length; j++) {

                regionTotal += collections[i][j];
            }

            grandTotal += regionTotal;

            System.out.printf(
                    "Region %d (%d branches): ₹%.2f%n",
                    i + 1,
                    collections[i].length,
                    regionTotal
            );
        }

        System.out.printf("Grand Total: ₹%.2f%n", grandTotal);
    }
}
