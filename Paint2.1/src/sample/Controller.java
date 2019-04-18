package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;


public class Controller {

    private FugureControl figureControle = new FugureControl();
    private double firstX;
    private double firstY;
    private KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    private GraphicsContext graphicsContext;

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
     void initialize(){
        PenCol.setValue(Color.BLACK);
        FillCol.setValue(Color.WHITE);
        graphicsContext = myCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(3);
    }

    @FXML
     void CanvOnPressed(MouseEvent event){
        FigureTypes figureType = null;
        firstX =  event.getX();
        firstY =  event.getY();

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
           FigureAbstract figure = FigureBuilder.create(figureType, firstX, firstY);
           figure.setFillCol(FillCol.getValue().toString());
           figure.setPenCol(PenCol.getValue().toString());
           figure.setSliderWidth(SliderWidth.getValue());

           FugureControl.add(figure);
           FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
    }

    @FXML
     void CanvOnDragged(MouseEvent event){
            double newX = event.getX();
            double newY = event.getY();
            double dX = newX - firstX;
            double dY = newY - firstY;

            FugureControl.resizeLast(dX, dY);
            FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
            firstX = newX;
            firstY = newY;
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
