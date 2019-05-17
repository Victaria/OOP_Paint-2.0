package sample.Singletons;

import javafx.scene.canvas.GraphicsContext;
import sample.FigureAbstract;

import java.util.Stack;

public class UndoStack {
    private static UndoStack undoStack = null;
    private static Stack <FigureAbstract> undoHistory;

    private UndoStack(){
        undoHistory = new Stack<FigureAbstract>();
    }

    public void add(FigureAbstract figure){
        getUndoHistory().push(figure);
    }

    public static Stack<FigureAbstract> getUndoHistory() {
        return undoHistory;
    }

    public static UndoStack getInstance(){
        if (undoStack == null){
            undoStack = new UndoStack();
        }
        return undoStack;
    }
}
