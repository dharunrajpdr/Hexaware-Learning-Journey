
public class ProductCategoryBST {

    private class Node {
        String data;
        Node left, right;

        Node(String data) {
            this.data = data;
        }
    }

    private Node root;

    // Constructor
    public ProductCategoryBST() {
        root = null;
    }

    // Insert category
    private Node insert(Node root, String data) {

        if (root == null) {
            return new Node(data);
        }

        int comparison = data.compareToIgnoreCase(root.data);

        if (comparison < 0) {
            root.left = insert(root.left, data);
        } else if (comparison > 0) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    public void insertCategory(String data) {
        root = insert(root, data);
    }

    // Search category
    private boolean search(Node root, String key) {

        if (root == null) {
            return false;
        }

        int comparison = key.compareToIgnoreCase(root.data);

        if (comparison == 0) {
            return true;
        }

        if (comparison < 0) {
            return search(root.left, key);
        }

        return search(root.right, key);
    }

    public boolean searchCategory(String key) {
        return search(root, key);
    }

    // Inorder: Left, Root, Right
    private void inorder(Node root) {

        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public void inorder() {
        inorder(root);
    }

    // Preorder: Root, Left, Right
    private void preorder(Node root) {

        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public void preorder() {
        preorder(root);
    }

    // Postorder: Left, Right, Root
    private void postorder(Node root) {

        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public void postorder() {
        postorder(root);
    }
}
