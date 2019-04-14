package sample;

import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import sample.GeomFigures.Line;


public class Controller {

    private Stage stage = Stage.Cursor;
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

  /*  @FXML
    ToggleButton[] toolsArr = {btnLine, btnTriang, btnCircle, btnRect, btnEllipse, btnSquare};

    @FXML
    private ToggleGroup buttons = new ToggleGroup(); */

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
        SliderWidth.setValue(3);
        SliderWidth.setMin(1);
        SliderWidth.setMax(30);
    }

    @FXML
     void CanvOnPressed(MouseEvent event){
        stage = Stage.Dragging;
        PrevX = (int) event.getX();
        PrevY = (int) event.getY();
        int DELTA_N = 20;

       if (btnLine.isSelected()){
           new Line(PrevX - DELTA_N, PrevY - DELTA_N, PrevX, PrevY);
          // FigureBuilder.create(FigureTypes.Line,PrevX, PrevY);
       }


    }

    @FXML
     void CanvOnDragged(MouseEvent event){
        if (stage == Stage.Dragging) {
            double newX = event.getX();
            double newY = event.getY();
            double dX = newX - PrevX;
            double dY = newY - PrevY;
        }
    }

    @FXML
    void CanvOnKeyPressed(KeyEvent event){
        if (undo.match(event)) {

        }
        if (redo.match(event)){

        }
    }

}
