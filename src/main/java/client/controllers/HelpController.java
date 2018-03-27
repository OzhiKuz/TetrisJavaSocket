package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class HelpController {

    @FXML
    WebView web;

    @FXML
    public void initialize()
    {
        web.getEngine().load(getClass().getResource("/help.html").toString());
    }
}
