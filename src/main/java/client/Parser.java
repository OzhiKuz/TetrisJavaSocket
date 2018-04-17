package client;

import java.awt.*;

public class Parser {

    //строка в матрицу, высота, ширина, значения
    public static int[][] stringToMatr(String response){
        String[] splitedString = response.split(" ");
        int height = Integer.parseInt(splitedString[0]);
        int width = Integer.parseInt(splitedString[1]);
        String matrixString = splitedString[2];
        int[][] matrix = new int[height][];
        for (int i = 0; i < height; i++) {
            matrix[i] = new int[width];
            for (int j = 0; j < width; j++)
                matrix[i][j] = Character.getNumericValue(matrixString.charAt(i * width + j));
        }
        return matrix;
    }

    public static boolean stringToBoolean(String response){
        return response.equals("true");
    }

    //x y x y...
    public static Point[] stringToPoints(String response){
        Point[] points = new Point[4];
        String[] splitedString = response.split(" ");
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(Integer.parseInt(splitedString[i * 2]), Integer.parseInt(splitedString[i * 2 + 1]));
        }
        return points;
    }

    public static int stringToInt(String response){
        return Integer.parseInt(response);
    }


}
