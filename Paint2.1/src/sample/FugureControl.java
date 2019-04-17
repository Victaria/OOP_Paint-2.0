package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FugureControl {
    private Stack<FigureAbstract> undoHistory = new Stack<FigureAbstract>();
    private Stack<FigureAbstract> redoHistory = new Stack<FigureAbstract>();

    private static List<FigureAbstract> figures = new ArrayList<FigureAbstract>();

    public static void redraw(GraphicsContext context, double w, double h)
    {
        context.clearRect(0, 0, w, h);
        for (FigureAbstract figure : figures) {
            figure.draw(context);
        }
    }

    public static void add(FigureAbstract figure)
    {
        figures.add(figure);
    }

    public static void resizeLast(double deltaX, double deltaY)
    {
        FigureAbstract figure = figures.get(figures.size() - 1);
        figure.resize(deltaX, deltaY);
    }

    public void removeLast()
    {
        if (!figures.isEmpty()) {
            figures.remove(figures.size() - 1);
        }
    }


}
