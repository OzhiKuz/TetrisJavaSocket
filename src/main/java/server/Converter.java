package server;

import java.awt.*;

public class Converter {

    public static String matrixToString(int[][] matrix){
        String result = "";
        int height = matrix.length;
        int width = matrix[0].length;
        result += height + " " + width + " ";
        StringBuilder resultBuilder = new StringBuilder(result);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                resultBuilder.append(matrix[i][j]);
        result = resultBuilder.toString();
        return result;
    }

    public static String pointsToString(Point[] points){
        StringBuilder result = new StringBuilder();
        for (Point point : points) result.append(point.x).append(" ").append(point.y).append(" ");
        return result.toString();
    }

}
