import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Grid(PointWithVisibility[][] points) {

    public int x() {
        return points.length;
    }

    public int y() {
        return points[0].length;
    }

    public List<PointWithVisibility> row(int y) {
        ArrayList<PointWithVisibility> row = new ArrayList<>();

        for (int x = 0; x < points.length; x++) {
            row.add(points[x][y]);
        }

        return row;
    }

    public List<PointWithVisibility> column(int x) {
        return new ArrayList<>(Arrays.asList(points[x]));
    }
}
