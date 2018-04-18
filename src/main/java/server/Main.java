package server;

import java.awt.*;
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
        boolean flag = true;
        while(flag) {
            try (ServerSocket server = new ServerSocket(8000)) {
                Socket client = server.accept();
                System.out.println("Соединение установлено.");

                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());

                Game game = new Game();
                String response = "";

                while (!client.isClosed()) {
                    String request = in.readUTF();
                    if (request.indexOf("end") > 0) {
                        break;
                    }
                    String[] splitRequest = request.split(" ");
                    //проверка запроса
                    switch (splitRequest[0]) {
                        case "getCurrentPoints":
                            Point[] points = game.getCurrentPoints();
                            response = Converter.pointsToString(points);
                            break;
                        case "getCurrentPoint":
                            response = String.valueOf(game.getCurrentPoint());
                            break;
                        case "makeMove":
                            if (game.makeMove(Integer.parseInt(splitRequest[1]), Integer.parseInt(splitRequest[2])))
                                response = "true";
                            else
                                response = "false";
                            break;
                        case "turn90":
                            if (game.turn90())
                                response = "true";
                            else
                                response = "false";
                            break;
                        case "getGameField":
                            response = Converter.matrixToString(game.getGameField());
                            break;
                        case "setGameField":
                            int[][] gameField = new int[Integer.parseInt(splitRequest[1])][];
                            for (int i = 0; i < gameField.length; i++)
                                gameField[i] = new int[Integer.parseInt(splitRequest[2])];
                            game.setGameField(gameField);
                            response = "void";
                            break;
                        case "setAllTwo":
                            game.setAllTwo();
                            response = "void";
                            break;
                        case "checkForDeleteLine":
                            response = String.valueOf(game.checkForDeleteLine());
                            break;
                        case "setAllThreeToTwo":
                            game.setAllThreeToTwo();
                            response = "void";
                            break;
                        case "generationFigure":
                            if (game.generationFigure())
                                response = "true";
                            else
                                response = "false";
                            break;
                        case "isNewRecord":
                            if (game.isNewRecord())
                                response = "true";
                            else
                                response = "false";
                            break;
                        case "getRecord":
                            response = String.valueOf(game.getRecord());
                            break;
                    }
                    out.writeUTF(response);
                    out.flush();
                }
                in.close();
                out.close();
                client.close();
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
