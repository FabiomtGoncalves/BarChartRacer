/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFile {

    /**
     * @param filename  file to read
     * @param separator separator for tokens in each line
     * @return array with one array of tokens in each position
     *         or empty array if error reading file
     *
     * read all lines to one array of arrays of Strings
     * Source: Projeto de IP 2020-2021
     */
    public String[][] readFileToStringArray2D(String filename, String separator) {
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
}
