package sample;

public class Config {
    private static Config instance;

    private int minHeight;

    private int minWidth;

    private String penCol;

    private String fillCol;


    public String  getPenCol() {
        return penCol;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public String getFillCol() {
        return fillCol;
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    private Config() {
        penCol = "#000000";
        minHeight = 900;
        minWidth = 1200;
        fillCol = "#ffffff";
    }
}
