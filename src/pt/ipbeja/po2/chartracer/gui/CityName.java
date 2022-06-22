/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CityName extends Text {

    private final int fontSize = 20;

    /**
     * @param name Name of the city or movie (data)
     * @param positionY Position of the object
     * To create Text objects for the names of the data represented bars with the following properties
     */
    public CityName(String name, int positionY){
        this.setText(name);
        this.setY(positionY);
        this.setFont(new Font(fontSize));
    }

}
