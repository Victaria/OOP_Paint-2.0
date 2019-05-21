package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import sample.Enums.FigureState;
import sample.Enums.FigureTypes;
import sample.Factory.ShapeFactory;
import sample.GeomFigures.Line;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ToggleButton btnMove;

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
        FigureTypes figureType;
        firstX =  event.getX();
        firstY =  event.getY();

        if (btnNone.isSelected()|| btnMove.isSelected()) {
            state = FigureState.SelectFigure;
            int count = figureControle.getUndoCount();
            Stack<FigureAbstract> redo = new Stack<FigureAbstract>();
            Stack<FigureAbstract> copied = figureControle.copyStack();

            while (count > 0){
                passed = copied.pop();
                redo.push(passed);
                figureType = passed.getFigureType();
                if (((figureType == FigureTypes.Line) && (passed.LineSelected(firstX, firstY)))||(passed.FigureSelected(firstX, firstY))){
                    System.out.println(passed);
                    FugureControl.add(passed);
                    count = 0;
                    redo.pop();
                    while (!redo.isEmpty()){
                        copied.push(redo.pop());
                    }
                    copied.push(passed);
                    passed.setPenCol(PenCol.getValue().toString());
                    passed.setFillCol(FillCol.getValue().toString());
                    passed.setSliderWidth(SliderWidth.getValue());
                    copied.push(passed);
                }
                count--;
            }
        }
        else {
            ShapeFactory shapeFactory = new ShapeFactory();
            
            state = FigureState.DrawFigure;
            figureType = FigureTypes.Line;

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
            figure.setFigureType(figureType);
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

        if (btnMove.isSelected()){
            passed.moveFigure(dX, dY);
            FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
            FugureControl.add(passed);
        } else
        { FugureControl.resize(dX, dY);}
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

    @FXML
    void Save() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            String directorypath = selectedDirectory.getPath();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String format = currentDate+".xml";
            String path = directorypath+"/"+format;

            File newfile = new File(path);
            try {
                if (newfile.createNewFile()) {
                    FugureControl.getUndoHistory();
                }
               // try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                   // bw.write(wrapedShapes);
               // }
                //catch (IOException e) {
               // e.printStackTrace();
               // }
            } catch (IOException e) {
                alert(new RuntimeException("Invalid path"));
            } catch (Exception e) {
                alert(new Exception(e));
        }
        }
    }

    @FXML
    private void alert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error:");
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }

    @FXML
    void Upload() throws IOException{

    }

}
