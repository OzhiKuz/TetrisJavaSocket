package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
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
