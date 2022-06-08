package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CityName extends Text {

    private final int fontSize = 20;

    public CityName(String name, int positionY){
        this.setText(name);
        this.setY(positionY);
        this.setFont(new Font(fontSize));
    }

}
