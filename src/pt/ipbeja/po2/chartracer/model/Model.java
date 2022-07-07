/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.gui.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.util.*;

public class Model{

    private View view;
    private int count = 0;
    private Names names;
    private Integer positionY = 50;
    private final int reset = 50;
    private final int numOfObjs = 12;
    private final ReadFile readFile = new ReadFile();
    private String date;

    public void setView(View view) {
        this.view = view;
    }

    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     */
    public void chartRace(Group group, String path, Color[] barColor, Color strokeColor){
        positionY = reset;
        view.drawTitle(group, "The biggest");
        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        List<City> cityData = new ArrayList<>();
        List<Bar> rectArray = new ArrayList<>();

        for (int i = 0; i < numOfObjs; i++) {
            Bar bar = new Bar(positionY, 0, barColor[i], strokeColor);
            names = new Names("", positionY + 30, 20.0);
            rectArray.add(i, bar);
            positionY += 70;
            cityData.add(i, new City("", 0));
        }

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                City city = new City(strings[1], Integer.parseInt(strings[3]));
                date = strings[0];

                Collections.sort(cityData);
                Collections.reverse(cityData);

                //System.out.println("last pos" + cityData.get(cityData.size()-1).getPopulation());

                String[][] data = new String[numOfObjs][2];

                for (int i = 0; i < cityData.size(); i++) {
                    data[i][0] = cityData.get(i).getCityName();
                    data[i][1] = String.valueOf(cityData.get(i).getPopulation());
                }

                System.out.println("Cities: " + Arrays.deepToString(data));

                int smallestPos = 0;

                for (int i = 0; i < cityData.size(); i++) {
                    if (cityData.get(i).getCityName().equals("")){
                        smallestPos = cityData.size()-1;
                        break;
                    }
                    if (cityData.get(i).getCityName().contains(city.getCityName())){
                        smallestPos = i;
                        break;
                    } else{
                        smallestPos = cityData.size()-1;
                    }
                }


                City city2 = new City(cityData.get(smallestPos).getCityName(), cityData.get(smallestPos).getPopulation());
                int result = city.compareTo(city2);

                if (result > 0) {

                    double position = rectArray.get(smallestPos).getY();
                    rectArray.get(smallestPos).setWidth(city.getPopulation());
                    cityData.get(smallestPos).setCityName(city.getCityName());
                    cityData.get(smallestPos).setPopulation(city.getPopulation());

                    view.draw(city.getPopulation(), group, position, city.getCityName(),
                            barColor[smallestPos], strokeColor, date, checkFile(path));
                }
            }
        }


        for (int i = 0; i < cityData.size(); i++) {
            view.drawFinal(cityData.get(i).getPopulation(), group, rectArray.get(i).getY(),
                    cityData.get(i).getCityName(), barColor[i], strokeColor, date, i, checkFile(path));
        }
    }

    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     * The user is prompt with a dropdown with all the options from the dataset and then the program will draw the bars
     * for the information that was chosen.
     */
    public void biggestInSpecificYear(Group group, String path, String year, Color[] barColor, Color strokeColor) {
        checkFile(path);
        System.out.println(checkFile(path));
        positionY = reset;
        int datasetSize = 0;
        view.drawTitle(group, "The most populous cities in the world in " + year);

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        List<City> cityData = new ArrayList<>();

        int count = 0;


        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year)) {
                cityData.add(count, new City(inputFile[i][1], Integer.parseInt(inputFile[i][3])));
                count++;
            }
        }

        Collections.sort(cityData);
        Collections.reverse(cityData);

        for (int i = 0; i < numOfObjs; i++) {
            view.drawBiggestInSpecificYear(group, positionY, cityData.get(i).getPopulation(),
                    cityData.get(i).getCityName(), barColor[i], strokeColor, checkFile(path));
            positionY += 70;
        }
    }

    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     * The user is prompt with a dropdown with all the options from the dataset and then the program will draw the bars
     * for the information that was chosen.
     */
    public void specificCity(Group group, String path, String city, Color[] barColor, Color strokeColor) {
        positionY = 70;
        view.drawTitle(group, "Population of " + city + " through the years");

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                if (strings[1].equals(city)) {
                    view.drawSpecificCityBar(group, positionY, Integer.parseInt(strings[3]),
                            barColor[1], strokeColor, checkFile(path));
                }
            }
        }
    }

    /**
     * @param path Path of the chosen dataset
     * @param stage
     * Generates a information file for the chosen dataset
     */
    public void generateFile(String path, Stage stage){

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        int numDataSets = 0;
        String firstDate = "";
        String lastDate = "";
        int numCols = 0;
        int max = 0;
        int numLines = 0;
        int min = Integer.parseInt(inputFile[6][3]);

        for (String[] strings : inputFile) {

            if (strings.length > 2) {

                if (firstDate.isEmpty()) {
                    firstDate = strings[0];
                }

                if (max < Integer.parseInt(strings[3])) {
                    max = Integer.parseInt(strings[3]);
                }

                if (min > Integer.parseInt(strings[3])) {
                    min = Integer.parseInt(strings[3]);
                }

                numLines++;
                lastDate = strings[0];
                numCols = strings.length + 1;

            } else {
                try {
                    Integer.parseInt(strings[0]);
                    numDataSets++;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        String[] datasetData = {"Number of data sets in file: " + numDataSets, "First date: " + firstDate, "Last date: " + lastDate,
                "Average number of lines in each data set: " + (numLines / numDataSets), "Number of columns in each data set: " + numCols,
                "Maximum value considering all data sets: " + max, "Minimum value considering all data sets: " + min};

        try {
            view.generateFile(stage, datasetData);
        } catch (Exception e) {
            System.out.println("Opção de guardar ficheiro cancelada.");
        }

        System.out.println("\nNumber of data sets in file: " + numDataSets);
        System.out.println("First date: " + firstDate);
        System.out.println("Last date: " + lastDate);
        System.out.println("Average number of lines in each data set: " + (numLines / numDataSets));
        System.out.println("Number of columns in each data set: " + numCols);
        System.out.println("Maximum value considering all data sets: " + max);
        System.out.println("Minimum value considering all data sets: " + min);
    }

    /**
     * @param path
     * read all lines to one array of arrays of Strings
     * Source: Projeto de IP 2020-2021
     */

    private double checkFile(String path){
        double divisor = 0;
        if (path.contains("cities.txt")) {
            divisor = 25.0;
        }
        else if (path.contains("countries.txt")){
            divisor = 1000.0;
        }
        else if (path.contains("cities-usa.txt")){
            divisor = 500.0;
        }
        else if (path.contains("movies.txt")){
            divisor = 1000000.0;
        } else if (path.contains("baby-names.txt")){
            divisor = 100.0;
        }
        else if (path.contains("brands.txt")){
            divisor = 300.0;
        }
        return divisor;
    }
}
