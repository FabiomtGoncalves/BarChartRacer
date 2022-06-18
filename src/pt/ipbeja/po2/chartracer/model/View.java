/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public interface View {

    MenuBar createMenu(Group group, String path, Stage stage);

}
