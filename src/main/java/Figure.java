import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public abstract class Figure {
    ArrayList<Point[]> states = new ArrayList<Point[]>();
    private int currentState = 0;
    // x - по вертикали, y - по горизонтали
    private int dx = 0;
    private int dy = 0;

    public boolean setFirst(int[][] field) {
        Random random = new Random();
        currentState = random.nextInt(states.size());
        Point[] currentPoints = states.get(currentState);
        return checkField(currentPoints, field);
    }

    public boolean turn90(int[][] field){
        int width = field[0].length;
        int height = field.length;
        int newState = currentState + 1;
        if(newState == states.size()) newState = 0;
        Point[] newPoints = getNewPoints(newState, dx, dy);
        for (Point newPoint : newPoints)
            if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= height || newPoint.y >= width)
                return false;
        if(checkField(newPoints, field)) {
            currentState = newState;
            return true;
        }
        else return false;
    }

    public boolean move(int x, int y, int[][] field){
        int width = field[0].length;
        int height = field.length;
        int newdx = dx + x;
        int newdy = dy + y;
        Point[] newPoints = getNewPoints(currentState, newdx, newdy);
        for (Point newPoint : newPoints)
            if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= height || newPoint.y >= width)
                return false;
        if(checkField(newPoints, field)) {
            dx = newdx;
            dy = newdy;
            return true;
        }
        else return false;
    }

    private boolean checkField(Point[] newPoints, int[][] field) {
        boolean isFree = true;
        for (Point newPoint : newPoints)
            if (field[newPoint.x][newPoint.y] == 2)
                isFree = false;
        return isFree;
    }

    //получения текущего положения
    public Point[] getCurrentPoints(){
        Point[] currentPoint = new Point[4];
        for(int i = 0; i < currentPoint.length; i++){
            currentPoint[i] = new Point(states.get(currentState)[i].x + dx,states.get(currentState)[i].y + dy);
        }
        return currentPoint;
    }

    private Point[] getNewPoints(int newState, int newdx, int newdy){
        Point[] newPoint = new Point[4];
        for(int i = 0; i < newPoint.length; i++){
            newPoint[i] = new Point(states.get(newState)[i].x + newdx,states.get(newState)[i].y + newdy);
        }
        return newPoint;
    }
}
