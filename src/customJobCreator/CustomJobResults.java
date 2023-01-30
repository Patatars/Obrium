package customJobCreator;

import Starters.Main;
import classes.CallableFromScenesManager;
import initScenes.Scenes;
import initScenes.ScenesManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import jobs.Work.baseJob;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJobResults implements CallableFromScenesManager {
    public Label File_Name;
    public Label StartTime;
    public Label EndTime;
    public Label DoingTime;
    public javafx.scene.layout.VBox VBox;
    public static baseJob info;

    @Override
    public void init(boolean firstShowing) {
        VBox.getChildren().clear();
        File_Name.setText("Ваша работа");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM y года в HH:mm:ss");
        long startTime = info.startTime*1000;
        long endTime = info.endTime*1000;
        Date date = new Date(startTime);
        StartTime.setText("Начало прохождения: " + simpleDateFormat.format(date));
        date.setTime(endTime);
        EndTime.setText("Конец прохождения: " + simpleDateFormat.format(date));
        long doingTime = endTime - startTime;
        DoingTime.setText("Время прохождения: " + new Time(doingTime - 10800000));
        //Stream<Map.Entry<String,Integer>> sorted = info.mistakes.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
//        sorted.forEach(stringIntegerEntry -> {
//            String s = stringIntegerEntry.getKey();
//            Tooltip tooltip = null;
//            if(stringIntegerEntry.getKey().length() > 23) {
//                s = s.replaceAll("(.{23})", "$1\n");
//                tooltip = new Tooltip(s);
//                tooltip.setFont(new Font(20));
//
//                s = s.substring(0, 20) + "...";
//
//            }
//            Label label = new Label(s + ": " + stringIntegerEntry.getValue());
//            label.setTooltip(tooltip);
//            label.setTextFill(Color.WHITE);
//            label.setFont(new Font(30));
//            label.setMaxWidth(Double.MAX_VALUE);
//            label.setAlignment(Pos.CENTER);
//            VBox.getChildren().add(label);
//        });
    }

    public void OneMoreTime(ActionEvent actionEvent) throws IOException {
        //Arrays.fill(info.points, 0);
        ScenesManager.setScene(Main.primaryStage, Scenes.checkWords);

    }

    public void Complete(ActionEvent actionEvent) throws IOException {
        ScenesManager.setScene(Main.primaryStage, Scenes.homePage);

    }
}
