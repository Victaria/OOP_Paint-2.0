package sample;

import javafx.scene.canvas.GraphicsContext;

public abstract class FigureAbstract {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private String FillCol;
    private String PenCol;
    private double SliderWidth;

    public FigureAbstract(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        PenCol = "#000000";
        FillCol = "#ffffff";
    }

    public void SecPoint(double dX, double dY){
        x2 += dX;
        y2 += dY;
    }

    public abstract void draw(GraphicsContext context);

    public String getFillCol() {
        return FillCol;
    }

    public void setFillCol(String fillCol) {
        this.FillCol = fillCol;
    }

    public String getPenCol() {
        return PenCol;
    }

    public void setPenCol(String penCol) {
        this.PenCol = penCol;
    }

    public double getSliderWidth(){
        return SliderWidth;
    }

    public void setSliderWidth(double sliderWidth) {
        SliderWidth = sliderWidth;
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
}
