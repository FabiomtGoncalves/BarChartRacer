/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadTxtFile implements View{

    private List<String> stats;

    public ReadTxtFile(String path){
        Path p = Paths.get(path);
        try {
            this.stats = Files.readAllLines(p);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao Ler o Ficheiro de Texto.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public static String[][] readFileToStringArray2D(String filename, String separator) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separator);
            }
            return allData;
        } catch (IOException e) {
            String errorMessage = "Error reading file " + filename;
            //showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString())  ;
            return new String[0][];
        }
    }

    @Override
    public MenuBar createMenu(Group group, String path, Stage stage) {
        return null;
    }

}
