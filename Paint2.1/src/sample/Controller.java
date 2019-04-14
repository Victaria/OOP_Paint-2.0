package sample;

import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.paint.Color;


public class Controller {
    private Stage state = Stage.Cursor;
    private double PrevX;
    private double PrevY;
    private KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);

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
        Slider slider = new Slider(1, 30, 3);
    }

    @FXML
    public void CanvOnPressed(MouseEvent event){
        state = Stage.Dragging;

    }

    @FXML
    public void CanvOnDragged(MouseEvent event){
        if (state == Stage.Dragging) {
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
    }

}
