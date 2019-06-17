package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;
import sample.Interfaces.ISelectable;

import javax.xml.bind.ValidationEventLocator;

public class Line extends FigureAbstract implements ISelectable {

    public Line(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
            }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.valueOf(getFillCol()));
        context.setStroke(Color.valueOf(getPenCol()));
        context.setLineWidth(getSliderWidth());

        context.strokeLine(getX1(), getY1(), getX2(), getY2());
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

    @Override
    public void select(GraphicsContext gc) {
        double tempWidth = gc.getLineWidth();
        gc.setLineWidth(6);

        gc.setStroke(Color.BLUE);
        gc.strokeLine(getX1(), getY1(), getX2(), getY2());

        gc.setLineWidth(tempWidth);
    }

}
