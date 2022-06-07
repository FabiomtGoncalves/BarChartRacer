package pt.ipbeja.po2.chartracer.model;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CityName extends Text {

    private final int fontSize = 20;

    public CityName(String name, int positionY){
        setText(name);
        setY(positionY);
        setFont(new Font(fontSize));
    }

}
