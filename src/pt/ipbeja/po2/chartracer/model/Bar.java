package pt.ipbeja.po2.chartracer.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle {

    private final int height = 50;

    public Bar(int positionY){
        setY(positionY);
        setHeight(this.height);
        setFill(Color.VIOLET);
    }

}
