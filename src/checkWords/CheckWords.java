package checkWords;

import classes.*;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import Starters.Main;
import jobs.Task.baseTask;
import jobs.Work.baseJob;

import java.io.IOException;
import java.util.*;

public class CheckWords implements CallableFromScenesManager {

    public static baseJob job;
    public AnchorPane parent;
    @FXML
    private ScrollPane SB_hist;
    @FXML
    private Label Points;
    public final static Random random = new Random();
    VBox cont = new VBox();

    @FXML
    void initialize() {

    }
    @Override
    public void init(boolean b) {
        if(!b){
            cont.getChildren().clear();
        }
        baseTask.controller = job;
        if(!job.init(parent, SB_hist, Points)) {
            try {
                ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.initOwner(Main.primaryStage);
                a.getDialogPane().getStylesheets().add("sources/Dialog.css");
                a.setContentText("Попросите учителя обновить работу");
                a.setTitle("Ошибка");
                a.setHeaderText("Файл устарел");
                a.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void back() throws IOException {
        //fileData = null;
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
    }


}


