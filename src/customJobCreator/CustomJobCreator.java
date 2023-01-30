package customJobCreator;

import Starters.Main;
import checkWords.CheckWords;
import classes.CallableFromScenesManager;
import classes.FileData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import jobs.Task.CheckBoxTask.CheckBoxTask;
import jobs.Task.DragAndDropTask.DragAndDropTask;
import jobs.Task.InsertWordTask.InsertWordTask;
import jobs.Task.RadioTask.RadioTask;
import jobs.Task.TextFieldTask.TextFieldTask;
import jobs.Task.baseTask;
import jobs.Work.baseJob;

import java.io.IOException;

public class CustomJobCreator implements CallableFromScenesManager {
    public TextArea json;

    public void comleteJSON(ActionEvent actionEvent) throws IOException {
        RuntimeTypeAdapterFactory<baseTask> typeFactoryTask = RuntimeTypeAdapterFactory.of(baseTask.class, "type").registerSubtype(TextFieldTask.class).registerSubtype(RadioTask.class).registerSubtype(CheckBoxTask.class).registerSubtype(InsertWordTask.class).registerSubtype(DragAndDropTask.class);
        Gson g = new GsonBuilder().registerTypeAdapterFactory(typeFactoryTask).create();
        CheckWords.job = g.fromJson(json.getText(), baseJob.class);
        ScenesManager.setScene(Main.primaryStage, Scenes.checkWords);
    }

    @Override
    public void init(boolean firstShowing) {
        json.setText("");
    }
}
