package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(1025)) {
            Socket client = server.accept();
            System.out.println("Соединение установлено.");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());

            while (!client.isClosed()) {
                String request = in.readUTF();
                String[] splitRequest = request.split(" ");
                //проверка запроса
                int first = Integer.parseInt(splitRequest[0]);
                int second = Integer.parseInt(splitRequest[1]);
                String response;
                if(second != 0) {
                    int div = first / second;
                    int mod = first % second;

                    response = "Результат целочисленного деления = " + div + ", остаток от деления = " + mod;
                }
                else{
                    response = "Деление на ноль!";
                }

                out.writeUTF(response);
                out.flush();
                if(request.indexOf("end")>0){
                    break;
                }
            }

            in.close();
            out.close();
            client.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*try {
            Game game = new Game();
            GameI stub = (GameI) UnicastRemoteObject.exportObject(game, 0);
            Registry registry = LocateRegistry.createRegistry(1080);
            registry.bind("GameI", stub);

        } catch (Exception e)
        {
            e.printStackTrace();
        }*/

    }
}
