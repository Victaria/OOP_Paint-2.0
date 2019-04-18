package sample;

import sample.GeomFigures.*;

public class FigureBuilder {

    public static FigureAbstract create(FigureTypes figureType, double X, double Y){
       switch (figureType){
           case Line:
                    return new Line(X, Y, X, Y);
        //    case Rectangle:
             //   return new Rectangle();
            case Circle:
                return new Circle(X, Y, X, Y);
          /*  case Square:
                return new Square();
            case Ellipse:
                return new Ellipse();
            case Triangle:
                return new Triangle();*/
           default:
               throw new IllegalArgumentException("Unknown type: " + figureType);
       }

    }
}
