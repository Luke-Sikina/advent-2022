import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 7 Part 1");
        s.parseInput().map(s::part1).ifPresentOrElse(
            i -> System.out.println("The sum of all dirs with size <= 100000 is: " + i),
            () -> System.out.println("Bad input")
        );

        System.out.println("Day 7 Part 2");
        s.parseInput().map(s::part2).ifPresentOrElse(
            i -> System.out.println("The smallest big dir is: " + i),
            () -> System.out.println("Bad input")
        );
    }

    private static final Pattern cdRegex = Pattern.compile("^\\$ cd (.*)$");
    private static final Pattern lsRegex = Pattern.compile("^\\$ ls$");
    private static final Pattern dirRegex = Pattern.compile("^dir (.*)$");
    private static final Pattern fileRegex = Pattern.compile("^(\\d+) (.*)$");

    public int part1(Node node) {
        return switch (node) {
            case Node.TreeNode n -> (n.size() <= 100000 ? n.size() : 0) +
                n.getChildren().values().stream()
                    .mapToInt(this::part1)
                    .sum();
            case Node.LeafNode ignored -> 0;
        };
    }

    public int part2(Node node) {
        return findAllDirs(node)
            .mapToInt(Node::size)
            .filter(i -> i > node.size() - 40000000)
            .min()
            .orElseThrow();
    }

    private Stream<Node.TreeNode> findAllDirs(Node node) {
        return switch (node) {
            case Node.LeafNode ignored -> Stream.empty();
            case Node.TreeNode t -> Stream.concat(
                Stream.of(t),
                t.getChildren().values().stream().flatMap(this::findAllDirs)
            );
        };
    }

    public Node.TreeNode parse(List<String> in) {
        Node.TreeNode root = new Node.TreeNode();
        Node current = root;

        for (int i = 0; i < in.size(); i++) {
            String line = in.get(i);
            Matcher cd = cdRegex.matcher(line);
            Matcher ls = lsRegex.matcher(line);
            if (cd.find()) {
                current = cd(root, current, cd.group(1));
            } else if (ls.find()) {
                i = ls(current, in, i);
            }
        }

        return root;
    }

    private int ls(Node current, List<String> lines, int i) {
        while (i < lines.size() - 1) {
            i++;
            String line = lines.get(i);
            if (line.startsWith("$ ")) {
                return i - 1;
            }
            Matcher dir = dirRegex.matcher(line);
            Matcher file = fileRegex.matcher(line);
            if (dir.find() && !current.getChildren().containsKey(dir.group(1))) {
                Node.TreeNode toAdd = new Node.TreeNode(current, dir.group(1));
                current.getChildren().put(toAdd.name(), toAdd);
            } else if (file.find() && current instanceof Node.TreeNode n) {
                Node.LeafNode toAdd = new Node.LeafNode(current, Integer.parseInt(file.group(1)), file.group(2));
                n.addChild(toAdd);
            }
        }
        return i;
    }

    private Node cd(Node.TreeNode root, Node current, String dir) {
        return switch (dir) {
            case "/" -> root;
            case ".." -> current.parent();
            default -> current.getChildren().get(dir);
        };
    }

    private Optional<Node.TreeNode> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            return Optional.of(parse(readFromInputStream(inputStream)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
