package jobs.Task.InsertWordTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jobs.Task.baseTask;

import java.io.IOException;

public class InsertWordTask extends baseTask {

    public String answer;
    public String firstPartTask;
    public String secondPartTask;

    private final transient TextField answerTextField;
    private final transient Label firstPartTaskLabel;
    private final transient Label secondPartTaskLabel;


    public InsertWordTask() throws IOException {
        super(FXMLLoader.load(InsertWordTask.class.getResource("InsertFieldTask.fxml")));
        answerTextField = (TextField) (super.taskPane.lookup("#Answer"));
        firstPartTaskLabel = (Label) (super.taskPane.lookup("#FirstPartTask"));
        secondPartTaskLabel = (Label) (super.taskPane.lookup("#SecondPartTask"));
        answerTextField.setOnAction(this::OnAnswer);
    }

    @Override
    protected void initialize() {
        super.initialize();
        firstPartTaskLabel.setText(firstPartTask);
        secondPartTaskLabel.setText(secondPartTask);
    }

    @Override
    protected void OnAnswer(ActionEvent actionEvent) {
        if(answerTextField.getText().trim().equals("")) return;
        if(answerTextField.getText().trim().toLowerCase().equals(answer.toLowerCase())){
            CorrectAnswer();
        } else {
            WrongAnswer(answerTextField.getText(), answer);
        }
        answerTextField.setText("");
    }
}
