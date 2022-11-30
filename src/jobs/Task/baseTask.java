package jobs.Task;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;



public abstract class baseTask {
    protected enum State{
        CORRECT,
        INCORRECT,
        COMPLETE
    }
    transient protected Pane taskPane;
    protected String task;
    transient protected int mistakes = 0;
    public transient State state;
    public baseTask(Pane root){
        taskPane = root;
    }
    public abstract boolean isCorrectAnswer();
    public void show(Pane parent){
        parent.getChildren().clear();
        parent.getChildren().add(taskPane);
    }

    public abstract void OnAnswer(ActionEvent actionEvent);
}
