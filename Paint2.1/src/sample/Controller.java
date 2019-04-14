package sample;

import javafx.scene.control.ListView;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.paint.Color;


public class Controller {
    private Stage stage = Stage.Cursor;
    private double PrevX;
    private double PrevY;
    private KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

    @FXML
    private ListView<FigureTypes> FigureList;

    @FXML
    private Canvas myCanvas;

    @FXML
    private ColorPicker PenCol;

    @FXML
    private ColorPicker FillCol;

    @FXML
    private Slider SliderWidth;

    @FXML
    public void initialize(){
        PenCol.setValue(Color.BLACK);
        FillCol.setValue(Color.WHITE);
        Slider slider = new Slider(1, 30, 3);
        FigureList.getItems().addAll(FigureTypes.values());

    }

    @FXML
    public void CanvOnPressed(MouseEvent event){
        stage = Stage.Dragging;


    }

    @FXML
    public void CanvOnDragged(MouseEvent event){
        if (stage == Stage.Dragging) {
            double newX = event.getX();
            double newY = event.getY();
            double dX = newX - PrevX;
            double dY = newY - PrevY;
        }
    }

    @FXML
    public void CanvOnKeyPressed(KeyEvent event){
        if (undo.match(event)) {

        }
        if (redo.match(event)){

        }
    }

}
