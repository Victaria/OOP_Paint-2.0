package sample.Singletons;

import sample.FigureAbstract;

import java.util.Stack;

public class UndoStack {
    private static Stack<FigureAbstract> undoHistory = new Stack<FigureAbstract>();

    private UndoStack(){}

    public static Stack<FigureAbstract> getInstance(){
        return undoHistory;
    }
}
