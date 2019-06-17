package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;
import sample.Interfaces.ISelectable;

import javax.xml.bind.ValidationEventLocator;

public class Ellipse extends FigureAbstract  implements ISelectable {

    public Ellipse(double x1, double y1, double x2, double y2)
    {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.valueOf(getFillCol()));
        context.setStroke(Color.valueOf(getPenCol()));
        context.setLineWidth(getSliderWidth());

        context.fillOval(getX1(), getY1(), getWidth(), getHight());
        context.strokeOval(getX1(), getY1(), getWidth(), getHight());
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
        gc.strokeOval(getX1(), getY1(), getWidth(), getHight());

        gc.setLineWidth(tempWidth);
    }

}
