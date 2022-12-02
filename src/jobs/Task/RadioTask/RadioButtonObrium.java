package jobs.Task.RadioTask;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

public class RadioButtonObrium extends RadioButton {
    private final boolean isCorrect;
    public RadioButtonObrium(String text, boolean isCorrectRadio){
        super(text);
        setFont(new Font(35));
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setWrapText(true);
        setAlignment(Pos.TOP_CENTER);
        getStylesheets().add("sources/radio.css");
        isCorrect = isCorrectRadio;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
}
