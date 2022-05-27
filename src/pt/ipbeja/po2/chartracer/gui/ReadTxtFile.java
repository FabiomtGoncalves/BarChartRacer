package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadTxtFile {

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

}
