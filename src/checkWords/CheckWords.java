package checkWords;

import classes.*;
import com.google.gson.Gson;
import customJobCreator.CustomJobResults;
import endScreen.EndWindow;
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
        if(!job.init(parent, SB_hist, Points, this)) {
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

    public void allCompleted(){
        try {
            job.endTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
            if(job.fileName.equals("default") && job.item.equals("default") && job.name.equals("default")){
                CustomJobResults.info=job;
                ScenesManager.setScene(Main.primaryStage, Scenes.customJobResult);
                return;
            }
            String ans = HTTPRequests.postRequest(URLs.ALLCOMPLETE_URL.toString(), "username=" + UserData.user.username + "&password=" + UserData.user.password + "&item=" + job.item + "&filename=" + job.fileName + "&mistakes=" + misstakes() + "&startTime=" + job.startTime + "&endTime=" + job.endTime, true);
            EndWindow.SuccesfulUploaded = !ans.contains("ERROR:::");
            EndWindow.alertStage = ScenesManager.createAlert("Работа пройдена", Scenes.endScreen);
            EndWindow.alertStage.setOnCloseRequest(event -> {
                try {
                    EndWindow.alertStage.close();
                    ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            EndWindow.alertStage.initOwner(Main.primaryStage);
            EndWindow.alertStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String misstakes(){
        final String[] misstakes = {"{"};
        job.tasks.forEach(baseTask -> {
            misstakes[0]+=String.format("\"%s\":%s,", baseTask.task, baseTask.mistakes);
        });
        misstakes[0] = misstakes[0].substring(0, misstakes[0].length()-1);
        System.out.println(misstakes[0] + "}");
        return misstakes[0] + "}";
    }

    public void back() throws IOException {
        //fileData = null;
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
    }


}


