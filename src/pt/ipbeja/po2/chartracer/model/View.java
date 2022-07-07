/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public interface View {

    void drawFinal(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date, int posI, double checkFile);

    void draw(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date, double checkFile);

    void drawTitle(Group group, String title);

    void drawBiggestInSpecificYear(Group group, int position, int population, String name, Color barColor, Color strokeColor, double checkFile);

    void generateFile(Stage stage, String[] datasetData);

    void drawSpecificCityBar(Group group, int position, int population, Color barcolor, Color strokeColor, double checkFile);
}
