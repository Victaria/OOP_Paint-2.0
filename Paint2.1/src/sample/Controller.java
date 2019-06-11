package sample;


import com.sun.xml.internal.ws.transport.http.ResourceLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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
    private FigureTypes figureType;

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
        FigureAbstract cop;

        if (btnNone.isSelected()|| btnMove.isSelected()) {
            state = FigureState.SelectFigure;
            int count = figureControle.getUndoCount();

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
        Stack<FigureAbstract> copied = figureControle.copyStack();
        Element e;

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

                if (newfile.createNewFile()) {
                   // int i = 1;

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;

                    builder = factory.newDocumentBuilder();
                    Document doc = builder.newDocument();
                    Element rootEle = doc.createElement("figures");


                    while (!copied.isEmpty()) {
                        figure = copied.pop();
                        e = doc.createElement("figure");

                        Element figType = doc.createElement("type");
                        figType.appendChild(doc.createTextNode(figure.getFigureType().toString()));
                        e.appendChild(figType);

                        Element x1 = doc.createElement("x1");
                        x1.appendChild(doc.createTextNode(String.valueOf(figure.getX1())));
                        e.appendChild(x1);

                        Element y1 = doc.createElement("y1");
                        y1.appendChild(doc.createTextNode(String.valueOf(figure.getY1())));
                        e.appendChild(y1);

                        Element x2 = doc.createElement("x2");
                        x2.appendChild(doc.createTextNode(String.valueOf(figure.getX2())));
                        e.appendChild(x2);

                        Element y2 = doc.createElement("y2");
                        y2.appendChild(doc.createTextNode(String.valueOf(figure.getY2())));
                        e.appendChild(y2);

                        Element fill = doc.createElement("fill");
                        fill.appendChild(doc.createTextNode(figure.getFillCol()));
                        e.appendChild(fill);

                        Element pen = doc.createElement("pen");
                        pen.appendChild(doc.createTextNode(figure.getPenCol()));
                        e.appendChild(pen);

                        Element width = doc.createElement("width");
                        width.appendChild(doc.createTextNode(String.valueOf(figure.getSliderWidth())));
                        e.appendChild(width);

                        rootEle.appendChild(e);
                       // i++;
                    }

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
              }
    }


    @FXML
    void Upload() throws IOException, ParserConfigurationException, SAXException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringComments(true);
                factory.setIgnoringElementContentWhitespace(true);
                factory.setValidating(false);
                DocumentBuilder builder = factory.newDocumentBuilder();

                FileInputStream fis = new FileInputStream(file);
                InputSource is = new InputSource(fis);
                Document doc = builder.parse(is);

            NodeList figureNodes = doc.getElementsByTagName("figure");

            for (int i =0;i<figureNodes.getLength();i++){
                Node figureNode = figureNodes.item(i);

                if(figureNode.getNodeType() == Node.ELEMENT_NODE){
                    Element figureElement = (Element) figureNode;
                    String figType = figureElement.getElementsByTagName("type").item(0).getTextContent();
                    String x1 = figureElement.getElementsByTagName("x1").item(0).getTextContent();
                    String y1 = figureElement.getElementsByTagName("y1").item(0).getTextContent();
                    String x2 = figureElement.getElementsByTagName("x2").item(0).getTextContent();
                    String y2 = figureElement.getElementsByTagName("y2").item(0).getTextContent();
                    String fill = figureElement.getElementsByTagName("fill").item(0).getTextContent();
                    String pen = figureElement.getElementsByTagName("pen").item(0).getTextContent();
                    String width = figureElement.getElementsByTagName("width").item(0).getTextContent();
                    System.out.println(figType + x1 + y1 + x2 + y2 + fill + pen + width);

                    switch (figType) {
                        case ("Line"):
                            figureType = FigureTypes.Line;
                            break;
                        case ("Circle"):
                            figureType = FigureTypes.Circle;
                            break;
                        case ("Ellipse"):
                            figureType = FigureTypes.Ellipse;
                            break;
                        case ("Rectangle"):
                            figureType = FigureTypes.Rectangle;
                            break;
                        case ("Square"):
                            figureType = FigureTypes.Square;
                            break;
                        case ("Triangle"):
                            figureType = FigureTypes.Triangle;
                            break;
                        default:
                            figureType = FigureTypes.Line;
                            break;
                    }
                    ShapeFactory shapeFactory = new ShapeFactory();

                    FigureAbstract figure = shapeFactory.create(figureType, Double.valueOf(x1), Double.valueOf(y1));
                    figure.setFigureType(figureType);
                    figure.setFillCol(fill);
                    figure.setPenCol(pen);
                    figure.setSliderWidth(Double.valueOf(width));
                    figure.setIsCreated(figure);
                    figureControle.pushMainStack(figure);

                    double dX = Double.valueOf(x2) - Double.valueOf(x1);
                    double dY = Double.valueOf(y2) - Double.valueOf(y1);

                    FugureControl.resize(dX, dY);
                    FugureControl.redraw(myCanvas.getGraphicsContext2D(), myCanvas.getWidth(), myCanvas.getHeight());
                   // Integer.valueOf(x1) = Integer.valueOf(x2);
                   // firstY = newY;
                    figureControle.clearRedo();


                }
            }
        } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
