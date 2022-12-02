package jobs.Task.RadioTask;

import checkWords.CheckWords;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RadioTask extends baseTask {

    public String[] answers;
    private final transient ToggleGroup radios = new ToggleGroup();
    private final transient List<RadioButtonObrium> radioButtonsList = new ArrayList<>();
    private final transient VBox radioContainer;

    public RadioTask() throws IOException {
        super(FXMLLoader.load(RadioTask.class.getResource("RadioTask.fxml")));
        radioContainer = (VBox) (super.taskPane.lookup("#radioContainer"));
    }

    @Override
    protected void initialize() {
        super.initialize();
        RadioButtonObrium correctRadioButton = new RadioButtonObrium(answers[0], true);
        correctRadioButton.setToggleGroup(radios);
        radioButtonsList.add(correctRadioButton);
        for (int i = 1; i<answers.length; i++) {
            RadioButtonObrium radioButton = new RadioButtonObrium(answers[i], false);
            radioButton.setToggleGroup(radios);
            radioButtonsList.add(radioButton);
        }
    }

    @Override
    public void show(Pane parent, Label pointsLabel) {
        super.show(parent, pointsLabel);
        Collections.shuffle(radioButtonsList);
        radioContainer.getChildren().clear();
        radioButtonsList.forEach(toggle -> radioContainer.getChildren().add(toggle));
    }

    @FXML
    protected void OnAnswer(ActionEvent actionEvent) {
        if (radios.getSelectedToggle() == null) return;
        if(((RadioButtonObrium)radios.getSelectedToggle()).isCorrect()){
            CorrectAnswer();
        } else {
            WrongAnswer(((RadioButtonObrium) radios.getSelectedToggle()).getText(), answers[0]);
        }
        radios.getSelectedToggle().setSelected(false);

    }

}
