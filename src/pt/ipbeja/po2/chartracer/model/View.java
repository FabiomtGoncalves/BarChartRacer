/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.gui.Names;

public interface View {

    void draw(int population, Group group, double position, String cityName, Color strokeColor, Names textArray);

    void drawTitle(Group group, String title);

    void drawBiggestInSpecificYear(Group group, int position, String population, String name, Color barcolor, Color strokeColor);

    void generateFile(Stage stage, String[] datasetData);

    void drawSpecificCityBar(Group group, int position, String barWidth, Color barcolor, Color strokeColor);
}
