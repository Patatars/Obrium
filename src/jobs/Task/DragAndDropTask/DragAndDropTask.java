package jobs.Task.DragAndDropTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import jobs.Task.baseTask;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class DragAndDropTask extends baseTask {

//    private final transient Label l1;
//    private final transient Label l2;
//    private final transient Label l3;
    private final transient Label l4;
    private final transient Label l5;
    private final transient Label l6;
    private final transient AnchorPane a;


    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(DragAndDropTask.class.getResource("DragAndDropTask.fxml"))));

        l4 = (Label) (super.taskPane.lookup("#4"));
        l5 = (Label) (super.taskPane.lookup("#5"));
        l6 = (Label) (super.taskPane.lookup("#6"));
        a = (AnchorPane) (super.taskPane.lookup("#7"));
    }
    final Bounds[] bounds = new Bounds[1];
    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);

        l4.setOnMousePressed(mouseEvent -> {
            dragDelta.x = l4.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = l4.getLayoutY() - mouseEvent.getSceneY();
            bounds[0] = taskPane.getBoundsInLocal();
            System.out.println(a.getTranslateX());
        });
        l4.setOnMouseReleased(mouseEvent -> l4.setCursor(Cursor.HAND));
        l4.setOnMouseDragged(mouseEvent -> {
            moveX(l4, mouseEvent.getSceneX());
            moveY(l4, mouseEvent.getSceneY());
            if(Math.sqrt(Math.pow(l4.getLayoutX() - (a.getLayoutX() + a.getWidth()/2), 2) + Math.pow(l4.getLayoutY() - (a.getLayoutY()+a.getHeight()/2), 2)) <= 10){

            } else {
                l4.setText("B");
            }
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
