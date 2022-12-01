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


public class TextFieldTask extends baseTask {

    public String answer;


    private final transient TextField answerTextField;

    public TextFieldTask() throws IOException {
        super(FXMLLoader.load(TextFieldTask.class.getResource("TextFieldTask.fxml")));
        answerTextField = (TextField) (super.taskPane.lookup("#Answer"));
        answerTextField.setOnAction(this::OnAnswer);
    }
    @Override
    protected void initialize() {

    }
    @Override
    public void OnAnswerEvent(ActionEvent actionEvent) {
        if(answerTextField.getText().trim().equals("")) return;
        if(answerTextField.getText().trim().toLowerCase().equals(answer.toLowerCase())){
            points++;
            answerTextField.setText("");
            state = points == repeats ? State.COMPLETE : State.CORRECT;
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.initOwner(Main.primaryStage);
            a.getDialogPane().getStylesheets().add("sources/Dialog.css");
            String contentText = answerTextField.getText().length() > 50 ? answerTextField.getText().substring(0, 50) + "..." : answerTextField.getText();
            a.setContentText("Вы написали: " + contentText);
            a.setTitle("Неверный ответ");
            a.setHeaderText("Правильный ответ: " + answer);
            a.showAndWait();
            points = 0;
            state = State.INCORRECT;
            mistakes++;
        }
        answerTextField.setText("");


    }


}