package jobs.Work;


import Starters.Main;
import checkWords.CheckWords;
import classes.HTTPRequests;
import classes.URLs;
import classes.UserData;
import com.google.gson.Gson;
import customJobCreator.CustomJobResults;
import endScreen.EndWindow;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class baseJob {
    public int version;
    public long startTime;
    public long endTime;
    public List<baseTask> tasks;
    public String fileName="default";
    public String name = "default";

    public String item="default";

    public String type;

    public transient CheckWords controller;

    transient AnchorPane parent;

    private transient Label points;
    final static Random random = new Random();
    transient VBox cont = new VBox();
    public boolean init(AnchorPane parent, ScrollPane SB_hist, Label points, CheckWords controller){
        if (version!=2) return false;
        this.parent = parent;
        this.controller = controller;
        this.points = points;
        cont.setSpacing(10);
        tasks.forEach(baseTask -> {
            HBox task = NewAnchorPane(baseTask.task);
            cont.getChildren().add(task);
            baseTask.setHistory(task);
        });
        SB_hist.setContent(cont);
        startTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
        randomWord();
        return true;
    }

    public void randomWord(){
        if(allCompleted()){
            controller.allCompleted();
            return;
        }
        int numWord =random.nextInt(tasks.size());
        while(tasks.get(numWord).state == baseTask.State.COMPLETE) numWord = random.nextInt(tasks.size());
        tasks.get(numWord).show(parent, points);
    }

    private boolean allCompleted(){
        AtomicBoolean allComplete = new AtomicBoolean(true);
        tasks.forEach(baseTask -> allComplete.set(baseTask.state == jobs.Task.baseTask.State.COMPLETE && allComplete.get()));
        return allComplete.get();
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
}
