package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.Singletons.RedoStack;
import sample.Singletons.UndoStack;

import java.util.Stack;

public class FugureControl {
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
        figure.resize(dX, dY);
    }

    //если фигура изменялась, то из главного в редо, а из ундо в мэин
    public void undoLast() {
        if (!mainStack.isEmpty()) {
            changeFor = mainStack.pop();
            if (changeFor.getIsChanged()) {
                if (!UndoStack.getInstance().isEmpty())
                    mainStack.push(UndoStack.getInstance().pop());
            }
            RedoStack.getInstance().push(changeFor);
        }

    }

    public void redoLast(){
        if (!RedoStack.getInstance().isEmpty()){
            if (changeFor.getIsChanged()) {
                if (!mainStack.isEmpty())
                    UndoStack.getInstance().push(mainStack.pop());
            }
            mainStack.push(RedoStack.getInstance().pop());
        }
    }

    public void clearRedo(){
        while (!RedoStack.getInstance().isEmpty()){
            RedoStack.getInstance().pop();
        }
    }

    public static Stack<FigureAbstract> getHistory() {
        return mainStack;
    }

    public int getUndoCount(){
        return mainStack.size();
    }


    public FigureAbstract popRedoHistory(){
        return RedoStack.getInstance().pop();
       // return redoHistory.pop();
    }

    public FigureAbstract popMainStack(){
        return mainStack.pop();
    }


    public Boolean RedoHistoryIsEmpty() {
        return RedoStack.getInstance().isEmpty();
      //  return redoHistory.isEmpty();
    }

    public static Boolean MainStackIsEmpty() {
        return mainStack.isEmpty();
    }

    public void pushUndoHistory(FigureAbstract figure){
        UndoStack.getInstance().push(figure);
    }

    public void pushRedoHistory(FigureAbstract figure){
        RedoStack.getInstance().push(figure);
    }

    public void pushMainStack(FigureAbstract figure){
        mainStack.push(figure);
    }

    public Stack<FigureAbstract> copyStack(){
        return (Stack<FigureAbstract>) mainStack.clone();
    }

}
