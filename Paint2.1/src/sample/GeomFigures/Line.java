package sample.GeomFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.FigureAbstract;
import sample.interfaces.IMoveable;
import sample.interfaces.IResizeable;
import sample.interfaces.ISelectable;

public class Line extends FigureAbstract  {

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

}
