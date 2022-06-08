package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle implements Comparable<Rectangle> {

    private final int height = 50;

    public Bar(int positionY, int width){
        this.setY(positionY);
        this.setHeight(this.height);
        this.setFill(Color.VIOLET);
        this.setWidth(width);
    }


    @Override
    public int compareTo(Rectangle bar) {

        if(this.getWidth() > bar.getWidth()){
            return 1;
        }

        else if(this.getWidth() < bar.getWidth()){
            return -1;
        }
        else{
            return 0;
        }

    }
}
