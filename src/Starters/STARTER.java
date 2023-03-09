package Starters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import jobs.Task.TextFieldTask.TextFieldTask;
import jobs.Task.baseTask;

import java.lang.reflect.Type;
import java.util.List;

public class STARTER {
    public static String VERSION = "pre-release 2.0";
    public static void main(String[] args) {
        System.out.println("version " + VERSION);
        for (String arg: args) {
           System.out.println(arg);
        }
        Main.main(args);

    }
}
