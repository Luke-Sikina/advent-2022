import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 7 Part 1");
        s.parseInput().map(s::part1).ifPresentOrElse(
            i -> System.out.println("The sum of all dirs with size <= 100000 is: " + i),
            () -> System.out.println("Bad input")
        );
    }

    private static final Pattern cdRegex = Pattern.compile("^\\$ cd (.*)$");
    private static final Pattern lsRegex = Pattern.compile("^\\$ ls$");
    private static final Pattern dirRegex = Pattern.compile("^dir (.*)$");
    private static final Pattern fileRegex = Pattern.compile("^(\\d+) (.*)$");

    public int part1(Node node) {
        return switch (node) {
            case Node.TreeNode n -> {
                if (n.getSize() <= 100000) {
                    yield n.getSize() + n.getChildren().values().stream()
                        .filter(c -> !c.isTerminal())
                        .mapToInt(this::part1)
                        .sum();
                } else {
                    yield n.getChildren().values().stream()
                        .filter(c -> !c.isTerminal())
                        .mapToInt(this::part1)
                        .sum();
                }
            }
            case Node.LeafNode ignored -> 0;
        };
    }

    public Node.TreeNode parse(List<String> in) {
        Node.TreeNode root = new Node.TreeNode();
        Node current = root;

        for (int i = 0; i < in.size(); i++) {
            String line = in.get(i);
            if (isCommand(line)) {
                Matcher cd = cdRegex.matcher(line);
                Matcher ls = lsRegex.matcher(line);
                if (cd.find()) {
                    current = cd(root, current, cd.group(1));
                } else if (ls.find()) {
                    i = ls(current, in, i);
                }
            }
        }

        return root;
    }

    private int ls(Node current, List<String> lines, int i) {
        while (i < lines.size() - 1) {
            i++;
            String line = lines.get(i);
            if (isCommand(line)) {
                return i - 1;
            }
            Matcher dir = dirRegex.matcher(line);
            Matcher file = fileRegex.matcher(line);
            if (dir.find() && !current.getChildren().containsKey(dir.group(1))) {
                Node.TreeNode toAdd = new Node.TreeNode(current, dir.group(1));
                current.getChildren().put(toAdd.getName(), toAdd);
            } else if (file.find()) {
                Node.LeafNode toAdd = new Node.LeafNode(current, Integer.parseInt(file.group(1)), file.group(2));
                current.getChildren().put(toAdd.getName(), toAdd);
            }
        }
        return i;
    }

    private Node cd(Node.TreeNode root, Node current, String dir) {
        return switch (dir) {
            case "/" -> root;
            case ".." -> current.getParent();
            default -> current.getChildren().get(dir);
        };
    }

    private boolean isCommand(String line) {
        return line.startsWith("$ ");
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
