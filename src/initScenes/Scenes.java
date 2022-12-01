package initScenes;

import auth.Auth;
import checkWords.CheckWords;
import classes.CallableFromScenesManager;
import customJobCreator.CustomJobCreator;
import endScreen.EndWindow;
import homePage.HomePage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public enum Scenes {
    auth(915, 510, Auth.class.getResource("auth.fxml")),
    homePage(604, 439, 697 ,534, HomePage.class.getResource("homePage.fxml")),
    checkWords(1400, 802, 1400,802, CheckWords.class.getResource("CheckWords.fxml")),
    endScreen(800, 400, EndWindow.class.getResource("EndWindow.fxml")),
    customJobCreator(500, 500, 500, 500, CustomJobCreator.class.getResource("customJobCreator.fxml")),
    customJobResult(500,500,500,500, CustomJobCreator.class.getResource("customJobResults.fxml"));
    double minWidth;
    double minHeight;
    double width;
    double height;
    boolean isResizable = true;
    boolean isMaximized = false;
    boolean firstShowing = true;
    Parent root;
    CallableFromScenesManager controller;
    Scenes(double _minWidth, double _minHeight, double _width, double _height, URL _FXML_URL) {
        minWidth = _minWidth;
        minHeight = _minHeight;
        width = _width;
        height = _height;
        isMaximized = true;
        try {
            FXMLLoader loader = new FXMLLoader(_FXML_URL);
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Scenes(double _width, double _height, URL res){
        width = _width;
        height = _height;
        isResizable = false;
        try {
            FXMLLoader loader = new FXMLLoader(res);
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
