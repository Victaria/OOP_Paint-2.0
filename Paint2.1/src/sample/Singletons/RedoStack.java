package sample.Singletons;

import sample.FigureAbstract;

import java.util.Stack;

public class RedoStack {
    private static Stack<FigureAbstract> redoHistory = new Stack<FigureAbstract>();

    private RedoStack(){}

    public static Stack<FigureAbstract> getInstance(){
        return redoHistory;
    }

}

