package jobs.Task.CheckBoxTask;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class CheckBoxObrium extends CheckBox {
    private final boolean isCorrect;
    public CheckBoxObrium(String text, boolean isCorrectRadio){
        super(text);
        setFont(new Font(50));
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setWrapText(true);
        setAlignment(Pos.CENTER);
        getStylesheets().add("sources/checkBox.css");
        isCorrect = isCorrectRadio;
    }
    public boolean isCorrect() {
        return isCorrect;
    }


}
