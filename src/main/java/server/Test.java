package server;

import java.awt.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Test {
   /* public static void main(String[] args) {
        int[][] field = new int[6][];
        for(int i = 0; i < field.length; i++)
        {
            field[i] = new int[10];
        }
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                field[i][j] = 0;
            }
        }
        Figure testFigure = new SFigure(field);
        testFigure.setFirst(field);
        testFigure.move(1,0, field);
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


    } */
   public static void main(String[] args) throws RemoteException, AlreadyBoundException {
       try {
           Game game = new Game();
           GameI stub = (GameI) UnicastRemoteObject.exportObject(game, 0);
           Registry registry = LocateRegistry.createRegistry(1080);
           registry.bind("GameI", stub);

       } catch (Exception e)
       {
           e.printStackTrace();
       }

   }
}
