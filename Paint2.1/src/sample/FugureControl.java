package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Stack;

public class FugureControl {
    private static Stack<ShapeSnapshot> undoHistory = new Stack<ShapeSnapshot>();
    private static Stack<FigureAbstract> mainStack = new Stack<FigureAbstract>();
    private static Stack<ShapeSnapshot> redoHistory = new Stack<ShapeSnapshot>();
    private FigureAbstract changeFor;


    public static void redraw(GraphicsContext context)
    {
        context.clearRect(0, 0, Config.getInstance().getMinWidth(), Config.getInstance().getMinHeight());
        for (FigureAbstract figure : mainStack) {
            figure.draw(context);
        }
    }


    public static void resize(double dX, double dY)
    {
        if (!mainStack.isEmpty()){
        FigureAbstract figure = mainStack.get(mainStack.size()-1);
        figure.resize(dX, dY);}
    }

    //если фигура изменялась, то из главного в редо, а из ундо в мэин
    public void undoLast() {
        if (!mainStack.isEmpty()) {
            changeFor = mainStack.pop();
            if (changeFor.getIsChanged()) {
                if (!undoHistory.isEmpty()){
                    ShapeSnapshot snap = undoHistory.pop();
                    FigureAbstract shape = snap.getFigshape();
                    shape.fromSnapshot(snap);
                    if (!mainStack.contains(shape)) mainStack.push(shape);}
                }
                   // mainStack.push(undoHistory.pop());
            redoHistory.push(changeFor.makeSnapshot());
            }

        }

          /*  if (!mainStack.isEmpty()) {
                changeFor = mainStack.pop();
                if (changeFor.getIsChanged()) {
                    undoHistory.push(changeFor.makeSnapshot());
                } else mainStack.push(changeFor);
                if (!redoHistory.isEmpty()){
                ShapeSnapshot snap = redoHistory.pop();
                FigureAbstract shape = snap.getFigshape();
                shape.fromSnapshot(snap);
                if (!mainStack.contains(shape)) mainStack.push(shape);}
            } else if (!redoHistory.isEmpty()){
                ShapeSnapshot snap = redoHistory.pop();
                FigureAbstract shape = snap.getFigshape();
                shape.fromSnapshot(snap);
                if (!mainStack.contains(shape)) mainStack.push(shape);
            }*/




    public void redoLast() {
        if (!redoHistory.isEmpty()){
            if (changeFor.getIsChanged()) {
                if (!mainStack.isEmpty())
                    undoHistory.push(mainStack.pop().makeSnapshot());
            }
            ShapeSnapshot snap = redoHistory.pop();
            FigureAbstract shape = snap.getFigshape();
            shape.fromSnapshot(snap);
            if (!mainStack.contains(shape)) mainStack.push(shape);
        }
      /*  FigureAbstract changeFor;
            if (!undoHistory.isEmpty()) {
                if (!mainStack.isEmpty()) {
                    changeFor = mainStack.pop();

                    if (changeFor.getIsChanged()) {
                        redoHistory.push(changeFor.makeSnapshot());
                    } else mainStack.push(changeFor);
                        ShapeSnapshot snap = undoHistory.pop();
                        FigureAbstract shape = snap.getFigshape();
                        shape.fromSnapshot(snap);
                        if (!mainStack.contains(shape)) mainStack.push(shape);
                } else {
                    ShapeSnapshot snap = undoHistory.pop();
                    FigureAbstract shape = snap.getFigshape();
                    shape.fromSnapshot(snap);
                    if (!mainStack.contains(shape)) mainStack.push(shape);
                }
    }*/
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


    public ShapeSnapshot popRedoHistory(){
        return redoHistory.pop();
       // return redoHistory.pop();
    }

    public FigureAbstract popMainStack(){
        return mainStack.pop();
    }


    public Boolean RedoHistoryIsEmpty() {
        return redoHistory.isEmpty();
      //  return redoHistory.isEmpty();
    }



    public void pushUndoHistory(ShapeSnapshot figure){
        undoHistory.push(figure);
    }

    public void pushRedoHistory(FigureAbstract figure){
       redoHistory.push(figure.makeSnapshot());
    }

    public void pushMainStack(ShapeSnapshot snap){
        FigureAbstract shape = snap.getFigshape();
        shape.fromSnapshot(snap);
        if(!mainStack.contains(shape)) mainStack.push(shape);
    }

    public Stack<FigureAbstract> copyStack(){
        return (Stack<FigureAbstract>) mainStack.clone();
    }

}
