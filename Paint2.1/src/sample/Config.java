package sample;

public class Config {
    private static Config instance;

    private int minHeight;

    private int minWidth;

    private String penCol;

    private String fillCol;

    private String libsPath;

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

    public String getLibsPath() {
        return libsPath;
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
        libsPath = "D:/!Disk_D/BSUIR/OOTISP/Paint2.1/Paint2.1/out/production/Paint2.1/sample/libs";
    }
}
