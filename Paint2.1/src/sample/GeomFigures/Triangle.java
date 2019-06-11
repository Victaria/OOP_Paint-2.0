package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;

import javax.xml.bind.ValidationEventLocator;

public class Triangle extends FigureAbstract {

    public Triangle(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.valueOf(getFillCol()));
        context.setStroke(Color.valueOf(getPenCol()));
        context.setLineWidth(getSliderWidth());

        double x1 = getX1(), x2 = getX2(), y1 = getY1(), y2 = getY2();

        double xpoint[] = new double[] {x1, (x1 + x2) / 2, x2};
        double ypoint[] = new double[] {y2, y1, y2};

        context.fillPolygon(xpoint, ypoint, xpoint.length);
        context.strokePolygon(xpoint, ypoint, xpoint.length);
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
