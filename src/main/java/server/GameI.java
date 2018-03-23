package server;

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameI extends Remote {
    int[][] getGameField() throws RemoteException;
    void setGameField(int[][] gameField) throws RemoteException;
    boolean makeMove(int x,int y) throws RemoteException, InterruptedException;
    boolean generationFigure() throws IOException;
    int checkForDeleteLine() throws RemoteException;
    void setAllTwo() throws RemoteException;
    boolean turn90() throws RemoteException;
    Point[] getCurrentPoints() throws RemoteException;
    boolean isNewRecord() throws RemoteException;
    int getCurrentPoint() throws RemoteException;
    int getRecord() throws IOException;
    void setAllThreeToTwo() throws IOException;
}
