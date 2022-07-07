/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.po2.chartracer.gui.City;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {
    ReadFile readFile = new ReadFile();
    WriteToFile writeToFile = new WriteToFile();
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
        City[] cityArray1500 = new City[12];
        City[] cityArray2018 = new City[12];

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
                    cityArray1500[countFY] = city1500;
                    countFY++;
                } else if (strings[0].equals("2018")) {
                    for (int j = 0; j < strings.length; j++) {
                        lastYear[countLY][j] = strings[j];
                    }
                    City city2018 = new City(lastYear[countLY][1], Integer.parseInt(lastYear[countLY][3]));
                    city2018.setYear(lastYear[countLY][0]);
                    city2018.setCountry(lastYear[countLY][2]);
                    city2018.setContinent(lastYear[countLY][4]);
                    cityArray2018[countLY] = city2018;
                    countLY++;
                }
            }
        }

        Arrays.sort(cityArray1500, Collections.reverseOrder());
        Arrays.sort(cityArray2018, Collections.reverseOrder());

        assertEquals(cityArray1500[0].getCityName(), "Beijing");
        assertEquals(cityArray1500[1].getCityName(), "Vijayanagar");
        assertEquals(cityArray1500[2].getCityName(), "Cairo");
        assertEquals(cityArray1500[3].getCityName(), "Hangzhou");
        assertEquals(cityArray1500[4].getCityName(), "Tabriz");
        assertEquals(cityArray2018[0].getCityName(), "Tokyo");
        assertEquals(cityArray2018[1].getCityName(), "Delhi");
        assertEquals(cityArray2018[2].getCityName(), "Shanghai");
        assertEquals(cityArray2018[3].getCityName(), "Beijing");
        assertEquals(cityArray2018[4].getCityName(), "Mumbai");
    }

    @Test
    void teste3() {
        int countFY = 0;
        int countLY = 0;
        City[] cityArray1500 = new City[12];
        City[] cityArray2018 = new City[12];

        String[] citiesString = new String[10];

        String teste3Path = "src/pt/ipbeja/po2/chartracer/results/test3.txt";

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
                    cityArray1500[countFY] = city1500;
                    countFY++;
                } else if (strings[0].equals("2018")) {
                    for (int j = 0; j < strings.length; j++) {
                        lastYear[countLY][j] = strings[j];
                    }
                    City city2018 = new City(lastYear[countLY][1], Integer.parseInt(lastYear[countLY][3]));
                    city2018.setYear(lastYear[countLY][0]);
                    city2018.setCountry(lastYear[countLY][2]);
                    city2018.setContinent(lastYear[countLY][4]);
                    cityArray2018[countLY] = city2018;
                    countLY++;
                }
            }
        }
        Arrays.sort(cityArray1500, Collections.reverseOrder());
        Arrays.sort(cityArray2018, Collections.reverseOrder());

        for (int i = 0; i < 5; i++) {
            citiesString[i] = cityArray1500[i].getYear() + "," + cityArray1500[i].getCityName() + "," + cityArray1500[i].getCountry() + "," +
                    cityArray1500[i].getPopulation() + "," + cityArray1500[i].getContinent();
        }

        for (int i = 5; i < 10; i++) {
            citiesString[i] = cityArray2018[i].getYear() + "," + cityArray2018[i].getCityName() + "," + cityArray2018[i].getCountry() + "," +
                    cityArray2018[i].getPopulation() + "," + cityArray2018[i].getContinent();
        }

        writeToFile.write(Paths.get(teste3Path), List.of(citiesString));

        String[][] fileTest3 = readFile.readFileToStringArray2D(teste3Path, ",");

        assertEquals(fileTest3[0][1], "Beijing");
        assertEquals(fileTest3[1][1], "Vijayanagar");
        assertEquals(fileTest3[2][1], "Cairo");
        assertEquals(fileTest3[3][1], "Hangzhou");
        assertEquals(fileTest3[4][1], "Tabriz");
        assertEquals(fileTest3[5][1], "São Paulo");
        assertEquals(fileTest3[6][1], "Mexico City");
        assertEquals(fileTest3[7][1], "Osaka");
        assertEquals(fileTest3[8][1], "Cairo");
        assertEquals(fileTest3[9][1], "Dhaka");
    }
}
