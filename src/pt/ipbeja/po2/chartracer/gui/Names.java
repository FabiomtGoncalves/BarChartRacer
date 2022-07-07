/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;


import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Names extends Text {

    public Names(String name, double positionY, Double fontSize){
        this.setText(name);
        this.fillProperty();
        this.setY(positionY);
        this.setFont(new Font(fontSize));
    }
}
