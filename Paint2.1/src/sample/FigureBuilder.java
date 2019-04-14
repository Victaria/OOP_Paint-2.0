package sample;

import sample.GeomFigures.*;

public class FigureBuilder {
    private static final int DELTA_N = 20;

    public static FigureAbstract create(FigureTypes figureType, double X, double Y){
      //  switch (figureType){
         //   case Line:
                return new Line(X - DELTA_N, Y - DELTA_N, X, Y);
        //    case Rectangle:
             /*   return new Rectangle();
            case Circle:
                return new Circle();
            case Square:
                return new Square();
            case Ellipse:
                return new Ellipse();
            case Triangle:
                return new Triangle();*/
       // }

    }
}
