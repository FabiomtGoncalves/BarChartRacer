package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.control.MenuBar;

public interface View {

    MenuBar createMenu(Group group, String path);

}
