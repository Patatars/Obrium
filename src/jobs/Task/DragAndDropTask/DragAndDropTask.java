package jobs.Task.DragAndDropTask;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class DragAndDropTask extends baseTask {

//    private final transient Label l1;
//    private final transient Label l2;
//    private final transient Label l3;
    private final transient Label l4;
    private final transient Label l5;
    private final transient Label l6;
    private final transient AnchorPane a;
    transient double oldX, oldY;

    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(DragAndDropTask.class.getResource("DragAndDropTask.fxml")));
        l4 = (Label) (super.taskPane.lookup("#4"));
        l5 = (Label) (super.taskPane.lookup("#5"));
        l6 = (Label) (super.taskPane.lookup("#6"));
        a = (AnchorPane) (super.taskPane.lookup("#7"));
        l4.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                oldX = l4.getTranslateX() - event.getSceneX();
                oldY = l4.getTranslateY() - event.getSceneY();
            }
        });

        l4.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                l4.setTranslateX(oldX + event.getSceneX());
                l4.setTranslateY(oldY + event.getSceneY());
            }
        });


    }


    @Override
    protected void OnAnswer(ActionEvent actionEvent) {

    }
}
