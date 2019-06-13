package sample.Factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import sample.Config;
import sample.Controller;
import sample.Enums.FigureTypes;
import sample.FigureAbstract;
import sample.FugureControl;
import sample.GeomFigures.Line;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ShapeFactory {

    public List<Class<FigureAbstract>> figuresClasses = getActualFigures();

    public static List<Class<FigureAbstract>> getActualFigures() {

        Config config = Config.getInstance();
        List<Class<FigureAbstract>> figures = new ArrayList<>();
        File folder = new File(Paths.get(config.getLibsPath()).toString());
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles == null) return figures;

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                try {
                    JarFile jarFile = new JarFile(listOfFile.getAbsolutePath());
                    Enumeration<JarEntry> e = jarFile.entries();


                    URL[] urls = {new URL("jar:file:" + listOfFile.getAbsolutePath() + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);


                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');

                        Class cls = cl.loadClass(className);

                        if (FigureAbstract.class.isAssignableFrom(cls)) {
                            figures.add((Class<FigureAbstract>) cls);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return figures;
    }


    public FigureAbstract create(String name, FigureTypes figureType, double X, double Y) {

        for (Class<FigureAbstract> figureClass : figuresClasses) {
            if (figureClass.getName().endsWith(name)) {
                try {
                    Constructor constructor = figureClass.getConstructor(double.class, double.class, double.class, double.class);
                    FigureAbstract figure = (FigureAbstract) constructor.newInstance(X,Y,X,Y);
                    return figure;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;

        }

    }
