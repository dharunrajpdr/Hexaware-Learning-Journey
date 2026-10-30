
public class Main {

    public static void main(String[] args) {

        // TASK 1: BINARY SEARCH TREE
        System.out.println("==================================");
        System.out.println("1. PRODUCT CATEGORY BST");
        System.out.println("==================================");

        ProductCategoryBST tree = new ProductCategoryBST();

        String[] categories = {
            "Electronics", "Clothing", "Books",
            "Furniture", "Groceries", "Mobiles",
            "Laptops", "Shoes", "Watches",
            "Beauty", "Sports", "Toys",
            "Jewellery", "Appliances", "Accessories"
        };

        // Insert 15 categories
        for (String category : categories) {
            tree.insertCategory(category);
        }

        // Display traversals
        System.out.print("Inorder: ");
        tree.inorder();

        System.out.print("\nPreorder: ");
        tree.preorder();

        System.out.print("\nPostorder: ");
        tree.postorder();

        // Search category
        System.out.println("\nSearch Electronics: " +
            tree.searchCategory("Electronics"));


        // TASK 2: HASHING AND HASHMAP
        System.out.println("\n\n==================================");
        System.out.println("2. SKU LOOKUP");
        System.out.println("==================================");

        SKUHashMapComparison.run();


        // TASK 3: GRAPH
        System.out.println("\n==================================");
        System.out.println("3. MICROSERVICE DEPENDENCIES");
        System.out.println("==================================");

        MicroserviceGraph graph = new MicroserviceGraph();

        graph.addDependency("Inventory", "Pricing");
        graph.addDependency("Pricing", "Cart");
        graph.addDependency("Cart", "Order");
        graph.addDependency("Inventory", "Payment");
        graph.addDependency("Payment", "Order");

        graph.bfsDeploymentOrder();

        graph.dfsDeploymentOrder();

        graph.demonstrateCycle();


        // TASK 4: SLIDING WINDOW
        System.out.println("\n==================================");
        System.out.println("4. PEAK TRAFFIC ANALYSIS");
        System.out.println("==================================");

        int[] requests = new int[1440];

        // Generate sample traffic
        for (int i = 0; i < requests.length; i++) {

            requests[i] = 100 + (i % 30);

            // Peak traffic between 9 AM and 12 PM
            if (i >= 540 && i < 720) {
                requests[i] += 500;
            }
        }

        TrafficAnalysis.findBusiestWindow(requests);


        // TASK 5: TWO POINTERS
        System.out.println("\n==================================");
        System.out.println("5. GIFT CARD PAIR FINDER");
        System.out.println("==================================");

        int[] orders = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100
        };

        int giftCardValue = 100;

        GiftCardPairFinder.findPairs(orders, giftCardValue);


        // PROJECT COMPLETION
        System.out.println("\n==================================");
        System.out.println("ALL TASKS COMPLETED SUCCESSFULLY");
        System.out.println("==================================");
    }
}
