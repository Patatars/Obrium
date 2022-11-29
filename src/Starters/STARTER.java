package Starters;

public class STARTER {
    public static String VERSION = "pre-release 1.7";
    public static void main(String[] args) {
        System.out.println("version " + VERSION);
        for (String arg: args) {
           System.out.println(arg);
        }
        Main.main(args);
    }
}
