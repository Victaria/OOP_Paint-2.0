package sample.Factory;

import sample.Config;
import sample.Enums.FigureTypes;
import sample.FigureAbstract;

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
    private static List<Class<FigureAbstract>> figuresClasses = getActualFigures();

    private static List<Class<FigureAbstract>> getActualFigures() {
        Config config = Config.getInstance();
        List<Class<FigureAbstract>> figures = new ArrayList<>();
        File folder = new File(Paths.get(config.getLibsPath()).toString());
        File[] listOfFiles = folder.listFiles();
        System.out.println(folder);
        System.out.println(listOfFiles);

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
                        System.out.println(className);

                        Class cls = cl.loadClass(className);
                        System.out.println(cls);

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
//D:\!Disk_D\BSUIR\OOTISP\Paint2.1\Paint2.1\out\production\Paint2.1\sample\GeomFigures


    public FigureAbstract create(String name, FigureTypes figureType, double X, double Y) {

        for (Class<FigureAbstract> figureClass : figuresClasses) {
            System.out.println(figureClass.getName() + " " + name);
            if (figureClass.getName().endsWith(name)) {
                try {
                    Constructor constructor = figureClass.getConstructor(double.class, double.class, double.class, double.class);
                    FigureAbstract figure = (FigureAbstract) constructor.newInstance(X,Y,X,Y);
                    System.out.println(figure);
                    return figure;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;

        }
       /* switch (figureType){
            case Line:
                return new Line(X, Y, X, Y);
            case Rectangle:
                return new Rectangle(X, Y, X, Y);
            case Circle:
                return new Circle(X, Y, X, Y);
            case Square:
                return new Square(X, Y, X, Y);
            case Ellipse:
                return new Ellipse(X, Y, X, Y);
            case Triangle:
                return new Triangle(X, Y, X, Y);
            default:
                throw new IllegalArgumentException("Unknown type: " + figureType);
        }
    }*/
    }
