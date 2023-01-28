package jobs.Task.DragAndDropTask;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.scene.paint.Color.WHITE;

public class DragAndDropLabel extends Label {
    private final AnchorPane dragArea;
    private final VBox labelContainer;
    private DragAndDropAnchorPane parentPane;
    private boolean isToHome = false;



    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param label Текст
     * @param dragArea AnchorPane, в котором перетаскиваются ноды
     * @param labelContainer Контейнер для данной label
     * @param labelsContainer Контейнер для labelContainers
     * @param anchorPanes Список ячеек
     */
    public DragAndDropLabel(String label, AnchorPane dragArea, VBox labelContainer, HBox labelsContainer, List<DragAndDropAnchorPane> anchorPanes){
        super(label);
        this.dragArea = dragArea;
        this.labelContainer = labelContainer;
        setTextFill(WHITE);
        setAlignment(Pos.CENTER);
        setWrapText(false);
        setCursor(Cursor.HAND);
        setStyle("-fx-background-color: #35008b; -fx-border-radius: 10; -fx-border-width: 5; -fx-border-color: black; -fx-background-radius: 20; -fx-padding: 1 5 1 5;");

        setOnMousePressed(mouseEvent -> {
            if(mouseEvent.isMiddleButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
            if (parentPane != null){
                RemoveFromAnchorPane();
                isToHome = true;
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
        final boolean atRightBorder = node.getLayoutX() >= (dragArea.getWidth() - node.getWidth());
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
        final boolean atBottomBorder = node.getLayoutY() >= (dragArea.getHeight() - node.getHeight());
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

    public void RemoveFromAnchorPane(){
        if(parentPane!=null) {
            parentPane.removeLabel(this);
            parentPane.getChildren().remove(this);
            labelContainer.getChildren().add(this);
            parentPane.setActive(false, this);
        }
    }



    private final Delta dragDelta = new Delta();



    private static class Delta { double x, y; }

}
