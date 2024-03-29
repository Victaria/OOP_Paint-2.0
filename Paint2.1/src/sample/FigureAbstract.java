package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import javafx.scene.canvas.GraphicsContext;

public abstract class FigureAbstract implements LocatorEx {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String figType;

    private String fillCol;
    private String penCol;
    private double slidWidth;
    private Boolean isChanged;

    public FigureAbstract(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        penCol = Config.getInstance().getPenCol();
        fillCol = Config.getInstance().getFillCol();
        this.isChanged = false;
    }


    protected FigureAbstract() {
    }

    public void setIsChanged(Boolean changed){ this.isChanged = changed;}

    public Boolean getIsChanged(){return isChanged;}

    public abstract void draw(GraphicsContext context);

    public String getFillCol() {
        return fillCol;
    }

    public void setFillCol(String fillCol) {
        this.fillCol = fillCol;
    }

    public String getPenCol() {
        return penCol;
    }

    public void setPenCol(String penCol) {
        this.penCol = penCol;
    }

    public double getSliderWidth(){
        return slidWidth;
    }

    public void setSliderWidth(double sliderWidth) {
        slidWidth = sliderWidth;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getWidth(){
        return x2 - x1;
    }

    public double getHight(){
        return  y2 - y1;
    }

    public void setFigureType(String figureType){ this.figType = figureType;
    }

    public String getFigureType(){
        return this.figType;
    }

    public void resize(double dX, double dY){
        x2 += dX;
        y2 += dY;
    }

    public void moveFigure(double newX, double newY){
        x1 += newX;
        x2 += newX;
        y1 += newY;
        y2 += newY;
    }

    public Boolean LineSelected(double newX, double newY){
        return (((newX <= getX1() && newX >= getX2()) || (newX >= getX1() && newX <= getX2())) &&
                ((newY <= getY1() && newY >= getY2()) || (newY >= getY1() && newY <= getY2())) &&
                (Math.abs(((getX1() - newX) / (getY1() - newY)) - ((getX1() - getX2()) / (getY1() - getY2()))) < 3));
    }

    public Boolean FigureSelected(double newX, double newY){
        return (((newX <= getX1() && newX >= getX2()) || (newX >= getX1() && newX <= getX2())) &&
                ((newY <= getY1() && newY >= getY2()) || (newY >= getY1() && newY <= getY2())));
    }

    public ShapeSnapshot makeSnapshot() {
        return new ShapeSnapshot(this);
    }

    public FigureAbstract fromSnapshot(ShapeSnapshot snapshot) {
        this.x1 = snapshot.getX1();
        this.y1 = snapshot.getY1();
        this.figType = snapshot.getType();
        this.x2 = snapshot.getX2();
        this.y2 = snapshot.getY2();
        this.fillCol = snapshot.getFill();
        this.penCol = snapshot.getPen();
        this.slidWidth = snapshot.getPenWidth();
        return this;
    }
}

