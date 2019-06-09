package sample;

import java.awt.*;
import java.util.Stack;

public interface FigureSerializer {
    String serialize(Stack<FigureAbstract> object);
    Stack<FigureAbstract> deserialize(Canvas canvas, Stack<String> rows);
}
