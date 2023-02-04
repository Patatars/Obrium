package jobs.Task;

import Starters.Main;
import checkWords.CheckWords;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jobs.Task.DragAndDropTask.DragAndDropAnchorPane;
import jobs.Work.baseJob;

import java.util.Collections;
import java.util.List;


public abstract class baseTask {

    public String task;
    public int repeats;

    public enum State{
        CORRECT,
        INCORRECT,
        COMPLETE,
        EMPTY
    }
    public transient State state = State.EMPTY;

    public static baseJob controller;

    protected final transient Label taskLabel;
    protected transient Pane taskPane;
    protected transient String shortTask;
    protected transient int fontSize = 52;
    public transient int mistakes = 0;
    protected transient int points = 0;
    protected transient HBox history;

    private boolean wasShown;

    public baseTask(Pane root){
        taskPane = root;
        taskLabel = (Label)(taskPane.lookup("#Task"));
        ((Button) (taskPane.lookup("#Done"))).setOnAction(this::OnAnswer);
    }

    protected abstract void OnAnswer(ActionEvent actionEvent);

    public void setHistory(HBox history) {
        this.history = history;
    }

    public void show(Pane parent, Label pointsLabel){
        if (!wasShown){
            initialize();
            wasShown = true;
        }
        history.getChildren().forEach(node -> node.setStyle("-fx-font-weight: bolder; -fx-font-style: italic; -fx-font-size: 30"));
        pointsLabel.setText("Баллы: " + points + "/" + repeats);
        parent.getChildren().clear();
        parent.getChildren().add(taskPane);
        AnchorPane.setBottomAnchor(taskPane, 0d);
        AnchorPane.setTopAnchor(taskPane, 0d);
        AnchorPane.setLeftAnchor(taskPane, 0d);
        AnchorPane.setRightAnchor(taskPane, 0d);
    }

    protected void initialize(){
        int letters = task.length();
        if(letters>13){
            shortTask = task.substring(0, 13) + "...";
        } else {
            shortTask = task;
        }
        taskLabel.setText(task);
        fontSize = getFontSize(letters);
        taskLabel.setFont(new Font(fontSize));
    }

    protected int getFontSize(int numLetters){
        if(numLetters <= 48){
            return  52;
        } else if(numLetters <= 96){
            return  43;
        } else if(numLetters <= 192){
            return  29;
        } else if(numLetters <= 394){
            return  21;
        } else {
            return  35;
        }
    }

    protected void MarkCorrectWord(HBox pane , baseTask.State state){
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
    protected void CorrectAnswer(){
        points++;
        state = points == repeats ? State.COMPLETE : State.CORRECT;
        Answered();
    }
    protected void WrongAnswer(String answer, String correctAnswer){
        ShowWrongAnswer(answer.length() > 50 ? answer.substring(0, 50) + "..." : answer, correctAnswer);
    }
    protected void WrongAnswer(String correctAnswer){
        ShowWrongAnswer(null, correctAnswer);
    }
    private void ShowWrongAnswer(String answer, String correctAnswer){
        if(controller.type.equals("controlJob")){
            mistakes++;
            state = State.COMPLETE;
            controller.randomWord();
            return;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initOwner(Main.primaryStage);
        a.getDialogPane().getStylesheets().add("sources/Dialog.css");
        a.setTitle("Неверный ответ");
        a.setHeaderText("Правильный ответ: " + correctAnswer);
        a.setContentText(answer==null ? "" : String.format("Ваш ответ: %s", answer));
        a.showAndWait();
        points = 0;
        state = State.INCORRECT;
        mistakes++;
        Answered();
    }

    private void Answered(){
        history.getChildren().forEach(node -> node.setStyle(""));
        MarkCorrectWord(history, state);
        ((Label)history.getChildren().get(1)).setText(String.valueOf(points));
        controller.randomWord();
    }
}
