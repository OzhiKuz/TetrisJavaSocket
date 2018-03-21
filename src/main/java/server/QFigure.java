package server;

import java.awt.Point;

public class QFigure extends Figure {
    public QFigure(int[][] field) {
        int width = field[0].length;
        int baseCol = width / 2;
        Point[] points = new Point[4];
        points[0] = new Point(0, baseCol - 1);
        points[1] = new Point(0, baseCol);
        points[2] = new Point(1, baseCol - 1);
        points[3] = new Point(1, baseCol);
        states.add(points);
    }

}
