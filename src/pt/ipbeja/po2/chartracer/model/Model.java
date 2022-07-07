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
    private Color barColor = Color.RED;
    private int count = 0;
    private Names names;
    private Integer positionY = 50;
    private final int reset = 50;
    private final int numOfObjs = 12;
    private final ReadFile readFile = new ReadFile();
    private int divisor = 0;

    public void setView(View view) {
        this.view = view;
    }

    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     */
    public void chartRace(Group group, String path, Color barColor, Color strokeColor){
        positionY = reset;
        view.drawTitle(group, "The biggest");
        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
        String[][] data = new String[numOfObjs][2];

        List<Bar> rectArray = new ArrayList<>();
        List<Names> textArray = new ArrayList<>();


        for (int i = 0; i < numOfObjs; i++) {
            Bar bar = new Bar(positionY, 0, barColor, strokeColor);
            names = new Names(data[i][0], positionY + 30, 20.0);
            textArray.add(i, names);
            rectArray.add(i, bar);
            positionY += 70;

            data[i][0] = "";
            data[i][1] = "0";
        }

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                City city = new City(strings[1], Integer.parseInt(strings[3]));
                String date = strings[0];

                List<String> cityList = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    cityList.add(data[i][0]);
                }


                double smallest = rectArray.get(0).getWidth();

                int smallestPos = 0;
                int duplicate = 0;

                array2DSort(data);
                System.out.println(Arrays.deepToString(data));

                for (int i = 0; i < data.length; i++) {
                    if (data[i][0].equals("")){
                        break;
                    }
                    if (data[i][0].equals(city.getCityName())){
                        duplicate = i;
                    }
                }

                for (int j = 0; j < numOfObjs; j++) {
                    if (smallest > rectArray.get(j).getWidth() && !cityList.contains(city.getCityName())) {
                        smallest = rectArray.get(j).getWidth();
                        smallestPos = j;
                    }
                    else if(cityList.contains(city.getCityName())){
                        smallestPos = duplicate;
                        break;
                    }
                }
                City city2 = new City(data[smallestPos][0], Integer.parseInt(data[smallestPos][1]));
                int result = city.compareTo(city2);

                if (result > 0) {

                    double position = rectArray.get(smallestPos).getY();

                    rectArray.get(smallestPos).setWidth(city.getPopulation());
                    textArray.get(smallestPos).setText(city.getCityName());
                    textArray.get(smallestPos).setX(rectArray.get(smallestPos).getWidth());

                    data[smallestPos][0] = city.getCityName();
                    data[smallestPos][1]  = String.valueOf(city.getPopulation());

                    view.draw(city.getPopulation() / 25, group, position, city.getCityName(),
                            barColor, strokeColor, textArray.get(smallestPos), date);
                }
            }
        }
        System.out.println("Cities: " + Arrays.deepToString(data));
    }


    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     * The user is prompt with a dropdown with all the options from the dataset and then the program will draw the bars
     * for the information that was chosen.
     */
    public void biggestInSpecificYear(Group group, String path, String year, Color barColor, Color strokeColor) {
        checkFile(path);
        System.out.println(checkFile(path));
        positionY = reset;
        int datasetSize = 0;
        view.drawTitle(group, "The most populous cities in the world in " + year);

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        for (String[] strings : inputFile) {

            if (strings.length == 1){
                try {
                    Integer.parseInt(strings[0]);
                    datasetSize = Integer.parseInt(strings[0]);
                } catch (NumberFormatException ignored) {
                }

            }

            if (strings.length > 2) {

                if(strings[0].equals(year)){
                    break;
                }

            }
        }

        String[][] data = new String[datasetSize][2];
        int count = 0;


        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year)) {
                data[count][0] = inputFile[i][1];
                data[count][1] = inputFile[i][3];

                count++;
            }
        }

        array2DSort(data);

        for (int i = 0; i < data.length; i++) {
            view.drawBiggestInSpecificYear(group, positionY, data[i][1], data[i][0], barColor, strokeColor);
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
    public void specificCity(Group group, String path, String city, Color barColor, Color strokeColor) {
        positionY = 70;
        view.drawTitle(group, "Population of " + city + " through the years");

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                if (strings[1].equals(city)) {
                    view.drawSpecificCityBar(group, positionY, strings[3], barColor, strokeColor);
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
                    //System.out.println(cities[line][0]);
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
     * @param data
     * read all lines to one array of arrays of Strings
     * Source: Projeto de IP 2020-2021
     */


    private void array2DSort(String[][] data){
        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] array1, String[] array2) {
                Integer i1 = Integer.parseInt(array1[1]);
                Integer i2 = Integer.parseInt(array2[1]);

                return i2.compareTo(i1);
            }
        });
    }

    private int checkFile(String path){
        String asd = "..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets";
        switch (path){
            case "..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets\\cities.txt":
                divisor = 100;
                System.out.println("entrou");
                break;
        }
        return divisor;
    }

}
