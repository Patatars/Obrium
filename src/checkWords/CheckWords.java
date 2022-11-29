package checkWords;

import classes.*;
import com.google.gson.Gson;
import com.sun.glass.ui.Size;
import customJobCreator.CustomJobResults;
import endScreen.EndWindow;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Starters.Main;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckWords implements CallableFromScenesManager {

    public static FileData fileData;

    private String[] words;
    private String[] translates;
    private int needPoints;
    private int numWord = 0;

    private int LastAnswerType = -1;

    String[] shortWords;
    private int[] points;

    private int[] fontSize;

    private boolean allComplete;


    private HBox[] anchorPanes;

    @FXML
    public VBox TaskContainer;

    @FXML
    private ScrollPane SB_hist;

    @FXML
    private TextArea BigAnswer;

    @FXML
    private TextField Answer;

    @FXML
    private Label Points;


    @FXML
    private Label Word;

    public enum State{
        CORRECT,
        INCORRECT,
        COMPLETE
    }


    VBox cont = new VBox();

    @FXML
    void initialize() {

    }

    @Override
    public void init(boolean b) {
        if(!b){
            BigAnswer.setText("");
            Answer.setText("");
            cont.getChildren().clear();
            allComplete = false;
            numWord = 0;
        }
        words = fileData.words;
        translates = fileData.translates;
        needPoints = fileData.repeats;
        points = fileData.points;
        fileData.mistakes = new HashMap<>();
        fontSize = new int[words.length];
        shortWords = new String[words.length];
        for (String word: words) {
            fileData.mistakes.put(word, 0);
        }
        for (int i=0; i<words.length; i++){
            int letters = words[i].length();
            if(letters>13){
                shortWords[i] = words[i].substring(0, 13) + "...";
            } else {
                shortWords[i] = words[i];
            }
            if(letters <= 48){
                fontSize[i] = 52;
            } else if(letters <= 96){
                fontSize[i] = 43;
            } else if(letters <= 192){
                fontSize[i] = 29;
            } else if(letters <= 394){
                fontSize[i] = 21;
            } else {
                fontSize[i] = 35;
            }

        }

        anchorPanes = new HBox[words.length];
        cont.setSpacing(10);
        for(int i = 0; i<words.length; i++){
            HBox word = NewAnchorPane(words[i]);
            cont.getChildren().add(word);
            SB_hist.setContent(cont);
            anchorPanes[i] = word;
        }
        Answer.setOnAction(this::GotAnswer);
        fileData.startTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
        randomWord();
    }




    @FXML
    void GotAnswer(ActionEvent event) {
        TextInputControl ans = fileData.AnswerType[numWord] == FileData.SHORT_ANSWER ? Answer : BigAnswer;
        if(ans.getText().trim().equals("")) return;
        if(ans.getText().trim().toLowerCase().equals(translates[numWord].toLowerCase())){
            points[numWord]++;
            ans.setText("");
            ((Label)anchorPanes[numWord].getChildren().get(1)).setText(String.valueOf(points[numWord]));
            MarkCorrectWord(anchorPanes[numWord], points[numWord]== needPoints ? State.COMPLETE : State.CORRECT);
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.initOwner(Main.primaryStage);
            a.getDialogPane().getStylesheets().add("sources/Dialog.css");
            String contentText = ans.getText().length() > 50 ? ans.getText().substring(0, 50) + "..." : ans.getText();
            a.setContentText("Вы написали: " + contentText);
            a.setTitle("Неверный ответ");
            a.setHeaderText("Правильный ответ: " + translates[numWord]);
            a.showAndWait();
            points[numWord] = 0;
            ((Label)anchorPanes[numWord].getChildren().get(1)).setText(String.valueOf(points[numWord]));
            ans.setText("");
            MarkCorrectWord(anchorPanes[numWord], State.INCORRECT);
            fileData.mistakes.put(words[numWord],  fileData.mistakes.get(words[numWord]) + 1);
        }
        SB_hist.setContent(cont);
        randomWord();

    }

    Node node0;
    Node node1;


    void randomWord(){
        numWord = (int)(Math.random()*words.length);
        for (int j : points) {
            allComplete = j == needPoints;
            if (!allComplete) {
                break;
            }
        }
        if(allComplete){
            try {
                Gson gson = new Gson();
                fileData.endTime = Long.parseLong(HTTPRequests.postRequest(URLs.GET_TIME.toString(), "", true));
                System.out.println(fileData.name);
                if(fileData.fileName.equals("default") && fileData.item.equals("default") && fileData.name.equals("default")){
                    CustomJobResults.info=fileData;
                    ScenesManager.setScene(Main.primaryStage, Scenes.customJobResult);
                    return;
                }
                String ans = HTTPRequests.postRequest(URLs.ALLCOMPLETE_URL.toString(), "username=" + UserData.user.username + "&password=" + UserData.user.password + "&item=" + fileData.item + "&filename=" + fileData.fileName + "&mistakes=" + gson.toJson(fileData.mistakes) + "&startTime=" + fileData.startTime + "&endTime=" + fileData.endTime, true);
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
        boolean oknum = points[numWord] < needPoints;
        while(!oknum){
            numWord = (int)(Math.random()*words.length);
            oknum = points[numWord] < needPoints;
        }
        Points.setText(points[numWord] + " баллов \nИз " + needPoints + " баллов");
        Word.setText(words[numWord]);
        if(node0!=null){
            node0.setStyle("");
            node1.setStyle("");
        }
        node0 = anchorPanes[numWord].getChildren().get(0);
        node1 = anchorPanes[numWord].getChildren().get(1);
        node0.setStyle("-fx-font-weight: bolder; -fx-font-style: italic; -fx-font-size: 30");
        node1.setStyle("-fx-font-weight: bolder;-fx-font-style: italic; -fx-font-size: 30");
        Word.setFont(new Font("Times New Roman", fontSize[numWord]));
        if(fileData.AnswerType[numWord] == FileData.SHORT_ANSWER && LastAnswerType != FileData.SHORT_ANSWER) {
            Answer.setVisible(true);
            Answer.requestFocus();
            BigAnswer.setVisible(false);
            TaskContainer.getChildren().add(1, Answer);
            TaskContainer.getChildren().remove(BigAnswer);
            LastAnswerType = FileData.SHORT_ANSWER;
        } else if(fileData.AnswerType[numWord] == FileData.LONG_ANSWER && LastAnswerType != FileData.LONG_ANSWER){
            Answer.setVisible(false);
            BigAnswer.setVisible(true);
            BigAnswer.requestFocus();
            TaskContainer.getChildren().add(1, BigAnswer);
            TaskContainer.getChildren().remove(Answer);
            LastAnswerType = FileData.LONG_ANSWER;
        }


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

    void MarkCorrectWord(HBox pane , State state){
        switch (state){
            case CORRECT:
                ((Label) pane.getChildren().get(0)).setTextFill(Color.rgb(118, 255, 118));
                ((Label) pane.getChildren().get(1)).setTextFill(Color.rgb(118, 255, 118));
                break;
            case INCORRECT:
                ((Label) pane.getChildren().get(0)).setTextFill(Color.rgb(255, 98, 98));
                ((Label) pane.getChildren().get(1)).setTextFill(Color.rgb(255, 98, 98));
                break;
            case COMPLETE:
                ((Label) pane.getChildren().get(0)).setTextFill(Color.rgb(100, 100, 100));
                ((Label) pane.getChildren().get(1)).setTextFill(Color.rgb(100, 100, 100));
                break;

        }
    }

    public void back() throws IOException {
        fileData = null;
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
    }


}


