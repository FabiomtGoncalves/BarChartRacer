package pt.ipbeja.po2.chartracer.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle implements Comparable {

    private final int height = 50;

    public Bar(int positionY){
        this.setY(positionY);
        this.setHeight(this.height);
        this.setFill(Color.VIOLET);
    }

    @Override
    public int compareTo(Object o) {
        Bar other = (Bar) o;

        return 0;
    }
}
