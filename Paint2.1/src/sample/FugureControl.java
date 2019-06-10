package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.Stack;

public class FugureControl {
    private static Stack<FigureAbstract> undoHistory = new Stack<FigureAbstract>();
    private Stack<FigureAbstract> redoHistory = new Stack<FigureAbstract>();
    private static Stack<FigureAbstract> mainStack = new Stack<FigureAbstract>();
    private FigureAbstract changeFor;


    public static void redraw(GraphicsContext context, double w, double h)
    {
        context.clearRect(0, 0, w, h);
        for (FigureAbstract figure : mainStack) {
            figure.draw(context);
        }
    }


    public static void resize(double dX, double dY)
    {
        FigureAbstract figure = mainStack.get(mainStack.size() - 1);
        //FigureAbstract figure = undoHistory.get(undoHistory.size() - 1);
        figure.resize(dX, dY);
    }

    //если фигура изменялась, то из главного в редо, а из ундо в мэин
    public void undoLast() {
        if (!mainStack.isEmpty()) {
            changeFor = mainStack.pop();
            if (changeFor.getIsChanged()) {
                 //redoHistory.push(changeFor);
                 if (!undoHistory.isEmpty())
                 mainStack.push(undoHistory.pop());
                // if (!undoHistory.isEmpty()) {
                //    return redoHistory.push(undoHistory.pop());
            }
                redoHistory.push(changeFor);
        }

    }

    public void redoLast(){
        if (!redoHistory.isEmpty()){
            if (changeFor.getIsChanged()) {
                if (!mainStack.isEmpty())
                 undoHistory.push(mainStack.pop());
            }
            mainStack.push(redoHistory.pop());
        }
    }

    public void clearRedo(){
        while (!redoHistory.isEmpty()){
            redoHistory.pop();
        }
    }

    public static Stack<FigureAbstract> getHistory() {
        return mainStack;
    }

    public int getUndoCount(){
        return mainStack.size();
    }

    public FigureAbstract popUndoHistory(){
        return undoHistory.pop();
    }

    public FigureAbstract popRedoHistory(){
        return redoHistory.pop();
    }

    public FigureAbstract popMainStack(){
        return mainStack.pop();
    }

    public static Boolean UndoHistoryIsEmpty() {
        return undoHistory.isEmpty();
    }

    public Boolean RedoHistoryIsEmpty() {
        return redoHistory.isEmpty();
    }

    public static Boolean MainStackIsEmpty() {
        return mainStack.isEmpty();
    }

    public void pushUndoHistory(FigureAbstract figure){
        undoHistory.push(figure);
    }

    public void pushRedoHistory(FigureAbstract figure){
        redoHistory.push(figure);
    }

    public void pushMainStack(FigureAbstract figure){
        mainStack.push(figure);
    }

    public Stack<FigureAbstract> copyStack(){
        return (Stack<FigureAbstract>) mainStack.clone();
    }

    /*

    public void pushRedo(FigureAbstract figure){
        redoHistory.push(figure);
    }
    public void popRedo(){
        redoHistory.pop();
    }

    public void changeUndoStack(Stack<FigureAbstract>copied){
        while (!undoHistory.isEmpty()){
            undoHistory.pop();
        }
        while (!copied.isEmpty()){
            undoHistory.push(copied.pop());
        }
    }*/
}
