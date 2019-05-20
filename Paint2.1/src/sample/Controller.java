package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import sample.Enums.FigureState;
import sample.Enums.FigureTypes;
import sample.Factory.ShapeFactory;

import java.util.Stack;


public class Controller {

    private FugureControl figureControle = new FugureControl();
    private double firstX;
    private double firstY;
    private KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    private GraphicsContext graphicsContext;
    private FigureState state;
    private FigureAbstract figure;
    private FigureAbstract passed;

    @FXML
    private ToggleButton btnLine;

    @FXML
    private ToggleButton btnTriang;

    @FXML
    private ToggleButton btnCircle;

    @FXML
    private ToggleButton btnRect;

    @FXML
    private ToggleButton btnEllipse;

    @FXML
    private ToggleButton btnSquare;

    @FXML
    private Canvas myCanvas;

    @FXML
    private ColorPicker PenCol;

    @FXML
    private ColorPicker FillCol;

    @FXML
    private Slider SliderWidth;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnOpen;

    @FXML
    private ToggleButton btnNone;

    @FXML
     void initialize(){
        PenCol.setValue(Color.BLACK);
        FillCol.setValue(Color.WHITE);
        graphicsContext = myCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(3);
    }

    @FXML
     void CanvOnPressed(MouseEvent event){
        firstX =  event.getX();
        firstY =  event.getY();

        if (btnNone.isSelected()) {
            state = FigureState.SelectFigure;
            int count = figureControle.getUndoCount();
            Stack<FigureAbstract> redo = new Stack<FigureAbstract>();

            Stack<FigureAbstract> copied = figureControle.copyStack();
            while (count > 0){
                passed = copied.pop();
                redo.push(passed);

                if ((passed.getX1()<= firstX)&& (passed.getY1()<= firstY)&& (passed.getX2()>= firstX) && (passed.getY2()>= firstY)){
                    //ф-я для изменений
                    passed.setPenCol(PenCol.getValue().toString());
                    passed.setFillCol(FillCol.getValue().toString());
                    passed.setSliderWidth(SliderWidth.getValue());

                    count = 0;
                    System.out.println(passed);
                    redo.pop();
                    while (!redo.isEmpty()){
                        copied.push(redo.pop());
                    }
                    copied.push(passed);
                }
                count--;
            }
            FugureControl.add(passed);


           /* Stack<FigureAbstract> selectedFigures = figureControle.getUndoHistory();
            for (figure : selectedFigures){
                if ((figure.getX1()<= firstX)&&(figure.getY1()<= firstY)&&(figure.getX2()>= firstX)&&(figure.getY2()>= firstY)){
                    FigureAbstract passed = figure;

            }
            }*/
        }
        else {
            ShapeFactory shapeFactory = new ShapeFactory();
            
            state = FigureState.DrawFigure;
            FigureTypes figureType = FigureTypes.Line;

            if (btnLine.isSelected()){
                figureType = FigureTypes.Line;
            } else if (btnCircle.isSelected()){
                figureType = FigureTypes.Circle;
            } else if (btnEllipse.isSelected()){
                figureType = FigureTypes.Ellipse;
            } else if (btnRect.isSelected()){
                figureType = FigureTypes.Rectangle;
            } else if (btnSquare.isSelected()){
                figureType = FigureTypes.Square;
            } else if (btnTriang.isSelected()){
                figureType = FigureTypes.Triangle;
            }

            FigureAbstract figure = shapeFactory.create(figureType, firstX, firstY);
            figure.setFillCol(FillCol.getValue().toString());
            figure.setPenCol(PenCol.getValue().toString());
            figure.setSliderWidth(SliderWidth.getValue());

            FugureControl.add(figure);
        }

    }

    @FXML
     void CanvOnDragged(MouseEvent event){
        double newX = event.getX();
        double newY = event.getY();
        double dX = newX - firstX;
        double dY = newY - firstY;

        if (btnNone.isSelected()){
            if (state == FigureState.SelectFigure){
                FugureControl.resize(dX, dY);
            }
        }
        else {

            FugureControl.resize(dX, dY);
        }

        FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
        firstX = newX;
        firstY = newY;
        figureControle.clearRedo();
    }

    @FXML
    void CanvOnKeyPressed(KeyEvent event){
        if (undo.match(event)) {
           figureControle.undoLast();
           FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
        }
        if (redo.match(event)){
            figureControle.redoLast();
            FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
        }
    }

    @FXML
    void RedoSelected(){
        figureControle.redoLast();
        FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
    }

    @FXML
    void UndoSelected(){
        figureControle.undoLast();
        FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
    }

}
