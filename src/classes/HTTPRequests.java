package classes;

import Starters.Main;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTPRequests {
    /**
        Отправить POST-запрос на сервер
     */
    public static String postRequest(String MyUrl, String Params, boolean NeedToShowAlert){
        byte[] data = null;
        InputStream is = null;
        try {
            URL url = new URL(MyUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Length", "" + Params.getBytes().length);
            OutputStream os = conn.getOutputStream();
            data = Params.getBytes(StandardCharsets.UTF_8);
            os.write(data);
            data = null;
            conn.connect();
            int responseCode= conn.getResponseCode();
            if(responseCode >= 400) {
                Errors error = Errors.SERVER_ERROR;
                showErrorAlert(error);
                return "ERROR:::" + error.ErrorCode;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = conn.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            Errors error = Errors.NO_INTERNET_CONNECTION;
            showErrorAlert(error);
            return "ERROR:::" + error.ErrorCode;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String answer = new String(data, StandardCharsets.UTF_8);
        Errors error = Errors.getError(answer);
        if(error!=Errors.OK){
            if(NeedToShowAlert){
                showErrorAlert(error);
            }
            System.out.println(error);
            return "ERROR:::" + error.ErrorCode;
        }
        return answer;
    }
    private static void showErrorAlert(Errors error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Произошла ошибка");
        alert.setContentText(error.ErrorDescription);
        alert.initOwner(Main.primaryStage);
        alert.getDialogPane().getStylesheets().add("sources/Dialog.css");
        alert.showAndWait();
    }
}
