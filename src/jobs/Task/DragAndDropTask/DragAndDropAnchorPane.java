package jobs.Task.DragAndDropTask;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DragAndDropAnchorPane extends AnchorPane {
    public enum States{
        RESTING,
        ACTIVE,
        BUSY
    }

    private Label currentLabel;
    private final Label correctLabel;

    public States state = States.RESTING;


    public DragAndDropAnchorPane(Label correctLabel){
        super();
        this.correctLabel = correctLabel;
        setDottedStyle("white");
        setPrefWidth(250);
        setPrefHeight(120);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);



    }
    public void setDottedStyle(String color){
        setStyle(String.format("-fx-background-color: black; -fx-border-color: %s; -fx-border-width: 5; -fx-background-radius: 15; -fx-border-style: segments(10, 15, 15, 15)  line-cap round;", color));
    }
    public void setActive(boolean active, DragAndDropLabel currentLabel){
        if (state == States.BUSY) return;
        if (active){
            setDottedStyle("#35008b");
            state = States.ACTIVE;
            currentLabel.setParentPane(this);
        } else{
            setDottedStyle("white");
            state = States.RESTING;
            currentLabel.setParentPane(null);
        }
    }
    public void setLabel(DragAndDropLabel currentLabel){
        if (state == States.RESTING || state == States.BUSY) return;
        this.currentLabel = currentLabel;
        state = States.BUSY;
        AnchorPane.setBottomAnchor(currentLabel, 0d);
        AnchorPane.setLeftAnchor(currentLabel, 0d);
        AnchorPane.setTopAnchor(currentLabel, 0d);
        AnchorPane.setRightAnchor(currentLabel, 0d);
    }
    public void removeLabel(DragAndDropLabel label){
        if (state != States.BUSY) return;
        currentLabel = null;
        state = States.RESTING;
        AnchorPane.clearConstraints(label);

    }
    public boolean isCorrect(){
        return currentLabel == correctLabel;
    }
    public double distanceToLabel(Label currentLabel){
        return Math.sqrt(Math.pow(currentLabel.getLayoutX() + currentLabel.getWidth()/2 - (getBoundsInParent().getMaxX()), 2) + Math.pow(currentLabel.getLayoutY() + currentLabel.getHeight()/2 - (getParent().getBoundsInParent().getMaxY() - getHeight()/2), 2));
    }

}
