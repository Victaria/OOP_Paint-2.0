package sample;

import sample.GeomFigures.*;

public class FigureBuilder {

    public static FigureAbstract create(FigureTypes figureType, double X, double Y){
       switch (figureType){
           case Line:
                    return new Line(X, Y, X, Y);
            case Rectangle:
                return new Rectangle(X, Y, X, Y);
            case Circle:
                return new Circle(X, Y, X, Y);
            case Square:
                return new Square(X, Y, X, Y);
            case Ellipse:
                return new Ellipse(X, Y, X, Y);
           case Triangle:
               return new Triangle(X, Y, X, Y);
           default:
               throw new IllegalArgumentException("Unknown type: " + figureType);
       }

    }
}
