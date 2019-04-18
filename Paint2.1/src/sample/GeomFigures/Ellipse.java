package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;

public class Ellipse extends FigureAbstract {

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
}
