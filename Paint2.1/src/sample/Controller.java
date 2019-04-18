package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import sample.GeomFigures.*;


public class Controller {

    private Stage stage = Stage.Cursor;
    private FugureControl figureControle = new FugureControl();
    private double PrevX;
    private double PrevY;
    private KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

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
    private GraphicsContext graphicsContext;

    @FXML
     void initialize(){
        PenCol.setValue(Color.BLACK);
        FillCol.setValue(Color.WHITE);
        graphicsContext = myCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(3);
        btnLine.isSelected();
    }

    @FXML
     void CanvOnPressed(MouseEvent event){
        FigureTypes figureType = null;
        stage = Stage.Dragging;
        PrevX = (int) event.getX();
        PrevY = (int) event.getY();

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
           FigureAbstract figure = FigureBuilder.create(figureType, PrevX, PrevY);
           figure.setFillCol(FillCol.getValue().toString());
           figure.setPenCol(PenCol.getValue().toString());
           figure.setSliderWidth(SliderWidth.getValue());

           FugureControl.add(figure);
           FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
    }

    @FXML
     void CanvOnDragged(MouseEvent event){
        if (stage == Stage.Dragging) {
            double newX = event.getX();
            double newY = event.getY();
            double dX = newX - PrevX;
            double dY = newY - PrevY;

            if (btnLine.isSelected()){
                Line line = new Line(PrevX,PrevY,newX,newY);
                line.draw(graphicsContext);
            } else if (btnCircle.isSelected()){
                Circle circle = new Circle(PrevX, PrevY, newX, newY);
                circle.draw(graphicsContext);
            } else if (btnEllipse.isSelected()){
                Ellipse ellipse = new Ellipse(PrevX, PrevY, newX, newY);
                ellipse.draw(graphicsContext);
            } else if (btnRect.isSelected()){
                Rectangle rectangle = new Rectangle(PrevX, PrevY, newX, newY);
            } else if (btnSquare.isSelected()){
                Square square = new Square(PrevX, PrevY, newX, newY);
                square.draw(graphicsContext);
            } else if (btnTriang.isSelected()){
                Triangle triangle = new Triangle(PrevX, PrevY, newX, newY);
                triangle.draw(graphicsContext);
            }
            FugureControl.resizeLast(dX, dY);
            FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
            PrevX = newX;
            PrevY = newY;
        }
    }

    @FXML
    void CanvOnKeyPressed(KeyEvent event){
        if (undo.match(event)) {
           figureControle.removeLast();
           FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
        }
        if (redo.match(event)){

        }
    }

}
