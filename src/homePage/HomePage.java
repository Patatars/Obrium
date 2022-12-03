package homePage;

import Starters.Main;
import checkWords.CheckWords;
import classes.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import jobs.Task.CheckBoxTask.CheckBoxTask;
import jobs.Task.DragAndDropTask.DragAndDropTask;
import jobs.Task.InsertWordTask.InsertWordTask;
import jobs.Task.RadioTask.RadioTask;
import jobs.Task.TextFieldTask.TextFieldTask;
import jobs.Task.baseTask;
import jobs.Work.baseJob;
import jobs.Work.learnWork.learnJob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HomePage implements CallableFromScenesManager {
    /**
     FXML переменные
     */
    public Label helloText;
    public Button button_start;
    public Button logOut;
    public Button reloadBut;
    public TabPane TabPane;
    public Tab AllJobsPassed = new Tab("Все работы пройдёны");
    public AnchorPane parent;


    /**
     Инфа о пользователе и его заданиях
     */
    UserData user;

    /**
        Приватные данные о контейнерах
     */
    private final Map<String,ScrollPane> scrollPaneMap = new HashMap<>();
    private final Map<String,VBox> VBoxMap = new HashMap<>();
    private final Map<String, Tab> TabMap = new HashMap<>();

    @FXML
    void initialize(){

    }
    @Override
    public void init(boolean b) {
        user = UserData.user;
        if (b){
            Label allPassed = new Label("Все работы пройдены");
            allPassed.setStyle("-fx-opacity: 1");
            allPassed.setTextFill(Color.GRAY);
            allPassed.setFont(new Font(25));
            AllJobsPassed.setContent(new BorderPane(allPassed));
            UserData.Items.forEach(this::initMaps);
            ClearAndAddPanels();
            helloText.setText("Привет, " + user.name + "!");
        }
        else reloadBut.fire();
    }


    String formatFileId(String item,String FileId){
        return item +"." + FileId.substring(0,FileId.indexOf('_')) + " класс.Модуль " + FileId.substring(FileId.indexOf('_')+1,FileId.lastIndexOf('_'));
    }

    void initMaps(String itemName, String itemCode){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId(itemCode + "_ScrollPane");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(276);
        scrollPane.setPrefWidth(255);
        scrollPane.setStyle("-fx-background: #080808; -fx-background-color: #080808; -fx-border-color: linear-gradient(from 38.5714% 51.4286%  to 68.0952% 51.4286% , #975bf2 0.0%,#2549ff 100.0%); -fx-border-width: 3 0 0 0;");
        scrollPane.getStylesheets().add("sources/styleForSB.css");

        Tab tab = new Tab();
        tab.setId(itemCode);
        tab.setClosable(false);
        tab.setOnSelectionChanged(this::selectionChanged);
        tab.setText(itemName);
        tab.setContent(scrollPane);
        tab.disableProperty().set(user.works.get(itemCode) == null);

        VBox vBox = new VBox();
        scrollPane.setContent(vBox);
        vBox.setPrefHeight(80);

        scrollPaneMap.put(itemCode, scrollPane);
        VBoxMap.put(itemCode, vBox);
        TabMap.put(itemCode, tab);
        TabPane.getTabs().add(tab);
    }


    void AddPanel(String id,String jobName, String key){
        assert VBoxMap.get(key) != null;
        assert scrollPaneMap.get(key) != null;
        AnchorPane ap = NewAnchorPane(id,jobName);
        VBoxMap.get(key).getChildren().add(ap);
        scrollPaneMap.get(key).setContent(VBoxMap.get(key));
        scrollPaneMap.get(key).vvalueProperty().bind(ap.heightProperty());
    }


    AnchorPane NewAnchorPane(String id,String jobName){
        AnchorPane hb = new AnchorPane();
        hb.setPrefHeight(35);
        hb.setMinHeight(hb.getPrefHeight());
        hb.setStyle("-fx-background-color: #000000;");
        hb.setOnMouseClicked(this::CheckItem);

        Text letter = new Text();
        letter.setFill(Color.WHITE);
        letter.setId(id);
        letter.setLayoutX(14);
        letter.setLayoutY(24);
        letter.setText(jobName);
        letter.setFont(new Font("Times New Roman", 18));
        hb.getChildren().add(letter);
        return hb;
    }

    AnchorPane lastClicked;


    private void CheckItem(MouseEvent mouseEvent) {
        AnchorPane ap = (AnchorPane) mouseEvent.getSource();
        if(ap == lastClicked) {
            button_start.disableProperty().setValue(true);
            ap.setStyle(ap.getStyle().replace("-fx-border-color: white; -fx-border-width: 2;", ""));
            lastClicked = null;
            return;
        }
        ap.setStyle(ap.getStyle() + "-fx-border-color: white; -fx-border-width: 2;");
        if(lastClicked == null) {
            lastClicked = ap;
            button_start.disableProperty().setValue(false);
            return;
        }
        lastClicked.setStyle(ap.getStyle().replace("-fx-border-color: white; -fx-border-width: 2;", ""));
        lastClicked = ap;
    }
    String lastId;
    public void selectionChanged(Event event) {
        String id = ((Tab)event.getSource()).getId();
        if(id.equals(lastId)) {
            lastId=null;
        }
        else{
            lastId=id;
        }
        if(lastClicked == null) return;
        lastClicked.setStyle(lastClicked.getStyle().replace("-fx-border-color: white; -fx-border-width: 2;", ""));
        lastClicked = null;
        button_start.disableProperty().setValue(true);
    }

    @FXML
    public void startTest() throws IOException {
        Text t = (Text) lastClicked.getChildren().get(0);
        String answer = HTTPRequests.postRequest(URLs.GETFILE_URL.toString(), "username=" + user.username + "&password=" + user.password + "&filename=" + t.getId() + "&item=" + TabPane.getSelectionModel().getSelectedItem().getId(), true);
        if(answer.contains("ERROR:::")) {
            return;
        }
        answer = "{\"version\":1, \"type\": \"learnJob\", \"name\" :  \"228\", \"filename\" :  \"f\", \"item\" :  \"fe\",\"tasks\" :  [\n" +
                "  {\n" +
                "    \"type\" : \"DragAndDropTask\",\n" +
                "    \"task\" : \"123\",\n" +
                "    \"repeats\" : 2\n" +
                "  }\n" +
                "  ]\n" +
                "}";
        RuntimeTypeAdapterFactory<baseTask> typeFactoryTask = RuntimeTypeAdapterFactory.of(baseTask.class, "type").registerSubtype(TextFieldTask.class).registerSubtype(RadioTask.class).registerSubtype(CheckBoxTask.class).registerSubtype(InsertWordTask.class).registerSubtype(DragAndDropTask.class);
        RuntimeTypeAdapterFactory<baseJob> typeFactoryJob = RuntimeTypeAdapterFactory.of(baseJob.class, "type").registerSubtype(learnJob.class);
        Gson g = new GsonBuilder().registerTypeAdapterFactory(typeFactoryTask).registerTypeAdapterFactory(typeFactoryJob).create();
        CheckWords.job = g.fromJson(answer, learnJob.class);
        ScenesManager.setScene(Main.primaryStage, Scenes.checkWords);

    }


    public void logOut() throws IOException {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        File data = new File(new File(decodedPath).getParent() + "/strings.data");
        if(!data.exists()) {
            ScenesManager.setScene(Main.primaryStage, Scenes.auth);
            return;
        }
        Properties p = new Properties();
        p.load(new FileReader(data));
        p.setProperty("username", "");
        p.setProperty("password", "");
        p.store(new FileOutputStream(data), null);
        ScenesManager.setScene(Main.primaryStage,Scenes.auth);
    }


    public void reloadJobs() {
        String username = user.username;
        String password = user.password;
        String ans = HTTPRequests.postRequest(URLs.LOGIN_URL.toString(), "username=" + username + "&password=" + password, true);
        if(ans.contains("ERROR:::")){
            logOut.fire();
            return;
        }
        Gson gson = new Gson();
        user = gson.fromJson(ans, UserData.class);
        user.username = username;
        user.password = password;
        UserData.user= user;
        helloText.setText("Привет, " + user.name + "!");
        ClearAndAddPanels();
    }
    void ClearAndAddPanels(){
        VBoxMap.forEach((s, vBox) -> vBox.getChildren().clear());
        UserData.Items.forEach((Full, Code) -> {
            try {
                TabMap.get(Code).setDisable(user.works.get(Code) == null);
            } catch (Exception e){
                System.out.println("error");
            }
            if(user.works.get(Code) != null)for(String s:user.works.get(Code)) AddPanel(s, formatFileId(Full, s), Code);
        });
        TabPane.getTabs().sort((o1, o2) -> {
            if(o1.isDisabled() && !o2.isDisabled()) return 1;
            if(o2.isDisabled() && !o1.isDisabled()) return -1;
            return 0;
        });
        if (TabPane.getTabs().get(0).isDisabled()) {
            TabPane.getTabs().add(0, AllJobsPassed);
            }
        else if(TabPane.getTabs().get(0) == AllJobsPassed && !TabPane.getTabs().get(1).isDisabled()) TabPane.getTabs().remove(AllJobsPassed);

        if(TabPane.getSelectionModel().getSelectedItem() != null) {
            if (TabPane.getSelectionModel().getSelectedItem().isDisabled()) TabPane.getSelectionModel().selectFirst();
        }
    }


}
