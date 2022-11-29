package jobs.Task;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class baseTask {
    protected Pane taskFX;
    protected String task;
    protected int mistakes;
    protected int points;
    public baseTask(Pane root,Node ... nodes){
        taskFX = root;
        for (Node n: nodes) {
            root.getChildren().add(n);
        }
    }
    abstract boolean isCorrectAnswer();
}
