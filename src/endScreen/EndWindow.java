package endScreen;

import Starters.Main;
import classes.CallableFromScenesManager;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class EndWindow  implements CallableFromScenesManager {
    public static boolean SuccesfulUploaded = false;
    public static Stage alertStage;
    public Label success;



    public void ToHomePage() throws IOException {
        alertStage.close();
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
    }

    @Override
    public void init(boolean b) {
        System.out.println(SuccesfulUploaded);
        if (SuccesfulUploaded){
            success.setText("Данные о прохождении этого задания\nбыли успешно сохранены");
            success.setTextFill(Color.rgb(118, 255, 118));
        } else {
            success.setText("Данные о прохождении этого задания\nне были сохранены");
            success.setTextFill(Color.rgb(255, 98, 98));
        }
    }
}

