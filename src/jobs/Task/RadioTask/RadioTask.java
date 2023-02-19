package jobs.Task.RadioTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RadioTask extends baseTask {

    public String[] answers;
    public String correctAnswer;

    private final transient ToggleGroup radios = new ToggleGroup();
    private final transient List<RadioButtonObrium> radioButtonsList = new ArrayList<>();
    private final transient VBox radioContainer;

    public RadioTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(RadioTask.class.getResource("RadioTask.fxml"))));
        radioContainer = (VBox) (super.taskPane.lookup("#radioContainer"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        RadioButtonObrium correctRadioButton = new RadioButtonObrium(correctAnswer, true, getFontSize(correctAnswer.length())/2d);
        correctRadioButton.setToggleGroup(radios);
        radioButtonsList.add(correctRadioButton);
        for (String answer: answers) {
            RadioButtonObrium radioButton = new RadioButtonObrium(answer, false, getFontSize(answer.length())/2d);
            radioButton.setToggleGroup(radios);
            radioButtonsList.add(radioButton);

        }
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);
        Collections.shuffle(radioButtonsList);
        radioContainer.getChildren().clear();
        radioButtonsList.forEach(toggle -> {
            toggle.setFont(new Font(1));
            radioContainer.getChildren().add(toggle);
            toggle.setTextFill(Color.BLACK);
        });
    }

    @FXML
    protected void OnAnswer(ActionEvent actionEvent) {
        if (radios.getSelectedToggle() == null) return;
        if(((RadioButtonObrium)radios.getSelectedToggle()).isCorrect()){
            CorrectAnswer();
        } else {
            radioButtonsList.forEach(radioButtonObrium -> {
                if(radioButtonObrium.isCorrect()){
                    radioButtonObrium.setTextFill(Color.GREENYELLOW);
                }
            });
            ((RadioButtonObrium) radios.getSelectedToggle()).setTextFill(Color.RED);
            WrongAnswer(((RadioButtonObrium) radios.getSelectedToggle()).getText(), correctAnswer);
        }
        radios.getSelectedToggle().setSelected(false);


    }

}
