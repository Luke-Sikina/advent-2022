public class PointWithVisibility {
    private final Point point;
    private int xNeg = 0, xPos = 0, yNeg = 0, yPos = 0;

    public PointWithVisibility(Point point) {
        this.point = point;
    }

    public void updateXNeg(PointWithVisibility prevX) {
        xNeg = prevX.point.height() < point.height() ? prevX.xNeg + 1 : 1;
    }

    public void updateXPos(PointWithVisibility nextX) {
        xPos = nextX.point.height() < point.height() ? nextX.xPos + 1 : 1;
    }

    public void updateYNeg(PointWithVisibility prevY) {
        yNeg = prevY.point.height() < point.height() ? prevY.yNeg + 1 : 1;
    }

    public void updateYPos(PointWithVisibility nextY) {
        yPos = nextY.point.height() < point.height() ? nextY.yPos + 1 : 1;
    }

    public int calcScore() {
        return xNeg * xPos * yNeg * yPos;
    }

    public Point point() {
        return point;
    }
}
