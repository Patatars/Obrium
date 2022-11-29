package initScenes;

import Starters.Main;
import Starters.STARTER;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;


public class ScenesManager {
    private static final String title = "Образовательный минимум v " + STARTER.VERSION;
    private static Scenes showingScene;
    public static Scene WindowScene;


    /**
     Создать сцену с дефолтным названием
     */
    public static void createScene(Stage primaryStage, Scenes sceneEnum) throws IOException {
        showingScene = sceneEnum;
        loadScene(primaryStage, sceneEnum);
    }
    /**
     Сменить сцену
     */
    public static void setScene(Stage primaryStage, Scenes sceneEnum) throws IOException {
        if (showingScene == null) return;
        primaryStage.getScene().setRoot(sceneEnum.root);
        sceneEnum.controller.init(sceneEnum.firstShowing);
        sceneEnum.firstShowing = false;
        primaryStage.setMaximized(sceneEnum.isMaximized);
        if(!sceneEnum.isMaximized) {
            primaryStage.setHeight(sceneEnum.height);
            primaryStage.setWidth(sceneEnum.width);
        }
        primaryStage.setResizable(sceneEnum.isResizable);
        primaryStage.setMinWidth(sceneEnum.minWidth);
        primaryStage.setMinHeight(sceneEnum.minHeight);
        showingScene = sceneEnum;
    }
    public static Stage createAlert(String title, Scenes sceneEnum) throws IOException {
        Stage alertStage = new Stage();
        Scene scene = sceneEnum.root.getScene() == null ? new Scene(sceneEnum.root) : sceneEnum.root.getScene();
        sceneEnum.controller.init(sceneEnum.firstShowing);
        sceneEnum.firstShowing = false;
        alertStage.setHeight(sceneEnum.height);
        alertStage.setWidth(sceneEnum.width);
        alertStage.setTitle(title);
        alertStage.setResizable(sceneEnum.isResizable);
        alertStage.setMinWidth(sceneEnum.minWidth);
        alertStage.setMinHeight(sceneEnum.minHeight);
        alertStage.setMaximized(sceneEnum.isMaximized);
        alertStage.setScene(scene);
        alertStage.getIcons().add(new Image("sources/ObrazMinLogo.png"));
        return alertStage;
    }

    /**
     Звгрузить сцену !private!
     */
    private static void loadScene(Stage primaryStage, Scenes sceneEnum) {
        primaryStage.setTitle(ScenesManager.title);
        Scene scene =  new Scene(sceneEnum.root);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN), () -> {
            try {
                setScene(Main.primaryStage, Scenes.customJobCreator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sceneEnum.controller.init(sceneEnum.firstShowing);
        sceneEnum.firstShowing = false;
        WindowScene = scene;
        primaryStage.setHeight(sceneEnum.height);
        primaryStage.setWidth(sceneEnum.width);
        primaryStage.setResizable(sceneEnum.isResizable);
        primaryStage.setMinWidth(sceneEnum.minWidth);
        primaryStage.setMinHeight(sceneEnum.minHeight);
        primaryStage.setMaximized(sceneEnum.isMaximized);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("sources/ObrazMinLogo.png"));
        primaryStage.show();
    }

}
