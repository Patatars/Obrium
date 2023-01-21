package jobs.Task.DragAndDropTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import jobs.Task.baseTask;
import sun.nio.ch.ThreadPool;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class DragAndDropTask extends baseTask {

//    private final transient Label l1;
//    private final transient Label l2;
//    private final transient Label l3;
    private final transient Label l4;
    private final transient Label l5;
    private final transient Label l6;
    private final transient VBox anchorPanesContainer;
    private final transient AnchorPane labelsContainer;
    private final transient AnchorPane dragArea;

    private final transient List<DragAndDropAnchorPane> anchorPanes = new ArrayList<>();


    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(DragAndDropTask.class.getResource("DragAndDropTask.fxml"))));

        l4 = (Label) (super.taskPane.lookup("#4"));
        l5 = (Label) (super.taskPane.lookup("#5"));
        l6 = (Label) (super.taskPane.lookup("#6"));
        labelsContainer = (AnchorPane) (taskPane.lookup("#LabelsContainer"));
        dragArea = (AnchorPane) (taskPane.lookup("#dragArea"));
        anchorPanes.add(new DragAndDropAnchorPane(l4));
        anchorPanes.add(new DragAndDropAnchorPane(l5));
        anchorPanesContainer = (VBox) taskPane.lookup("#anchorPanesContainer");
        anchorPanes.forEach(dragAndDropAnchorPane -> anchorPanesContainer.getChildren().add(dragAndDropAnchorPane));

    }
    final Bounds[] bounds = new Bounds[1];
    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);

        l4.setOnMousePressed(mouseEvent -> {
            labelsContainer.getChildren().remove(l4);
            dragArea.getChildren().add(l4);
            dragDelta.x = l4.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = l4.getLayoutY() - mouseEvent.getSceneY();
            bounds[0] = taskPane.getBoundsInLocal();
        });


        l4.setOnMouseReleased(mouseEvent -> {
            l4.setCursor(Cursor.HAND);
            dragArea.getChildren().remove(l4);
            labelsContainer.getChildren().add(l4);
        });
        l4.setOnMouseDragged(mouseEvent -> {
            moveX(l4, mouseEvent.getSceneX());
            moveY(l4, mouseEvent.getSceneY());

        });

    }



    void moveX(Label node, double cursorLocation){
        final boolean atRightBorder = node.getLayoutX() >= (taskPane.getWidth() - node.getWidth());
        if (atRightBorder){
            if (cursorLocation + dragDelta.x >= node.getLayoutX()) return;
        }
        final boolean atLeftBorder = node.getLayoutX() <= 0;
        if (atLeftBorder){
            if (cursorLocation + dragDelta.x <= node.getLayoutX()) return;
        }
        node.setLayoutX(cursorLocation + dragDelta.x);
    }

    void moveY(Label node, double cursorLocation){
        final boolean atBottomBorder = node.getLayoutY() >= (taskPane.getHeight() - node.getHeight());
        if (atBottomBorder){
            if (cursorLocation + dragDelta.y >= node.getLayoutY()) return;
        }
        final boolean atTopBorder = node.getLayoutY() <= 0;
        if (atTopBorder){
            if (cursorLocation + dragDelta.y <= node.getLayoutY()) return;
        }
        node.setLayoutY(cursorLocation + dragDelta.y);
    }




    final Delta dragDelta = new Delta();



    static class Delta { double x, y; }

    @Override
    protected void OnAnswer(ActionEvent actionEvent) {

    }
}
