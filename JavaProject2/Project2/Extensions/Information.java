package Project2.Extensions;

public class Information {
    private static String text = "";

    public static String getText() {
        return text;
    }

    public static void AddInformation(String information) {
        text += information + "\n";
    }

    public static void clearInformation() {
        text = "";
    }
}
