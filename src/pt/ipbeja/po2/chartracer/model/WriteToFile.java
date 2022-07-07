/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriteToFile {

    /**
     * @param path Path where we want to save the file
     * @param datasetData The data that will be written
     * Write to a file
     */
    public void write(Path path, List<String> datasetData) {
        try {
            Files.write(path, datasetData);
        } catch (IOException e) {
            System.out.println("Não foi possível escrever o ficheiro.");
            e.printStackTrace();
        }
    }
}
