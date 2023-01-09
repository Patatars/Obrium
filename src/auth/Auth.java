package auth;

import Starters.Main;
import classes.CallableFromScenesManager;
import classes.HTTPRequests;
import classes.URLs;
import classes.UserData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;

public class Auth implements CallableFromScenesManager {
    public Text wrong;
    public CheckBox saveAuthData;
    public Label label;

    @FXML
    private TextField login;

    @FXML
    private TextField password;



    @FXML
    void logIn() throws IOException {
        if(Auth.logIn(login.getText(), Auth.HashPassword(password.getText()))) {
            wrong.setVisible(false);
            if(saveAuthData.isSelected()){
                String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String decodedPath = URLDecoder.decode(path, "UTF-8");

                File data = new File(new File(decodedPath).getParent() + "/strings.data");
                if (!data.exists()) {
                    System.out.println(data.createNewFile() ? "created" : "not created");
                }
                FileReader r = new FileReader(data);
                Properties prop = new Properties();
                prop.load(r);
                prop.setProperty("username", login.getText());
                prop.setProperty("password", HashPassword(password.getText()));
                prop.store(Files.newOutputStream(data.toPath()), null);
            }
            ScenesManager.setScene(Main.primaryStage, Scenes.homePage);
        } else {
            wrong.setVisible(true);
        }
    }

    public static boolean logIn(String username, String password) {
        try {
            String answer = HTTPRequests.postRequest(URLs.LOGIN_URL.toString(), "username=" + username + "&password=" + password, false);
            if(answer.contains("ERROR:::")) return false;
            Gson g = new Gson();
            UserData user = g.fromJson(answer, UserData.class);
            user.username = username;
            user.password = password;
            UserData.user = user;
            Gson gson = new Gson();
            answer = HTTPRequests.postRequest(URLs.GET_ITEMCODE_BY_FULL_NAME.toString(),"stage=" + user.stage.substring(0, user.stage.indexOf('.')), true);
            UserData.Items = gson.fromJson(answer, new TypeToken<Map<String, String>>(){}.getType());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static String HashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void init(boolean b) {
        if(!b){
            wrong.setVisible(false);
            login.setText("");
            password.setText("");

        }
    }
}
