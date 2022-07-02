/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {
    ReadFile readFile = new ReadFile();
    String[][] file = readFile.readFileToStringArray2D("src/pt/ipbeja/po2/chartracer/datasets/cities.txt", ",");

    String[][] firstYear = new String[12][5];
    String[][] lastYear = new String[12][5];

    @Test
    void teste1() {
        assertEquals(file[5][0], "1500");
        assertEquals(file[5][1], "Beijing");
        assertEquals(file[5][2], "China");
        assertEquals(file[5][3], "672");
        assertEquals(file[5][4], "East Asia");
        assertEquals(file[4797][0], "1842");
        assertEquals(file[4797][1], "London");
        assertEquals(file[4797][2], "United Kingdom");
        assertEquals(file[4797][3], "1990");
        assertEquals(file[4797][4], "Europe");
    }

    @Test
    void teste2() {
        int countFY = 0;
        int countLY = 0;
        String[] tempVar;

        for (int i = 0; i < file.length; i++) {
            if (file[i].length > 2) {
                if (file[i][0].equals("1500")) {
                    for (int j = 0; j < file[i].length; j++) {
                        firstYear[countFY][j] = file[i][j];
                    }
                    countFY++;
                } else if (file[i][0].equals("2018")) {
                    for (int j = 0; j < file[i].length; j++) {
                        lastYear[countLY][j] = file[i][j];
                    }
                    countLY++;
                }
            }
        }
        for (int i = 0; i < firstYear.length; i++) {
            for (int j = 0; j < firstYear.length; j++) {
                model.pop = Integer.parseInt(firstYear[i][3]);
                int result = model.compareTo(Integer.parseInt(firstYear[j][3]));

                if (result == 1) {
                    tempVar = firstYear[j];
                    firstYear[j] = firstYear[i];
                    firstYear[i] = tempVar;
                }
            }
        }

        for (int i = 0; i < lastYear.length; i++) {
            for (int j = 0; j < lastYear.length; j++) {
                model.pop = Integer.parseInt(lastYear[i][3]);
                int result = model.compareTo(Integer.parseInt(lastYear[j][3]));

                if (result == 1) {
                    tempVar = lastYear[j];
                    lastYear[j] = lastYear[i];
                    lastYear[i] = tempVar;
                }
            }
        }

        assertEquals(firstYear[0][1], "Beijing");
        assertEquals(firstYear[1][1], "Vijayanagar");
        assertEquals(firstYear[2][1], "Cairo");
        assertEquals(firstYear[3][1], "Tabriz");
        assertEquals(firstYear[4][1], "Hangzhou");
        assertEquals(lastYear[0][1], "Tokyo");
        assertEquals(lastYear[1][1], "Delhi");
        assertEquals(lastYear[2][1], "Shanghai");
        assertEquals(lastYear[3][1], "Beijing");
        assertEquals(lastYear[4][1], "Mumbai");
    }

    @Test
    void teste3() throws IOException {
        teste2();

        FileWriter writer = new FileWriter(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets\\teste3.txt"));

        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                writer.append(firstYear[i][0] + ",").append(firstYear[i][1] + ",").append(firstYear[i][2] + ",").append(firstYear[i][3] + ",").append(firstYear[i][4]).append("\n");
            } else {
                writer.append(lastYear[i][0] + ",").append(lastYear[i][1] + ",").append(lastYear[i][2] + ",").append(lastYear[i][3] + ",").append(lastYear[i][4]).append("\n");
            }
        }
        writer.close();

        String[][] teste3 = model.readFileToStringArray2D("src/pt/ipbeja/po2/chartracer/datasets/teste3.txt", ",");

        assertEquals(teste3[0][1], "Beijing");
        assertEquals(teste3[1][1], "Vijayanagar");
        assertEquals(teste3[2][1], "Cairo");
        assertEquals(teste3[3][1], "Tabriz");
        assertEquals(teste3[4][1], "Hangzhou");
        assertEquals(teste3[5][1], "São Paulo");
        assertEquals(teste3[6][1], "Mexico City");
        assertEquals(teste3[7][1], "Osaka");
        assertEquals(teste3[8][1], "Cairo");
        assertEquals(teste3[9][1], "Dhaka");
    }
}
