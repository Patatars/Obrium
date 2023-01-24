package jobs.Task.DragAndDropTask;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.scene.paint.Color.WHITE;

public class DragAndDropLabel extends Label {
    private final Pane taskPane;
    private DragAndDropAnchorPane parentPane;
    private boolean isToHome = false;

    public DragAndDropLabel(String label, AnchorPane dragArea, VBox labelContainer, HBox labelsContainer, List<DragAndDropAnchorPane> anchorPanes, Pane taskPane){
        super(label);
        this.taskPane = taskPane;
        setFont(new Font(50));
        setTextFill(WHITE);
        setAlignment(Pos.CENTER);
        setWrapText(true);

        setOnMousePressed(mouseEvent -> {
            if(mouseEvent.isMiddleButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
            if (parentPane != null){
                parentPane.removeLabel(this);
                parentPane.getChildren().remove(this);
                labelContainer.getChildren().add(this);
                parentPane.setActive(false, this);
                isToHome = true;
                System.out.println(123);
            }else{
                labelContainer.getChildren().remove(this);
                dragArea.getChildren().add(this);
            }
            setLayoutX(labelContainer.getBoundsInParent().getMaxX() - getWidth());
            setLayoutY(labelsContainer.getLayoutY());
            dragDelta.x = getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = getLayoutY() - mouseEvent.getSceneY();
        });
        AtomicReference<DragAndDropAnchorPane> nearestPane = new AtomicReference<>();
        setOnMouseReleased(mouseEvent -> {
            if(mouseEvent.isMiddleButtonDown() || mouseEvent.isSecondaryButtonDown() || mouseEvent.isPrimaryButtonDown()) return;
            if(isToHome){
                isToHome = false;
                return;
            }
            setCursor(Cursor.HAND);
            if (parentPane != null){
                parentPane.setLabel(this);
                dragArea.getChildren().remove(this);
                parentPane.getChildren().add(this);
            }else{
                dragArea.getChildren().remove(this);
                labelContainer.getChildren().add(this);
            }
            nearestPane.set(null);
        });


        setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.isMiddleButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
            if (isToHome){
                return;
            }
            moveX(this, mouseEvent.getSceneX());
            moveY(this, mouseEvent.getSceneY());
            final double[] minDistance = {999};
            if (nearestPane.get() != null) nearestPane.get().setActive(false, this);
            anchorPanes.forEach(dragAndDropAnchorPane -> {
                double distance = dragAndDropAnchorPane.distanceToLabel(this);
                if(distance < minDistance[0]){
                    minDistance[0] = distance;
                    nearestPane.set(dragAndDropAnchorPane);
                }
            });
            if (minDistance[0] >= 150) return;
            if (nearestPane.get() != null)
                nearestPane.get().setActive(true, this);
        });
    }
    private void moveX(Label node, double cursorLocation){
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

    private void moveY(Label node, double cursorLocation){
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

    void setParentPane(DragAndDropAnchorPane parentPane){
        this.parentPane = parentPane;
    }




    private final Delta dragDelta = new Delta();



    private static class Delta { double x, y; }

}
