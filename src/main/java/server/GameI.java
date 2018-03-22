package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameI extends Remote {
    int[][] getGameField() throws RemoteException;
    void setGameField(int[][] gameField) throws RemoteException;
    boolean makeMove(int x,int y) throws RemoteException, InterruptedException;
    boolean generationFigure() throws RemoteException;
    int checkForDeleteLine() throws RemoteException;
    void setAllTwo() throws RemoteException;
    boolean turn90() throws RemoteException;
}
