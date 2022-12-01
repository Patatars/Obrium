package jobs.Task.RadioTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import jobs.Task.baseTask;

import java.io.IOException;
import java.util.Collections;

public class RadioTask extends baseTask {

    public String[] answers;
    private final transient ToggleGroup radios = new ToggleGroup();
    private final transient VBox radioContainer;

    public RadioTask() throws IOException {
        super(FXMLLoader.load(RadioTask.class.getResource("RadioTask.fxml")));
        radioContainer = (VBox) (super.taskPane.lookup("#radioContainer"));
    }

    @Override
    protected void initialize() {
        for (String answer:answers){
            newRadioButton(answer).setToggleGroup(radios);
        }
        //Collections.shuffle(radios.getToggles());
        radios.getToggles().forEach(toggle -> {
            radioContainer.getChildren().add((Node) toggle);
        });
    }

    private RadioButton newRadioButton(String text){
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(new Font(20));
        return radioButton;
    }

    @Override
    protected void OnAnswerEvent(ActionEvent actionEvent) {

    }
}
