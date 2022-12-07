import java.util.*;

public sealed interface Node permits Node.LeafNode, Node.TreeNode {
    Map<String, Node> getChildren();
    void addChild(Node node);
    Node getParent();
    boolean isTerminal();
    int getSize();
    String getName();

    final class LeafNode implements Node {
        private final Node parent;
        private final int size;
        private final String name;

        public LeafNode(Node parent, int size, String name) {
            this.parent = parent;
            this.size = size;
            this.name = name;
        }

        @Override
        public Map<String, Node> getChildren() {
            return Map.of();
        }

        @Override
        public void addChild(Node node) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node getParent() {
            return parent;
        }

        @Override
        public boolean isTerminal() {
            return true;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    final class TreeNode implements Node {
        private final Node parent;
        private final Map<String, Node> children;
        private final String name;

        public TreeNode() {
            this(null, "/");
        }

        public TreeNode(Node parent, String name) {
            this.parent = parent;
            this.name = name;
            children = new HashMap<>();
        }

        @Override
        public Map<String, Node> getChildren() {
            return children;
        }

        @Override
        public void addChild(Node node) {
            children.put(node.getName(), node);
        }

        @Override
        public Node getParent() {
            return parent;
        }

        @Override
        public boolean isTerminal() {
            return false;
        }

        @Override
        public int getSize() {
            return children.values().stream().mapToInt(Node::getSize).sum();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TreeNode treeNode)) return false;
            return Objects.equals(parent, treeNode.parent) && getName().equals(treeNode.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, getName());
        }
    }
}
