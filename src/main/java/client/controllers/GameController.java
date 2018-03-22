package client.controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
    private int points;
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
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("w") || ke.getCharacter().equals("ц")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.turn90();
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (ke.getCharacter().equals("s") || ke.getCharacter().equals("ы")) {
                try {
                    Point[] oldPoints1 = stub.getCurrentPoints();
                    stub.makeMove(1, 0);
                    primaryStage.setScene(setFigureToScene(oldPoints1, stub.getGameField()));

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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
                }
            }
        });
    }

    private Scene setFigureToScene(Point[] oldPoints, int[][] field) throws RemoteException {
        Point[] points = stub.getCurrentPoints();
        if (oldPoints != null)
        for (int i = 0; i < oldPoints.length; i++) {
            matr[oldPoints[i].x][oldPoints[i].y] = new ImageView(new Image("/field.png"));
            matr[oldPoints[i].x][oldPoints[i].y].setFitHeight(400 / heigth);
            matr[oldPoints[i].x][oldPoints[i].y].setFitWidth(400 / heigth);
        }
        for (int i = 0; i < points.length; i++) {
            matr[points[i].x][points[i].y] = new ImageView(new Image("/" + picture + ".png"));
            matr[points[i].x][points[i].y].setFitHeight(400 / heigth);
            matr[points[i].x][points[i].y].setFitWidth(400 / heigth);
        }
        Scene scene = new Scene(getContainer(), 590, 390);
        setOnKeyTypedForScene(scene);
        return scene;
    }

    public Scene getScene(int[][] field) throws RemoteException, FileNotFoundException {

        ImageView[][] oldMatr = matr;

        matr = new ImageView[heigth][width];
        for (int i = 0; i < matr.length; i++)
            for (int j = 0; j < matr[i].length; j++) {
                if (field[i][j] == 2) {
                    Image im = oldMatr[i - 1][j].getImage();
                    matr[i][j] = new ImageView(im);
                    matr[i][j].setFitHeight(400 / heigth);
                    matr[i][j].setFitWidth(400 / heigth);
                    continue;
                } else if (field[i][j] == 1) {

                    matr[i][j] = new ImageView(new Image("/" + picture + ".png"));
                    matr[i][j].setFitHeight(400 / heigth);
                    matr[i][j].setFitWidth(400 / heigth);
                    continue;
                }

                matr[i][j] = new ImageView(new Image("/field.png"));
                matr[i][j].setFitHeight(400 / heigth);
                matr[i][j].setFitWidth(400 / heigth);
            }

            scene = new Scene(getContainer(), 590, 390);
        setOnKeyTypedForScene(scene);
        return scene;
    }

    private Pane getContainer() throws RemoteException {
        Button button = new Button("Завершить!");
        button.setPrefSize(150, 80);
        button.setFont(new Font("Times new roman", 20));
        button.setOnAction(new EventHandlerEndGame());
        Label label = new Label("Очки: " + points);
        label.setFont(new Font("Times new roman", 20));
        label.setPadding(new Insets(20, 0, 0, 0));

        Pane vbox1 = new VBox(label, button);
        vbox1.setPadding(new Insets(20, 20, 20, 20));


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
        return new HBox(vbox, vbox1);
    }

    public void processGame() throws FileNotFoundException, RemoteException, InterruptedException {
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
                            primaryStage.setScene(setFigureToScene(oldPoints, stub.getGameField()));

                        } else {
                            stub.setAllTwo();
                            int deletedLines = stub.checkForDeleteLine();
                            if (deletedLines != 0) {
                                primaryStage.setScene(getScene(stub.getGameField()));

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

                            }else timer.cancel();
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

    private class EventHandlerEndGame implements javafx.event.EventHandler {
        @Override
        public void handle(Event event) {
            timer.cancel();
            primaryStage.close();
        }
    }
}
