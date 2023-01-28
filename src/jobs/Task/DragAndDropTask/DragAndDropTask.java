package jobs.Task.DragAndDropTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DragAndDropTask extends baseTask {


    private final transient VBox problemsContainer;
    private final transient HBox labelsContainer;

    private final transient AnchorPane dragArea;

    private final transient List<DragAndDropAnchorPane> anchorPanes = new ArrayList<>();
    private final transient List<DragAndDropLabel> labels = new ArrayList<>();

    public Map<String, String> problems;


    public DragAndDropTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(DragAndDropTask.class.getResource("DragAndDropTask.fxml"))));
        problemsContainer = (VBox) (taskPane.lookup("#problemsContainer"));
        dragArea = (AnchorPane) (taskPane.lookup("#dragArea"));
        labelsContainer = (HBox) (taskPane.lookup("#LabelsContainer"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        problems.forEach((label, answer) -> {
            VBox labelContainer = new VBox();
            DragAndDropLabel dragAndDropLabel = new DragAndDropLabel(answer, dragArea, labelContainer, labelsContainer, anchorPanes);
            labels.add(dragAndDropLabel);
            labelContainer.getChildren().add(dragAndDropLabel);
            labelsContainer.getChildren().add(labelContainer);
            dragAndDropLabel.setFont(new Font(Math.round(getFontSize(answer.length())/2.5f)));

            HBox problemContainer = new HBox();
            problemContainer.setSpacing(50);
            problemContainer.setAlignment(Pos.CENTER_RIGHT);
            Label problemLabel = new Label(label);
            problemLabel.setTextFill(Color.WHITE);
            problemLabel.setFont(new Font(Math.round(getFontSize(label.length())/2f)));
            problemContainer.getChildren().add(problemLabel);

            DragAndDropAnchorPane dragAndDropAnchorPane = new DragAndDropAnchorPane(dragAndDropLabel);
            anchorPanes.add(dragAndDropAnchorPane);
            problemContainer.getChildren().add(dragAndDropAnchorPane);
            problemsContainer.getChildren().add(problemContainer);
        });
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);
        labels.forEach(DragAndDropLabel::RemoveFromAnchorPane);
        anchorPanes.forEach(dragAndDropAnchorPane -> dragAndDropAnchorPane.setDottedStyle("white"));
    }





    @Override
    protected void OnAnswer(ActionEvent actionEvent) {
        final boolean[] correct = {true};
        final boolean[] hasEmpty = {false};
        anchorPanes.forEach(dragAndDropAnchorPane -> {
            if(dragAndDropAnchorPane.state != DragAndDropAnchorPane.States.BUSY) {
                hasEmpty[0] = true;
            }
        });
        if (hasEmpty[0]) return;
        anchorPanes.forEach(dragAndDropAnchorPane -> {
            if (!dragAndDropAnchorPane.isCorrect()) {
                correct[0] = false;
            }
            dragAndDropAnchorPane.setDottedStyle(dragAndDropAnchorPane.isCorrect() ? "green" : "red");
        });
        if (correct[0]){
            CorrectAnswer();
        } else {
            final String[] wrongAnswer = {"\n\n"};
            problems.forEach((s, s2) -> {
                wrongAnswer[0]+= s + " - " + s2 + "\n";
            });
            WrongAnswer(wrongAnswer[0]);
        }
    }
}
