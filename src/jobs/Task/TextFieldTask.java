package jobs.Task;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TextFieldTask extends baseTask {

    public TextFieldTask(){
        super(new VBox());

    }

    @Override
    boolean isCorrectAnswer() {
        return false;
    }
}
