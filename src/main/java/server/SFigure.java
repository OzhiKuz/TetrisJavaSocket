package server;

import java.awt.*;

public class SFigure extends Figure {
    public SFigure(int[][] field) {
        int width = field[0].length;
        int baseCol = width / 2;
        //горизонтальная
        Point[] points = new Point[4];
        points[0] = new Point(0, baseCol);
        points[1] = new Point(0, baseCol + 1);
        points[2] = new Point(1, baseCol);
        points[3] = new Point(1, baseCol - 1);
        states.add(points);
        //вертикальная
        points = new Point[4];
        points[0] = new Point(0, baseCol - 1);
        points[1] = new Point(1, baseCol - 1);
        points[2] = new Point(1, baseCol);
        points[3] = new Point(2, baseCol);
        states.add(points);
    }
}
