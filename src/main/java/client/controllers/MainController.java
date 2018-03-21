package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.GameI;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class MainController {
    private Stage primaryStage;
    Stage stage;
    private int width;
    private int heigth;

    @FXML
    private ComboBox<String> comboBoxSizeField;

    @FXML
    public void initialize()
    {
        comboBoxSizeField.getItems().addAll("5x10","15x25","20x30");
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void getRecord(ActionEvent actionEvent) throws IOException {
        Properties prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/server/game/records.properties");

        prop.load(fileInputStream);
        String records = "";


        for (int i = 3; i < 11; i++)
        {
            int record = Integer.valueOf(prop.getProperty(String.valueOf(i)));
            records += "Рекорд для поля размера " + i + " - " + record + "\n";
        }

        fileInputStream.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(records);
        alert.showAndWait();
        return;

    }

    public void startGame(ActionEvent actionEvent) throws IOException, NotBoundException, InterruptedException {

        if (comboBoxSizeField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Для начала игры необходимо указать размер поля!");
            alert.showAndWait();
            return;
        }

        stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        stage.setTitle("Игра");
        stage.setResizable(false);

        stage.initModality(Modality.WINDOW_MODAL);//свойство окна поверх родительского
        stage.initOwner(primaryStage); //указываем родительское окно
        GameController controller = loader.getController();
        Registry registry = LocateRegistry.getRegistry(null,1080);
        GameI stub =  (GameI) registry.lookup("GameI");
        controller.setStub(stub);
        controller.setPrimaryStage(stage);
        setSize(getSize());
        controller.setWidth(width);
        controller.setHeigth(heigth);
        stage.setScene(controller.getScene(null,new int[heigth][width]));
        stage.show();
        controller.processGame();
    }

    private int getSize()
    {
        if (comboBoxSizeField.getValue().compareTo("5x10") == 0)
            return 3;
        if (comboBoxSizeField.getValue().compareTo("15x25") == 0)
            return 4;
        if (comboBoxSizeField.getValue().compareTo("20x30") == 0)
            return 5;

        else return 0;
    }
    private void setSize(int size) {
        if (size == 3)
        {
            this.width = 5;
            this.heigth = 10;
            return;
        }
        if (size == 4)
        {
            this.width = 15;
            this.heigth = 25;
            return;
        }
        if (size == 5)
        {
            this.width = 20;
            this.heigth = 30;
            return;
        }
    }

    public void getHelp(ActionEvent actionEvent) {

    }
}
