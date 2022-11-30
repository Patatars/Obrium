package Starters;

import auth.Auth;
import com.cyberes.Property.UpdateManager;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest((WindowEvent)->{
            try {
                primaryStage.close();
                UpdateManager.DownloadNewJar("http://cyberes.admin-blog.ru/other/getVersion.php", STARTER.VERSION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if(!tryLogin(primaryStage)) {
            Main.primaryStage = primaryStage;
            ScenesManager.createScene(primaryStage, Scenes.auth);
        }
    }

    boolean tryLogin(Stage primaryStage) throws IOException {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        File data = new File(new File(decodedPath).getParent() + "/strings.data");
        if(!data.exists()){
            System.out.println(data.createNewFile() ? "created" : "not created");
            return false;
        }
        Properties prop = new Properties();
        prop.load(new FileReader(data));
        if(Auth.logIn(prop.getProperty("username"), prop.getProperty("password"))){
            Main.primaryStage = primaryStage;
            ScenesManager.createScene(primaryStage, Scenes.homePage);
            return true;
        } else {
            return  false;
        }
    }


    public static void main(String[] args) {
    launch(args);

    }

}
