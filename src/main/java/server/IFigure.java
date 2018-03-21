package server;

import java.awt.*;

public class IFigure extends Figure {

    public IFigure(int[][] field) {
        int width = field[0].length;
        int baseCol = width / 2;
        //вертикальная
        Point[] points = new Point[4];
        points[0] = new Point(0, baseCol);
        points[1] = new Point(1, baseCol);
        points[2] = new Point(2, baseCol);
        points[3] = new Point(3, baseCol);
        states.add(points);
        //горизонтальная
        points = new Point[4];
        points[0] = new Point(0, baseCol - 2);
        points[1] = new Point(0, baseCol - 1);
        points[2] = new Point(0, baseCol);
        points[3] = new Point(0, baseCol + 1);
        states.add(points);
    }
}
