package customJobCreator;

import Starters.Main;
import checkWords.CheckWords;
import classes.CallableFromScenesManager;
import classes.FileData;
import com.google.gson.Gson;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class CustomJobCreator implements CallableFromScenesManager {
    public TextArea json;

    public void comleteJSON(ActionEvent actionEvent) throws IOException {
        Gson g = new Gson();
        FileData fileData = g.fromJson(json.getText(), FileData.class);
        fileData.CompleteFileData();
        //CheckWords.fileData = fileData;
        ScenesManager.setScene(Main.primaryStage, Scenes.checkWords);
    }

    @Override
    public void init(boolean firstShowing) {
        json.setText("");
    }
}
