package jobs.Task.CheckBoxTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.*;

public class CheckBoxTask extends baseTask {

    public String[] answers;
    public String[] correctAnswers;
    private final transient List<CheckBoxObrium> checkBoxesList = new ArrayList<>();
    private final transient VBox checkBoxContainer;

    public CheckBoxTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(CheckBoxTask.class.getResource("CheckBoxTask.fxml"))));
        checkBoxContainer = (VBox) (super.taskPane.lookup("#checkBoxContainer"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        for (String correctAnswer : correctAnswers) {
            CheckBoxObrium checkBoxButton = new CheckBoxObrium(correctAnswer, true, getFontSize(correctAnswer.length())/2d);
            checkBoxesList.add(checkBoxButton);
        }
        for (String answer: answers) {
            System.out.println(answer + " " + answer.length());
            CheckBoxObrium checkBoxButton = new CheckBoxObrium(answer, false, getFontSize(answer.length())/2d);
            checkBoxesList.add(checkBoxButton);
        }
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);
        Collections.shuffle(checkBoxesList);
        checkBoxContainer.getChildren().clear();
        checkBoxesList.forEach(toggle -> checkBoxContainer.getChildren().add(toggle));
    }

    @Override
    protected void OnAnswer(ActionEvent actionEvent) {
        final boolean[] answered = {false};
        checkBoxesList.forEach(checkBoxObrium -> {
            answered[0] = answered[0] || checkBoxObrium.isSelected();
        });
        if (!answered[0]) return;
        final boolean[] isCorrect = {true};
        checkBoxesList.forEach(checkBoxObrium -> {
            if(checkBoxObrium.isSelected()) isCorrect[0] = checkBoxObrium.isCorrect() && isCorrect[0];
            else isCorrect[0] = isCorrect[0] && !checkBoxObrium.isCorrect();
            if(checkBoxObrium.isCorrect())checkBoxObrium.setTextFill(Color.GREENYELLOW);
            else checkBoxObrium.setTextFill(Color.RED);
        });
        if(isCorrect[0]){
            CorrectAnswer();
        } else {
            WrongAnswer(Arrays.toString(correctAnswers));
        }
        checkBoxesList.forEach(checkBoxObrium -> {
            checkBoxObrium.setSelected(false);
            checkBoxObrium.setTextFill(Color.BLACK);
        });

    }
}
