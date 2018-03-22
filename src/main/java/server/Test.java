package server;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        int[][] field = new int[5][];
        for(int i = 0; i < field.length; i++)
        {
            field[i] = new int[5];
        }
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                field[i][j] = 0;
            }
        }
        Figure testFigure = new LFigure(field);
        testFigure.setFirst(field);
        boolean a = true;
        do {
            if(a){
                Point[] testPoints = testFigure.getCurrentPoints();
                for (Point curPoint : testPoints) {
                    field[curPoint.x][curPoint.y] = 1;
                }

                for (int[] row : field) {
                    for (int elem : row) {
                        System.out.print(elem + " ");
                    }
                    System.out.println();
                }
                System.out.println();
                for(int i = 0; i < field.length; i++)
                {
                    for(int j = 0; j < field[i].length; j++)
                    {
                        field[i][j] = 0;
                    }
                }
                a = testFigure.move(1,0, field);

            }

        }while(a);
        /*boolean a = testFigure.move(1,0, field);
        Point[] testPoints = testFigure.getCurrentPoints();
        for (Point curPoint : testPoints) {
            field[curPoint.x][curPoint.y] = 1;
        }

        for (int[] row : field) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
        System.out.println();
        testFigure.turn90(field);
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                field[i][j] = 0;
            }
        }
        testPoints = testFigure.getCurrentPoints();
        for (Point curPoint : testPoints) {
            field[curPoint.x][curPoint.y] = 1;
        }

        for (int[] row : field) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }

        System.out.println(a);*/
    }
}
