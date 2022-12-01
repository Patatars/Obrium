package checkWords;

import classes.*;
import com.google.gson.Gson;
import customJobCreator.CustomJobResults;
import endScreen.EndWindow;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Starters.Main;
import jobs.Task.baseTask;
import jobs.Work.learnWork.learnJob;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CheckWords implements CallableFromScenesManager {

    public static learnJob job;
    public AnchorPane parent;



    @FXML
    private ScrollPane SB_hist;


    @FXML
    private Label Points;




    final static Random random = new Random();

    VBox cont = new VBox();

    @FXML
    void initialize() {

    }

    @Override
    public void init(boolean b) {
        if(!b){
            cont.getChildren().clear();
        }
        baseTask.controller = this;
        cont.setSpacing(10);
        job.tasks.forEach(baseTask -> {
            HBox task = NewAnchorPane(baseTask.task);
            cont.getChildren().add(task);
            baseTask.setHistory(task);
        });
        SB_hist.setContent(cont);
        job.startTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
        randomWord();
    }

    public void randomWord(){
        AtomicBoolean allComplete = new AtomicBoolean(true);
        job.tasks.forEach(baseTask -> allComplete.set(baseTask.state == jobs.Task.baseTask.State.COMPLETE && allComplete.get()));
        if(allComplete.get()){
            try {
                Gson gson = new Gson();
                job.endTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
                if(job.fileName.equals("default") && job.item.equals("default") && job.name.equals("default")){
                    CustomJobResults.info=job;
                    ScenesManager.setScene(Main.primaryStage, Scenes.customJobResult);
                    return;
                }
                String ans = HTTPRequests.postRequest(URLs.ALLCOMPLETE_URL.toString(), "username=" + UserData.user.username + "&password=" + UserData.user.password + "&item=" + job.item + "&filename=" + job.fileName + "&mistakes=" + gson.toJson(job.tasks.get(0)) + "&startTime=" + job.startTime + "&endTime=" + job.endTime, true);
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
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        int numWord =random.nextInt(job.tasks.size());
        while(job.tasks.get(numWord).state == baseTask.State.COMPLETE) numWord = random.nextInt(job.tasks.size());
        job.tasks.get(numWord).show(parent, Points);


    }
    HBox NewAnchorPane(String word){
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(0,10 , 0 ,10));
        hb.setStyle("-fx-background-color: #131313;");

        Label letter = new Label();
        letter.setTextFill(Color.WHITE);
        letter.setText(word);
        letter.setFont(new Font("Times New Roman", 30));
        hb.getChildren().add(letter);


        Label pointsB = new Label();
        pointsB.setTextFill(Color.WHITE);
        HBox.setMargin(pointsB, new Insets(0,0,0,20));
        pointsB.setMinWidth(Region.USE_PREF_SIZE);
        pointsB.setText("0");
        pointsB.setFont(new Font("Times New Roman", 30));
        hb.getChildren().add(pointsB);
        return hb;
    }



    public void back() throws IOException {
        //fileData = null;
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
    }


}


