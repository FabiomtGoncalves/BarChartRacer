/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import pt.ipbeja.po2.chartracer.gui.Names;

public interface View {

    void write(String state);

    void draw(int population, Group group, double position, String cityName, Color strokeColor);

    void drawRect(Group group, Names name);

    //void sleep(int population, Group group, Double position, String name, String date, Label dateYear, Color strokeColor);

}
