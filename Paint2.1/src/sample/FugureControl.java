package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FugureControl {
    private static Stack<FigureAbstract> undoHistory = new Stack<FigureAbstract>();
    private Stack<FigureAbstract> redoHistory = new Stack<FigureAbstract>();

  //  private static List<FigureAbstract> figures = new ArrayList<FigureAbstract>();

    public static void redraw(GraphicsContext context, double w, double h)
    {
        context.clearRect(0, 0, w, h);
        for (FigureAbstract figure : undoHistory) {
            figure.draw(context);
        }
    }

    public static void add(FigureAbstract figure)
    {
        undoHistory.push(figure);
    }

    public static void resizeLast(double dX, double dY)
    {
        FigureAbstract figure = undoHistory.get(undoHistory.size() - 1);
        figure.resize(dX, dY);
    }

    public void removeLast()
    {
        if (!undoHistory.isEmpty()) {
           // undoHistory.pop();
            redoHistory.push(undoHistory.pop());
        }
    }

    public void redoLast(){
        if (!redoHistory.isEmpty()){
            undoHistory.push(redoHistory.pop());
        }
    }


}
