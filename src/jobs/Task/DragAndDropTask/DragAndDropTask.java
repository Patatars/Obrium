package jobs.Task.DragAndDropTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DragAndDropTask extends baseTask {

//    private final transient Label l1;
//    private final transient Label l2;
//    private final transient Label l3;

    private final transient VBox anchorPanesContainer;
    private final transient VBox labelContainer;
    private final transient VBox labelContainer1;
    private final transient HBox labelsContainer;
    private final transient HBox ggg;
    private final transient HBox ggg1;
    private final transient AnchorPane dragArea;

    private final transient List<DragAndDropAnchorPane> anchorPanes = new ArrayList<>();


    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(DragAndDropTask.class.getResource("DragAndDropTask.fxml"))));

        labelContainer = (VBox) (taskPane.lookup("#LabelContainer"));
        labelContainer1 = (VBox) (taskPane.lookup("#LabelContainer1"));
        ggg = (HBox) (taskPane.lookup("#ggg"));
        ggg1 = (HBox) (taskPane.lookup("#ggg1"));
        labelsContainer = (HBox) (taskPane.lookup("#LabelsContainer"));
        dragArea = (AnchorPane) (taskPane.lookup("#dragArea"));
        DragAndDropLabel dr = new DragAndDropLabel("gr", dragArea, labelContainer, labelsContainer, anchorPanes, taskPane);
        DragAndDropLabel dr1 = new DragAndDropLabel("ve", dragArea, labelContainer1, labelsContainer, anchorPanes, taskPane);
        labelContainer.getChildren().add(dr);
        labelContainer1.getChildren().add(dr1);
        anchorPanes.add(new DragAndDropAnchorPane(dr));
        anchorPanes.add(new DragAndDropAnchorPane(dr1));
        anchorPanesContainer = (VBox) taskPane.lookup("#anchorPanesContainer");
        ggg.getChildren().add(anchorPanes.get(0));
        ggg1.getChildren().add(anchorPanes.get(1));
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);

    }





    @Override
    protected void OnAnswer(ActionEvent actionEvent) {

    }
}
