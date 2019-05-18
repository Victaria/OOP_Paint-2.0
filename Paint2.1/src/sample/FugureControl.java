package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FugureControl {
    private static Stack<FigureAbstract> undoHistory = new Stack<FigureAbstract>();
    private Stack<FigureAbstract> redoHistory = new Stack<FigureAbstract>();


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

    public static void resize(double dX, double dY)
    {
        FigureAbstract figure = undoHistory.get(undoHistory.size() - 1);
        figure.resize(dX, dY);
    }

    public void undoLast()
    {
        if (!undoHistory.isEmpty()) {
            redoHistory.push(undoHistory.pop());
        }
    }

    public void redoLast(){
        if (!redoHistory.isEmpty()){
            undoHistory.push(redoHistory.pop());
        }
    }

    public void clearRedo(){
        while (!redoHistory.isEmpty()){
            redoHistory.pop();
        }
    }

    public static Stack<FigureAbstract> getUndoHistory() {
        return undoHistory;
    }

    public int getUndoCount(){
        return undoHistory.size();
    }

    public Stack<FigureAbstract> copyStack(){
        Stack<FigureAbstract>copied = (Stack<FigureAbstract>) undoHistory.clone();
        return copied;
    }
}
