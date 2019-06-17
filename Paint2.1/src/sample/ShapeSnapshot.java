package sample;


import java.awt.*;

public class ShapeSnapshot {
    private String type;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String fill;
    private String pen;
    private double penWidth;
    private FigureAbstract figshape;
    private Boolean isChange;

    ShapeSnapshot(FigureAbstract shape) {
        this.type = shape.getFigureType();
        this.x1 = shape.getX1();
        this.y1 = shape.getY1();
        this.x2 = shape.getX2();
        this.y2 = shape.getY2();
        this.fill = shape.getFillCol();
        this.pen = shape.getPenCol();
        this.penWidth = shape.getSliderWidth();
        this.figshape = shape;
        this.isChange = shape.getIsChanged();
    }

    public FigureAbstract  getFigshape(){
        return figshape;
    }

    public Boolean getIsChange(){
        return isChange;
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

    public String getType() {
        return type;
    }

    public double getPenWidth() {
        return penWidth;
    }

    public String getFill() {
        return fill;
    }

    public String getPen() {
        return pen;
    }

}
