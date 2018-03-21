package server;

import java.awt.*;

public class LFigure extends Figure {
    public LFigure(int[][] field) {
        int width = field[0].length;
        int baseCol = width / 2;
        //вертикальная правая
        Point[] points = new Point[4];
        points[0] = new Point(0, baseCol - 1);
        points[1] = new Point(1, baseCol - 1);
        points[2] = new Point(2, baseCol - 1);
        points[3] = new Point(2, baseCol);
        states.add(points);
        //горизонтальная вниз
        points = new Point[4];
        points[0] = new Point(0, baseCol + 1);
        points[1] = new Point(0, baseCol);
        points[2] = new Point(0, baseCol - 1);
        points[3] = new Point(1, baseCol - 1);
        states.add(points);
        //вертикальная левая
        points = new Point[4];
        points[0] = new Point(0, baseCol);
        points[1] = new Point(1, baseCol);
        points[2] = new Point(2, baseCol);
        points[3] = new Point(0, baseCol - 1);
        states.add(points);
        //горизонтальная вверх
        points = new Point[4];
        points[0] = new Point(1, baseCol + 1);
        points[1] = new Point(1, baseCol);
        points[2] = new Point(1, baseCol - 1);
        points[3] = new Point(0, baseCol + 1);
        states.add(points);
    }
}
