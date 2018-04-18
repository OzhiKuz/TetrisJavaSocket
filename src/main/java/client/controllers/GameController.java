package client.controllers;

import client.Parser;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    //реализовать получение ответас сервера

    private ImageView[][] matr;
    private int width;
    private int height;
    private Stage primaryStage;
    //private GameI stub;
    private Socket clientSocket;
    private DataOutputStream oos;
    private DataInputStream ois;
    private Scene scene;
    private int picture;

    private Timer timer;
    private boolean flag = true;
    private int speed = 1000;


    @FXML
    public void initialize() throws IOException {
        clientSocket = new Socket("localhost", 800);
        oos = new DataOutputStream(clientSocket.getOutputStream());
        ois = new DataInputStream(clientSocket.getInputStream());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /*public void setStub(GameI stub) {
        this.stub = stub;
    } */

    private void setOnKeyTypedForScene(Scene scene) {
        scene.setOnKeyTyped(ke -> {
            if (ke.getCharacter().equals("d") || ke.getCharacter().equals("в")) {
                try {
                    sendRequest("getCurrentPoints");
                    Point[] oldPoints1 = Parser.stringToPoints(getResponse());
                    sendRequest("makeMove 0 1");
                    flag = true;
                    sendRequest("getGameField");
                    String socketResponse = getResponse();
                    primaryStage.setScene(setFigureToScene(oldPoints1,  Parser.stringToMatr(socketResponse)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("w") || ke.getCharacter().equals("ц")) {
                try {
                    sendRequest("getCurrentPoints");
                    Point[] oldPoints1 = Parser.stringToPoints(getResponse());
                    sendRequest("turn90");
                    flag = true;
                    String socketResponse = getResponse();
                    primaryStage.setScene(setFigureToScene(oldPoints1,  Parser.stringToMatr(socketResponse)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("s") || ke.getCharacter().equals("ы")) {
                try {
                    sendRequest("getCurrentPoints");
                    Point[] oldPoints1 = Parser.stringToPoints(getResponse());
                    sendRequest("makeMove 1 0");
                    flag = true;
                    String socketResponse = getResponse();
                    primaryStage.setScene(setFigureToScene(oldPoints1,  Parser.stringToMatr(socketResponse)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("a") || ke.getCharacter().equals("ф")) {
                try {
                    sendRequest("getCurrentPoints");
                    Point[] oldPoints1 = Parser.stringToPoints(getResponse());
                    sendRequest("makeMove 0 -1");
                    String socketResponse = getResponse();
                    primaryStage.setScene(setFigureToScene(oldPoints1,  Parser.stringToMatr(socketResponse)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Scene setFigureToScene(Point[] oldPoints, int[][] field) throws IOException {
        sendRequest("getCurrentPoints");
        Point[] points = Parser.stringToPoints(getResponse());
        if (oldPoints != null)
            for (int i = 0; i < oldPoints.length; i++) {
                matr[oldPoints[i].x][oldPoints[i].y] = new ImageView(new Image("/field.png"));
                matr[oldPoints[i].x][oldPoints[i].y].setFitHeight(500 / height);
                matr[oldPoints[i].x][oldPoints[i].y].setFitWidth(320 / width);
            }
        for (int i = 0; i < points.length; i++) {
            matr[points[i].x][points[i].y] = new ImageView(new Image("/" + picture + ".png"));
            matr[points[i].x][points[i].y].setFitHeight(500 / height);
            matr[points[i].x][points[i].y].setFitWidth(320 / width);
        }
        Scene scene = new Scene(getContainer(), 320, 610);
        setOnKeyTypedForScene(scene);
        return scene;
    }

    public Scene getScene(int[][] field) throws IOException {

        ImageView[][] oldMatr = matr;

        matr = new ImageView[height][width];
        for (int i = 0; i < matr.length; i++)
            for (int j = 0; j < matr[i].length; j++) {
                if (field[i][j] == 2) {
                    Image im = oldMatr[i][j].getImage();
                    matr[i][j] = new ImageView(im);
                    matr[i][j].setFitHeight(500 / height);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                } else if (field[i][j] == 3) {
                    Image im = oldMatr[i - 1][j].getImage();
                    matr[i][j] = new ImageView(im);
                    matr[i][j].setFitHeight(500 / height);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                } else if (field[i][j] == 1) {

                    matr[i][j] = new ImageView(new Image("/" + picture + ".png"));
                    matr[i][j].setFitHeight(500 / height);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                }

                matr[i][j] = new ImageView(new Image("/field.png"));
                matr[i][j].setFitHeight(500 / height);
                matr[i][j].setFitWidth(320 / width);
            }

        scene = new Scene(getContainer(), 320, 610);
        setOnKeyTypedForScene(scene);

        return scene;
    }

    private Pane getContainer() throws IOException {
        Button button = new Button("Завершить!");
        button.setPrefSize(150, 40);
        button.setFont(new Font("Times new roman", 20));
        button.setPadding(new Insets(5, 0, 0, 20));
        button.setOnAction(new EventHandlerEndGame());
        sendRequest("getCurrentPoint");
        Label label = new Label("Очки: " + getResponse());
        label.setFont(new Font("Times new roman", 20));
        //label.setPadding(new Insets(20, 0, 0, 0));

        sendRequest("getRecord");
        Label label1 = new Label("Рекорд: " + getResponse());
        label1.setFont(new Font("Times new roman", 20));
        label1.setPadding(new Insets(0, 0, 0, 20));

        Pane hbox1 = new HBox(label, label1);
        hbox1.setPadding(new Insets(5, 20, 20, 20));


        Pane[] hbox = new Pane[height];

        for (int i = 0; i < height; i++) {
            ImageView[] im = new ImageView[width];
            for (int j = 0; j < width; j++) {
                im[j] = matr[i][j];
            }
            hbox[i] = new HBox(im);
        }

        Pane vbox = new VBox(hbox);
        //vbox.setId("testId");

        return new VBox(hbox1, vbox, button);
    }

    private void sendRequest(String socketRequest) throws IOException {
        oos.writeUTF(socketRequest);
        oos.flush();
        oos.close();
    }

    private String getResponse() throws IOException {
        String response = "";
        while (response.length() == 0) {
            response = ois.readUTF();
        }
        ois.close();
        return response;
    }

    public void processGame() throws IOException {
        timer = new java.util.Timer();

        //stub.setGameField(new int[height][width]);
        String socketResponse;
        sendRequest("setGameField " + height + " " + width + "");
        //while (stub.generationFigure()) {
        //генерируем самую первую фигуру
        //stub.generationFigure();
        sendRequest("generationFigure");
        getResponse();
        Random rand = new Random();
        picture = rand.nextInt(4);
        //реализовать метод в классе Parser, который будет преобразовывать строку в матрицу int
        sendRequest("getGameField");
        socketResponse = getResponse();
        primaryStage.setScene(setFigureToScene(null, Parser.stringToMatr(socketResponse)));
        // метод создающий задачу
        clock();
    }

    private void clock() {
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    try {
                        String socketResponse;
                        if (flag) {
                            //реализовать метод в классе Parser, который будет из строки создавать массив Point
                            sendRequest("getCurrentPoints");
                            socketResponse = getResponse();
                            Point[] oldPoints = Parser.stringToPoints(socketResponse);
                            //реализовать метод в классе Parser, который будет из строки создавать boolean
                            sendRequest("makeMove 1 0");
                            socketResponse = getResponse();
                            flag = Parser.stringToBoolean(socketResponse);
                            if (flag) {
                                sendRequest("getGameField");
                                socketResponse = getResponse();
                                primaryStage.setScene(setFigureToScene(oldPoints, Parser.stringToMatr(socketResponse)));
                            } else
                                //stub.setAllTwo();
                                sendRequest("setAllTwo");
                        } else {
                            //stub.setAllTwo();
                            sendRequest("setAllTwo");

                            //реализовать метод в классе Parser, который будет из строки создавать int
                            sendRequest("checkForDeleteLine");
                            socketResponse = getResponse();
                            int deletedLines = Parser.stringToInt(socketResponse);
                            if (deletedLines != 0) {
                                sendRequest("getGameField");
                                socketResponse = getResponse();
                                primaryStage.setScene(getScene(Parser.stringToMatr(socketResponse)));
                                //stub.setAllThreeToTwo();
                                sendRequest("setAllThreeToTwo");
                                for (int i = 0; i < deletedLines; i++) {
                                    if (speed > 300)
                                        speed -= 50;
                                }

                                timer.cancel();
                                timer = new java.util.Timer();
                                clock();
                            }
                            sendRequest("generationFigure");
                            socketResponse = getResponse();
                            if (Parser.stringToBoolean(socketResponse)) {
                                flag = true;
                                Random rand = new Random();
                                picture = rand.nextInt(4);
                                sendRequest("getGameField");
                                socketResponse = getResponse();
                                primaryStage.setScene(setFigureToScene(null, Parser.stringToMatr(socketResponse)));
                                //timer = new Timer();
                                //clock();

                            } else {

                                timer.cancel();
                                endGameMessage();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        long delay = 1000;
        timer.schedule(task, speed, speed);
    }

    private void endGameMessage() throws IOException {
        sendRequest("isNewRecord");

        boolean newRecord = Parser.stringToBoolean(getResponse());
        String message = "";

        sendRequest("getCurrentPoint");
        String socketResponse = getResponse();
        if (newRecord)
            message = "Игра окончега! Вы установили новый рекорд: " + socketResponse;
        else message = "Игра окончена! Набрано очков: " + socketResponse;


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private class EventHandlerEndGame implements javafx.event.EventHandler {
        @Override
        public void handle(Event event) {
            timer.cancel();
            primaryStage.close();
        }
    }
}
