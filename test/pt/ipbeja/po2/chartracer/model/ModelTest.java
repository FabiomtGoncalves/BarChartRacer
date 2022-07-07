/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.po2.chartracer.gui.Bar;
import pt.ipbeja.po2.chartracer.gui.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        List<City> cityArray1500 = new ArrayList<>();
        List<City> cityArray2018 = new ArrayList<>();


        for (String[] strings : file) {
            if (strings.length > 2) {
                if (strings[0].equals("1500")) {
                    for (int j = 0; j < strings.length; j++) {
                        firstYear[countFY][j] = strings[j];
                    }
                    City city1500 = new City(firstYear[countFY][1], Integer.parseInt(firstYear[countFY][3]));
                    city1500.setYear(firstYear[countFY][0]);
                    city1500.setCountry(firstYear[countFY][2]);
                    city1500.setContinent(firstYear[countFY][4]);
                    cityArray1500.add(countFY, city1500);
                    countFY++;
                } else if (strings[0].equals("2018")) {
                    for (int j = 0; j < strings.length; j++) {
                        lastYear[countLY][j] = strings[j];
                    }
                    City city2018 = new City(lastYear[countLY][1], Integer.parseInt(lastYear[countLY][3]));
                    city2018.setYear(lastYear[countLY][0]);
                    city2018.setCountry(lastYear[countLY][2]);
                    city2018.setContinent(lastYear[countLY][4]);
                    cityArray2018.add(countLY, city2018);
                    countLY++;
                }
            }
        }

        Collections.sort(cityArray1500);
        Collections.reverse(cityArray1500);

        Collections.sort(cityArray2018);
        Collections.reverse(cityArray2018);

        assertEquals(cityArray1500.get(0).getCityName(), "Beijing");
        assertEquals(cityArray1500.get(1).getCityName(), "Vijayanagar");
        assertEquals(cityArray1500.get(2).getCityName(), "Cairo");
        assertEquals(cityArray1500.get(3).getCityName(), "Tabriz");
        assertEquals(cityArray1500.get(4).getCityName(), "Hangzhou");
        assertEquals(cityArray2018.get(0).getCityName(), "Tokyo");
        assertEquals(cityArray2018.get(1).getCityName(), "Delhi");
        assertEquals(cityArray2018.get(2).getCityName(), "Shanghai");
        assertEquals(cityArray2018.get(3).getCityName(), "Beijing");
        assertEquals(cityArray2018.get(4).getCityName(), "Mumbai");
    }

//    @Test
//    void teste3() throws IOException {
//        teste2();
//
//        FileWriter writer = new FileWriter(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets\\teste3.txt"));
//
//        for (int i = 0; i < 10; i++) {
//            if (i < 5) {
//                writer.append(firstYear[i][0] + ",").append(firstYear[i][1] + ",").append(firstYear[i][2] + ",").append(firstYear[i][3] + ",").append(firstYear[i][4]).append("\n");
//            } else {
//                writer.append(lastYear[i][0] + ",").append(lastYear[i][1] + ",").append(lastYear[i][2] + ",").append(lastYear[i][3] + ",").append(lastYear[i][4]).append("\n");
//            }
//        }
//        writer.close();
//
//        String[][] teste3 = model.readFileToStringArray2D("src/pt/ipbeja/po2/chartracer/datasets/teste3.txt", ",");
//
//        assertEquals(teste3[0][1], "Beijing");
//        assertEquals(teste3[1][1], "Vijayanagar");
//        assertEquals(teste3[2][1], "Cairo");
//        assertEquals(teste3[3][1], "Tabriz");
//        assertEquals(teste3[4][1], "Hangzhou");
//        assertEquals(teste3[5][1], "São Paulo");
//        assertEquals(teste3[6][1], "Mexico City");
//        assertEquals(teste3[7][1], "Osaka");
//        assertEquals(teste3[8][1], "Cairo");
//        assertEquals(teste3[9][1], "Dhaka");
//    }
}
