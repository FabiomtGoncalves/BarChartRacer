package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle implements Comparable {

    private final int height = 50;
    private int width;

    public Bar(int positionY){
        this.setY(positionY);
        this.setHeight(this.height);
        this.setFill(Color.VIOLET);
        this.setWidth(width);
    }

    public int getRectWidth() {
        return width;
    }

    @Override
    public int compareTo(Object o) {
        Bar other = (Bar) o;

        if(getRectWidth() > other.getRectWidth()){
            return 1;
        }

        else if(getRectWidth() < other.getRectWidth()){
            return -1;
        }
        else{
            return 0;
        }

    }
}
