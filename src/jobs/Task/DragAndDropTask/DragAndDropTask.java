package jobs.Task.DragAndDropTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jobs.Task.baseTask;

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
    transient double oldX, oldY;
    static double minX, maxX, minY, maxY, x, l4X;

    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(DragAndDropTask.class.getResource("DragAndDropTask.fxml"))));
        l4 = (Label) (super.taskPane.lookup("#4"));
        l5 = (Label) (super.taskPane.lookup("#5"));
        l6 = (Label) (super.taskPane.lookup("#6"));
        a = (AnchorPane) (super.taskPane.lookup("#7"));
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);

        l4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = l4.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = l4.getLayoutY() - mouseEvent.getSceneY();
                l4.setCursor(Cursor.MOVE);
            }
        });
        l4.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                l4.setCursor(Cursor.HAND);
            }
        });
        l4.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                l4.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                l4.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        l4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                l4.setCursor(Cursor.HAND);
            }
        });
    }


    // allow the label to be dragged around.
    final Delta dragDelta = new Delta();



    // records relative x and y co-ordinates.
    static class Delta { double x, y; }

    @Override
    protected void OnAnswer(ActionEvent actionEvent) {

    }
}
