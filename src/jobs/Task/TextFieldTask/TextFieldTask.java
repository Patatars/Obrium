package jobs.Task.TextFieldTask;

import Starters.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.Objects;


public class TextFieldTask extends baseTask {

    public String answer;


    private final transient TextField answerTextField;

    public TextFieldTask() throws IOException {
        super(FXMLLoader.load(Objects.requireNonNull(TextFieldTask.class.getResource("TextFieldTask.fxml"))));
        answerTextField = (TextField) (super.taskPane.lookup("#Answer"));
        answerTextField.setOnAction(this::OnAnswer);
    }
    public void OnAnswer(ActionEvent actionEvent) {
        if(answerTextField.getText().trim().equals("")) return;
        if(answerTextField.getText().trim().equalsIgnoreCase(answer)){
            CorrectAnswer();
        } else {
            WrongAnswer(answerTextField.getText(), answer);
        }
        answerTextField.setText("");

    }


}
