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
import jobs.Task.baseTask;

import java.io.IOException;


public class TextFieldTask extends baseTask {

//    private final Label taskLabel;
//    private final TextField answerField;
    public String answer;
    public int repeats;
    private transient int points = 0;
    private final transient Label taskLabel;
    private final transient TextField answerTextField;
    private final transient Button doneButton;

    public TextFieldTask() throws IOException {
        super(FXMLLoader.load(TextFieldTask.class.getResource("TextFieldTask.fxml")));
        taskLabel = (Label)(super.taskPane.getChildren().get(0));
        answerTextField = (TextField) (super.taskPane.getChildren().get(1));
        doneButton = (Button) ((StackPane)(super.taskPane.getChildren().get(3))).getChildren().get(0);
        doneButton.setOnAction(this::OnAnswer);
    }
    @Override
    public boolean isCorrectAnswer() {
        return false;
    }

    @Override
    public void OnAnswer(ActionEvent actionEvent) {
        if(answerTextField.getText().trim().equals("")) return;
        if(answerTextField.getText().trim().toLowerCase().equals(answer.toLowerCase())){
            points++;
            answerTextField.setText("");
            state = points == repeats ? State.COMPLETE : State.CORRECT
            ((Label)anchorPanes[numWord].getChildren().get(1)).setText(String.valueOf(points));
            MarkCorrectWord(anchorPanes[numWord], points[numWord]== needPoints ? State.COMPLETE : State.CORRECT);
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

            ((Label)anchorPanes[numWord].getChildren().get(1)).setText(String.valueOf(points));
            ans.setText("");
            MarkCorrectWord(anchorPanes[numWord], State.INCORRECT);
            mistakes++;
        }
        SB_hist.setContent(cont);
        randomWord();
    }


}
