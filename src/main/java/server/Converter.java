package server;

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

}
