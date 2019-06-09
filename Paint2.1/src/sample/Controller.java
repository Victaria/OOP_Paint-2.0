package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.Enums.FigureState;
import sample.Enums.FigureTypes;
import sample.Factory.ShapeFactory;
import sample.GeomFigures.Line;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Attribute;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private String role1 = null;
    private String role2 = null;
    private String role3 = null;
    private String role4 = null;
    private ArrayList<String> rolev;

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
        FigureAbstract cop;

        if (btnNone.isSelected()|| btnMove.isSelected()) {
            state = FigureState.SelectFigure;
            int count = figureControle.getUndoCount();
          //  Stack<FigureAbstract> redo = figureControle.getRedoHistory();
          //  Stack<FigureAbstract> undo = FugureControl.getUndoHistory();
          //  Stack<FigureAbstract> main = FugureControl.getMainStack();

            while (count > 0){
                passed = figureControle.popMainStack(); //take an element
               // passed = figureControle.undoLast();
                figureControle.pushRedoHistory(passed); //add to Redo Stack
                figureType = passed.getFigureType();
                if (((figureType == FigureTypes.Line) && (passed.LineSelected(firstX, firstY)))||(passed.FigureSelected(firstX, firstY))){
                    System.out.println(passed);
                    figureControle.pushUndoHistory(passed);
                    count = -1;
                    figureControle.popRedoHistory();
                    while (!figureControle.RedoHistoryIsEmpty()){
                        figureControle.pushMainStack(figureControle.popRedoHistory());
                    }
                    passed.setIsChanged(passed);
                    passed.setPenCol(PenCol.getValue().toString());
                    passed.setFillCol(FillCol.getValue().toString());
                    passed.setSliderWidth(SliderWidth.getValue());
                    figureControle.pushMainStack(passed);
                  //  main.push(passed);
                }
                if (count == 1)
                    while (!figureControle.RedoHistoryIsEmpty()){
                        figureControle.pushMainStack(figureControle.popRedoHistory());
                    }
                else
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
            figure.setIsCreated(figure);

            figureControle.pushMainStack(figure);

           // FugureControl.add(figure);
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
          //  FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
           // figureControle.pushMainStack(passed);
           // FugureControl.add(passed);
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
    void Save() throws IOException, ParserConfigurationException {
        Element e = null;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Save Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {

            String directorypath = selectedDirectory.getPath();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            String format = currentDate+".xml";
            String path = directorypath+"/"+format;

            File newfile = new File(path);
           // try {
                if (newfile.createNewFile()) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                  //  try{
                        builder = factory.newDocumentBuilder();
                        Document doc = builder.newDocument();
                        Element rootEle = doc.createElement("figures");

                        e = doc.createElement("figure");
                       /* e.appendChild(doc.createTextNode("type="+figure.getFigureType().toString()));
                        e.appendChild(doc.createTextNode("x1="+figure.getX1()));
                        e.appendChild(doc.createTextNode("y1="+figure.getY1()));
                        e.appendChild(doc.createTextNode("x2="+figure.getX2()));
                        e.appendChild(doc.createTextNode("y2="+figure.getY2()));
                        e.appendChild(doc.createTextNode("Fill="+figure.getFillCol()));
                        e.appendChild(doc.createTextNode("Pen="+figure.getPenCol()));
                        e.appendChild(doc.createTextNode("Width="+figure.getWidth()));*/

                        rootEle.appendChild(e);
                        doc.appendChild(rootEle);
                    try {
                        Transformer tr = TransformerFactory.newInstance().newTransformer();
                        tr.setOutputProperty(OutputKeys.INDENT, "yes");
                        tr.setOutputProperty(OutputKeys.METHOD, "xml");
                        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "figures.dtd");
                        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                        // send DOM to file
                        tr.transform(new DOMSource(doc),
                                new StreamResult(new FileOutputStream(path)));

                    } catch (TransformerException te) {
                        System.out.println(te.getMessage());
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                }
              //  }catch (Exception e) {
              //  alert(new Exception(e));}
           // } catch (Exception e){alert(new Exception(e));}


            //////////////////////////////////////////////////////////////////////////
        //    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          //  DocumentBuilder builder;

       /* String xml = "new";
       figureControle.printst();
        /////////////////////////////
        Document dom;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element rootEle = dom.createElement("roles");

            e = dom.createElement("role1");
            e.appendChild(dom.createTextNode(role1));
            rootEle.appendChild(e);

            e = dom.createElement("role2");
            e.appendChild(dom.createTextNode(role2));
            rootEle.appendChild(e);

            e = dom.createElement("role3");
            e.appendChild(dom.createTextNode(role3));
            rootEle.appendChild(e);

            e = dom.createElement("role4");
            e.appendChild(dom.createTextNode(role4));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try{
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));
            } catch (TransformerException te){ System.out.println(te.getMessage());}
        } catch (IOException ioe){System.out.println(ioe.getMessage());}
        catch (ParserConfigurationException pce){System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);}
*/}
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
