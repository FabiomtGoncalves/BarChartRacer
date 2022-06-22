/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.gui.Bar;

public interface View {

    String[][] readFileToStringArray2D(String filename, String separator);
    void path(String path);
    void sleep(Bar bar, Group group, Double position);
    MenuBar createMenu(Group group, String path, Stage stage);
}
