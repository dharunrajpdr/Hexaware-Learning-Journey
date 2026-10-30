
import java.util.*;

public class MicroserviceGraph {

    private Map<String, List<String>> graph;

    public MicroserviceGraph() {
        graph = new LinkedHashMap<>();
    }

    // Add service
    public void addService(String service) {
        graph.putIfAbsent(service, new ArrayList<>());
    }

    // Add dependency: dependency -> dependent service
    public void addDependency(String dependency, String service) {

        addService(dependency);
        addService(service);

        graph.get(dependency).add(service);
    }

    // BFS Topological Sort (Kahn's Algorithm)
    public void bfsDeploymentOrder() {

        Map<String, Integer> indegree = new HashMap<>();

        for (String service : graph.keySet()) {
            indegree.put(service, 0);
        }

        for (String service : graph.keySet()) {

            for (String dependent : graph.get(service)) {
                indegree.put(
                    dependent,
                    indegree.get(dependent) + 1
                );
            }
        }

        Queue<String> queue = new LinkedList<>();

        for (String service : graph.keySet()) {

            if (indegree.get(service) == 0) {
                queue.offer(service);
            }
        }

        List<String> order = new ArrayList<>();

        while (!queue.isEmpty()) {

            String current = queue.poll();

            order.add(current);

            for (String dependent : graph.get(current)) {

                indegree.put(
                    dependent,
                    indegree.get(dependent) - 1
                );

                if (indegree.get(dependent) == 0) {
                    queue.offer(dependent);
                }
            }
        }

        if (order.size() != graph.size()) {
            System.out.println(
                "BFS: Circular dependency detected!"
            );
        } else {
            System.out.println(
                "BFS Deployment Order: " + order
            );
        }
    }

    // DFS Topological Sort
    private boolean dfs(
        String node,
        Map<String, Integer> state,
        Stack<String> stack
    ) {

        state.put(node, 1);

        for (String next : graph.get(node)) {

            if (state.get(next) == 1) {
                return false;
            }

            if (state.get(next) == 0) {

                if (!dfs(next, state, stack)) {
                    return false;
                }
            }
        }

        state.put(node, 2);

        stack.push(node);

        return true;
    }

    public void dfsDeploymentOrder() {

        Map<String, Integer> state = new HashMap<>();

        for (String service : graph.keySet()) {
            state.put(service, 0);
        }

        Stack<String> stack = new Stack<>();

        for (String service : graph.keySet()) {

            if (state.get(service) == 0) {

                if (!dfs(service, state, stack)) {

                    System.out.println(
                        "DFS: Circular dependency detected!"
                    );

                    return;
                }
            }
        }

        System.out.print("DFS Deployment Order: ");

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }

        System.out.println();
    }

    // Demonstrate circular dependency
    public void demonstrateCycle() {

        System.out.println("\nCIRCULAR DEPENDENCY TEST");

        // Order depends on Inventory, creating a cycle
        addDependency("Order", "Inventory");

        bfsDeploymentOrder();
        dfsDeploymentOrder();
    }
}
