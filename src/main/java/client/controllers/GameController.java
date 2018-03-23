package client.controllers;

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
import server.GameI;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private ImageView[][] matr;
    private int width;
    private int heigth;
    private Stage primaryStage;
    private GameI stub;
    private Scene scene;
    private int picture;

    private Timer timer;
    private boolean flag = true;
    private int speed = 1000;


    @FXML
    public void initialize() {

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setStub(GameI stub) {
        this.stub = stub;
    }

    private void setOnKeyTypedForScene(Scene scene)
    {
        scene.setOnKeyTyped(ke -> {
            if (ke.getCharacter().equals("d") || ke.getCharacter().equals("в")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.makeMove(0, 1);
                    flag = true;
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("w") || ke.getCharacter().equals("ц")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.turn90();
                    flag = true;
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("s") || ke.getCharacter().equals("ы")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.makeMove(1, 0);
                    flag = true;
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("a") || ke.getCharacter().equals("ф")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.makeMove(0, -1);
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Scene setFigureToScene(Point[] oldPoints, int[][] field) throws IOException {
        Point[] points = stub.getCurrentPoints();
        if (oldPoints != null)
        for (int i = 0; i < oldPoints.length; i++) {
            matr[oldPoints[i].x][oldPoints[i].y] = new ImageView(new Image("/field.png"));
            matr[oldPoints[i].x][oldPoints[i].y].setFitHeight(500 / heigth);
            matr[oldPoints[i].x][oldPoints[i].y].setFitWidth(320 / width);
        }
        for (int i = 0; i < points.length; i++) {
            matr[points[i].x][points[i].y] = new ImageView(new Image("/" + picture + ".png"));
            matr[points[i].x][points[i].y].setFitHeight(500 / heigth);
            matr[points[i].x][points[i].y].setFitWidth(320 / width);
        }
        Scene scene = new Scene(getContainer(), 320, 610);
        setOnKeyTypedForScene(scene);
        return scene;
    }

    public Scene getScene(int[][] field) throws IOException {

        ImageView[][] oldMatr = matr;

        matr = new ImageView[heigth][width];
        for (int i = 0; i < matr.length; i++)
            for (int j = 0; j < matr[i].length; j++) {
                if (field[i][j] == 2) {
                    Image im = oldMatr[i][j].getImage();
                    matr[i][j] = new ImageView(im);
                    matr[i][j].setFitHeight(500 / heigth);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                } else if (field[i][j] == 3) {
                    Image im = oldMatr[i - 1][j].getImage();
                    matr[i][j] = new ImageView(im);
                    matr[i][j].setFitHeight(500 / heigth);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                }
                else if (field[i][j] == 1) {

                    matr[i][j] = new ImageView(new Image("/" + picture + ".png"));
                    matr[i][j].setFitHeight(500 / heigth);
                    matr[i][j].setFitWidth(320 / width);
                    continue;
                }

                matr[i][j] = new ImageView(new Image("/field.png"));
                matr[i][j].setFitHeight(500 / heigth);
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
        button.setPadding(new Insets(5,0,0,20));
        button.setOnAction(new EventHandlerEndGame());
        Label label = new Label("Очки: " + stub.getCurrentPoint());
        label.setFont(new Font("Times new roman", 20));
        //label.setPadding(new Insets(20, 0, 0, 0));

        Label label1 = new Label("Рекорд: " + stub.getRecord());
        label1.setFont(new Font("Times new roman",20));
        label1.setPadding(new Insets(0,0,0,20));

        Pane hbox1 = new HBox(label,label1);
        hbox1.setPadding(new Insets(5, 20, 20, 20));


        Pane[] hbox = new Pane[heigth];

        for (int i = 0; i < heigth; i++) {
            ImageView[] im = new ImageView[width];
            for (int j = 0; j < width; j++) {
                im[j] = matr[i][j];
            }
            hbox[i] = new HBox(im);
        }

        Pane vbox = new VBox(hbox);
        //vbox.setId("testId");
        return new VBox(hbox1,vbox,button);
    }

    public void processGame() throws IOException {
        timer = new java.util.Timer();

        stub.setGameField(new int[heigth][width]);

        //while (stub.generationFigure()) {
        //генерируем самую первую фигуру
        stub.generationFigure();
        Random rand = new Random();
        picture = rand.nextInt(4);
        primaryStage.setScene(setFigureToScene(null, stub.getGameField()));
        // метод создающий задачу
        clock();

            /*stub.setAllTwo();
            int deletedLines = stub.checkForDeleteLine();
            if (deletedLines != 0) {
                for (int i = 0; i < deletedLines; i++) {
                    if (speed > 300)
                        speed -= 25;
                }
            }*/
        //}
    }

    private void clock() {
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    try {
                        if (flag) {
                            Point[] oldPoints = stub.getCurrentPoints();
                            flag = stub.makeMove(1, 0);
                            if(flag)
                                primaryStage.setScene(setFigureToScene(oldPoints, stub.getGameField()));
                            else
                                stub.setAllTwo();

                        } else {
                            stub.setAllTwo();
                            int deletedLines = stub.checkForDeleteLine();
                            if (deletedLines != 0) {
                                primaryStage.setScene(getScene(stub.getGameField()));
                                stub.setAllThreeToTwo();
                                for (int i = 0; i < deletedLines; i++) {
                                    if (speed > 300)
                                        speed -= 50;
                                }

                                timer.cancel();
                                timer = new java.util.Timer();
                                clock();
                            }
                            if (stub.generationFigure()) {
                                flag = true;
                                Random rand = new Random();
                                picture = rand.nextInt(4);
                                primaryStage.setScene(setFigureToScene(null, stub.getGameField()));
                                //timer = new Timer();
                                //clock();

                            }else {

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

    private void endGameMessage() throws RemoteException {
        boolean newRecord = stub.isNewRecord();
        String message = "";


            if (newRecord)
                message = "Игра окончнга! Вы установили новый рекорд: " + stub.getCurrentPoint();
            else message = "Игра окончена! Набрано очков: " + stub.getCurrentPoint();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
        return;

    }

    private class EventHandlerEndGame implements javafx.event.EventHandler {
        @Override
        public void handle(Event event) {
            timer.cancel();
            primaryStage.close();
        }
    }
}
