/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle implements Comparable<Rectangle> {

    private final int height = 50;

    public Bar(double positionY, double width,Color barColor, Color strokeColor){
        this.setY(positionY);
        this.setHeight(this.height);
        this.setFill(barColor);
        this.setWidth(width);
        this.setStrokeWidth(5);
        this.setStroke(strokeColor);
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
