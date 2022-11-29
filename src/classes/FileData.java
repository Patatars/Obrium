package classes;

import java.util.Map;

public class FileData {
    public static final int SHORT_ANSWER = 1;
    public static final int LONG_ANSWER = 2;


    public int version = 1;
    public int repeats = 4;
    public String name = "default";
    public String fileName="default";
    public String item="default";
    public String[] words;
    public String[] translates;
    public int[] AnswerType;
    public int[] points;

    public long startTime;
    public long endTime;
    public Map<String, Integer> mistakes;



    public void CompleteFileData(){
        points = new int[words.length];
    }

}
