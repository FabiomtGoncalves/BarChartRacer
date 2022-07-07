/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Names extends Text {
    /**
     * @param name name to be displayed
     * @param positionY Y axis position
     * @param fontSize size of the font
     * To create Bar objects with the following properties
     */
    public Names(String name, double positionY, Double fontSize){
        this.setText(name);
        this.fillProperty();
        this.setY(positionY);
        this.setFont(new Font(fontSize));
    }
}
