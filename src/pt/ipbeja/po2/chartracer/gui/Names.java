/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Names extends Text {

    public Names(String name, int positionY, Double fontSize){
        this.setText(name);
        this.setY(positionY);
        this.setFont(new Font(fontSize));
    }
}
