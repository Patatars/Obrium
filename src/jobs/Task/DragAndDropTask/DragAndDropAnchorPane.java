package jobs.Task.DragAndDropTask;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class DragAndDropAnchorPane extends AnchorPane {

    private Label currentLabel;
    private final Label correctLabel;
    public boolean isBusy = false;
    private boolean isActive = false;

    public DragAndDropAnchorPane(Label correctLabel){
        super();
        this.correctLabel = correctLabel;
        setStyle(getDottedStyle("white"));
        setPrefHeight(200);

    }
    private String getDottedStyle(String color){
        return String.format("-fx-background-color: black; -fx-border-color: %s; -fx-border-width: 5; -fx-background-radius: 15; -fx-border-style: segments(10, 15, 15, 15)  line-cap round;", color);
    }
    public void setActive(){
        if (isBusy) return;
        setStyle(getDottedStyle("#35008b"));
        isActive = true;

    }
    public boolean setLabel(Label currentLabel){
        if (isBusy || !isActive) return false;
        this.currentLabel = currentLabel;
        isBusy = true;
        isActive = false;
        return true;
    }
    public boolean removeLabel(){
        if (!isBusy || isActive) return false;
        currentLabel = null;
        isBusy = false;
        return true;
    }
    public boolean isCorrect(){
        return currentLabel == correctLabel;
    }
    public double distanceToLabel(Label currentLabel){
        return Math.sqrt(Math.pow(currentLabel.getLayoutX() + currentLabel.getWidth()/2 - (getBoundsInParent().getMaxX() + getWidth()/2), 2) + Math.pow(currentLabel.getLayoutY() + currentLabel.getHeight()/2 - (getBoundsInParent().getMinY() + getHeight()/2), 2));
    }

}
