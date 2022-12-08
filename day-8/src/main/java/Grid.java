import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Grid(Point[][] points) {

    public int x() {
        return points.length;
    }

    public int y() {
        return points[0].length;
    }

    public List<Point> row(int y) {
        ArrayList<Point> row = new ArrayList<>();

        for (int x = 0; x < points.length; x++) {
            row.add(points[x][y]);
        }

        return row;
    }

    public List<Point> column(int x) {
        return new ArrayList<>(Arrays.asList(points[x]));
    }
}
