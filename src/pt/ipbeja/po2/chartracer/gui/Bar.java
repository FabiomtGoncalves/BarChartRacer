/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle{

    /**
     * @param positionY Y axis position
     * @param width Width of the bar
     * @param barColor Color of the bar
     * @param strokeColor Color of the stroke of the bar
     * To create Bar objects with the following properties
     */
    public Bar(double positionY, double width, Color barColor, Color strokeColor) {
        this.setY(positionY);
        this.setHeight(50);
        this.setFill(barColor);
        this.setWidth(width);
        this.setStrokeWidth(5);
        this.setStroke(strokeColor);
    }
}
