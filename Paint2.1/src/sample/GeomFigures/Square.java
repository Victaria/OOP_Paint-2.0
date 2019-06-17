package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;

import javax.xml.bind.ValidationEventLocator;

public class Square extends FigureAbstract  {

    public Square(double x1, double y1, double x2, double y2)
    {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.valueOf(getFillCol()));
        context.setStroke(Color.valueOf(getPenCol()));
        context.setLineWidth(getSliderWidth());

        double min;
        double w = getWidth();
        double h = getHight();
        if (w < h){ min = w;} else min = h;
        context.fillRect(getX1(), getY1(), min, min);
        context.strokeRect(getX1(), getY1(), min, min);
    }

    @Override
    public ValidationEventLocator getLocation() {
        return null;
    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public String getSystemId() {
        return null;
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public int getColumnNumber() {
        return 0;
    }

}
