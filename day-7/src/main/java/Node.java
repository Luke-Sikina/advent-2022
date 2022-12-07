import java.util.*;

public sealed interface Node permits Node.LeafNode, Node.TreeNode {
    Map<String, Node> getChildren();
    Node parent();
    int size();
    String name();

    record LeafNode(Node parent, int size, String name) implements Node {
        @Override
        public Map<String, Node> getChildren() {
            return Map.of();
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

        public void addChild(Node node) {
            children.put(node.name(), node);
        }

        @Override
        public Node parent() {
            return parent;
        }

        @Override
        public int size() {
            return children.values().stream().mapToInt(Node::size).sum();
        }

        @Override
        public String name() {
            return name;
        }
    }
}
