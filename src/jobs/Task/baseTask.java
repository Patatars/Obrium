package jobs.Task;

import Starters.Main;
import checkWords.CheckWords;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public abstract class baseTask {

    public String task;
    public int repeats;

    public enum State{
        CORRECT,
        INCORRECT,
        COMPLETE
    }
    public transient State state;

    public static transient CheckWords controller;

    protected transient Pane taskPane;
    protected transient String shortTask;
    protected transient int fontSize = 52;
    protected transient int mistakes = 0;
    protected transient int points = 0;
    protected transient HBox history;

    public void setHistory(HBox history) {
        this.history = history;
    }

    public baseTask(Pane root){
        taskPane = root;
    }
    private boolean wasShown;
    public void show(Pane parent, Label pointsLabel){
        if (!wasShown){
            init();
            wasShown = true;
        }
        history.getChildren().forEach(node -> node.setStyle("-fx-font-weight: bolder; -fx-font-style: italic; -fx-font-size: 30"));
        pointsLabel.setText("Баллы: " + points + "/" + repeats);
        parent.getChildren().clear();
        parent.getChildren().add(taskPane);
    }
    protected abstract void initialize();

    private void init(){
        int letters = task.length();
        if(letters>13){
            shortTask = task.substring(0, 13) + "...";
        } else {
            shortTask = task;
        }
        if(letters <= 48){
            fontSize = 52;
        } else if(letters <= 96){
            fontSize = 43;
        } else if(letters <= 192){
            fontSize = 29;
        } else if(letters <= 394){
            fontSize = 21;
        } else {
            fontSize = 35;
        }
        initialize();
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

    protected void OnAnswer(ActionEvent actionEvent){
        history.getChildren().forEach(node -> node.setStyle(""));
        OnAnswerEvent(actionEvent);
        MarkCorrectWord(history, state);
        ((Label)history.getChildren().get(1)).setText(String.valueOf(points));
        controller.randomWord();
    }
    protected abstract void OnAnswerEvent(ActionEvent actionEvent);
}
