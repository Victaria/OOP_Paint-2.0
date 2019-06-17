package sample;


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
import sample.Factory.ShapeFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public ToggleButton btn1;

    @FXML
    private ToggleButton btn2;

    @FXML
    private ToggleButton btn3;

    @FXML
    private ToggleButton btn4;

    @FXML
    private ToggleButton btn5;

    @FXML
    private ToggleButton btn6;

    @FXML
    private ToggleButton btn7;

    @FXML
    private ToggleButton btn8;

    @FXML
    private ToggleButton btn9;

    @FXML
    private ToggleButton btn10;

    @FXML
    private ToggleButton btn11;

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
    private String figureType;

    @FXML
     void initialize(){
        PenCol.setValue(Color.BLACK);
        FillCol.setValue(Color.WHITE);
        graphicsContext = myCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(3);

        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);
        btn10.setDisable(true);
        btn11.setDisable(true);
        btnNone.setDisable(true);
        btnMove.setDisable(true);

        List<Class<FigureAbstract>> spf  = ShapeFactory.getActualFigures();
        String name;

        int m = 1;

        for (Class i : spf){
            name = i.toString().substring(25);
            System.out.println(name);

            if(m==1){
                btn1.setDisable(false);
                btn1.setText(name);
            } else if (m==2){
                btn2.setDisable(false);
                btn2.setText(name);
            } else if (m==3){
                btn3.setDisable(false);
                btn3.setText(name);
            } else if (m==4){
                btn4.setDisable(false);
                btn4.setText(name);
            } else if (m==5){
                btn5.setDisable(false);
                btn5.setText(name);
            } else if (m==6){
                btn6.setDisable(false);
                btn6.setText(name);
            }else if (m==7){
                btn7.setDisable(false);
                btn7.setText(name);
            } else if (m==8){
                btn8.setDisable(false);
                btn8.setText(name);
            } else if (m==9){
                btn9.setDisable(false);
                btn9.setText(name);
            } else if (m==10){
                btn10.setDisable(false);
                btn10.setText(name);
            } else if (m==11){
                btn11.setDisable(false);
                btn11.setText(name);
            }

            m++;
        }
    }




    @FXML
     void CanvOnPressed(MouseEvent event){

        firstX =  event.getX();
        firstY =  event.getY();

        if ((btnNone.isSelected()|| btnMove.isSelected()) && !FugureControl.getHistory().isEmpty()) {
            state = FigureState.SelectFigure;
            int count = figureControle.getUndoCount();

            while (count > 0){
                passed = figureControle.popMainStack(); //take an element
                figureControle.pushRedoHistory(passed); //add to Redo Stack
                figureType = passed.getFigureType();
                if ((((figureType == "Line") && (passed.LineSelected(firstX, firstY)))||(passed.FigureSelected(firstX, firstY)))){

                    passed.setIsChanged(true);

                    figureControle.pushUndoHistory(passed.makeSnapshot());
                    count = -1;
                    figureControle.popRedoHistory();
                    while (!figureControle.RedoHistoryIsEmpty()){
                        figureControle.pushMainStack(figureControle.popRedoHistory());
                    }
                    passed.setIsChanged(true);
                    passed.setPenCol(PenCol.getValue().toString());
                    passed.setFillCol(FillCol.getValue().toString());
                    passed.setSliderWidth(SliderWidth.getValue());
                    figureControle.pushMainStack(passed.makeSnapshot());}

                if (count == 1)
                    while (!figureControle.RedoHistoryIsEmpty()){
                        figureControle.pushMainStack(figureControle.popRedoHistory());
                    }
                else
                    count--;
            } if (count == 0) { while (!figureControle.RedoHistoryIsEmpty()){
                figureControle.pushMainStack(figureControle.popRedoHistory());
                passed = null;
            }}
        }
        else if (btn1.isSelected() || btn2.isSelected() || btn3.isSelected() || btn4.isSelected()|| btn5.isSelected() || btn6.isSelected() || btn7.isSelected() ||btn8.isSelected() ||btn9.isSelected() ||btn10.isSelected() || btn11.isSelected() ){
           ShapeFactory shapeFactory = new ShapeFactory();
            
            state = FigureState.DrawFigure;
            figureType = null;

            if (btn1.isSelected()){
                figureType = btn1.getText();
            } else if (btn2.isSelected()){
                figureType = btn2.getText();
            } else if (btn3.isSelected()){
                figureType = btn3.getText();
            } else if (btn4.isSelected()){
                figureType = btn4.getText();
            } else if (btn5.isSelected()){
                figureType = btn5.getText();
            } else if (btn6.isSelected()){
                figureType = btn6.getText();
            } else if (btn7.isSelected()){
                figureType = btn7.getText();
            } else if (btn8.isSelected()){
                figureType = btn8.getText();
            } else if (btn9.isSelected()){
                figureType = btn9.getText();
            } else if (btn10.isSelected()){
                figureType = btn10.getText();
            } else if (btn11.isSelected()){
                figureType = btn11.getText();
            } else figureType = null;

            FigureAbstract figure = shapeFactory.create(figureType, firstX, firstY);
            figure.setFigureType(figureType);
            figure.setFillCol(FillCol.getValue().toString());
            figure.setPenCol(PenCol.getValue().toString());
            figure.setSliderWidth(SliderWidth.getValue());

            figureControle.pushMainStack(figure.makeSnapshot());

            if (!FugureControl.getHistory().isEmpty())
            {
                btnMove.setDisable(false);
                btnNone.setDisable(false);
            }

        }

    }

    @FXML
     void CanvOnDragged(MouseEvent event){
        double newX = event.getX();
        double newY = event.getY();
        double dX = newX - firstX;
        double dY = newY - firstY;

        if ((btnMove.isSelected() && !FugureControl.getHistory().isEmpty() && (passed != null))){
            passed.moveFigure(dX, dY);
        } else if (!btnMove.isSelected() || (!btnNone.isSelected() && passed != null))
        { FugureControl.resize(dX, dY);}
        FugureControl.redraw(myCanvas.getGraphicsContext2D());
        firstX = newX;
        firstY = newY;
        figureControle.clearRedo();
    }

    @FXML
    void CanvOnKeyPressed(KeyEvent event){
        if (undo.match(event)) {
           figureControle.undoLast();
           FugureControl.redraw(myCanvas.getGraphicsContext2D());
        }
        if (redo.match(event)){
            figureControle.redoLast();
            FugureControl.redraw(myCanvas.getGraphicsContext2D());
        }
    }

    @FXML
    void RedoSelected(){
        figureControle.redoLast();
        FugureControl.redraw(myCanvas.getGraphicsContext2D());
        if (!FugureControl.getHistory().isEmpty())
        {
            btnMove.setDisable(false);
            btnNone.setDisable(false);
        } else {
            btnNone.setDisable(true);
            btnMove.setDisable(true);
        }
    }

    @FXML
    void UndoSelected(){
        figureControle.undoLast();
        FugureControl.redraw(myCanvas.getGraphicsContext2D());
        if (!FugureControl.getHistory().isEmpty())
        {
            btnMove.setDisable(false);
            btnNone.setDisable(false);
        } else {
            btnNone.setDisable(true);
            btnMove.setDisable(true);
        }
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
                        figType.appendChild(doc.createTextNode(figure.getFigureType()));
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
                try{
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


                    figureType = figType;


                    List<Class<FigureAbstract>> spf  = ShapeFactory.getActualFigures();
                String name;

                    int k = 0;
                    for (Class p : spf){
                        name = p.toString().substring(25);

                        if(name.equals(figureType)){k=1; }
                    }

                    if (k == 1) {
                        ShapeFactory shapeFactory = new ShapeFactory();

                        FigureAbstract figure = shapeFactory.create(figureType, Double.valueOf(x1), Double.valueOf(y1));


                        figure.setFigureType(figureType);
                        figure.setFillCol(fill);
                        figure.setPenCol(pen);
                        figure.setSliderWidth(Double.valueOf(width));
                        figureControle.pushMainStack(figure.makeSnapshot());

                        double dX = Double.valueOf(x2) - Double.valueOf(x1);
                        double dY = Double.valueOf(y2) - Double.valueOf(y1);

                        FugureControl.resize(dX, dY);
                        FugureControl.redraw(myCanvas.getGraphicsContext2D());
                        figureControle.clearRedo();
                    }
                    }

            }
                    if (!FugureControl.getHistory().isEmpty())
                    {
                        btnMove.setDisable(false);
                        btnNone.setDisable(false);
                    } else {
                        btnNone.setDisable(true);
                        btnMove.setDisable(true);
                    }
                }
                catch (Exception e){
                    System.out.println("File is invalid");
                }
        } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
