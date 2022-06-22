/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Board{

    private Names names;
    private Integer positionY = 50;
    private View view;
    private final int reset = 50;
    private final int numOfObjs = 12;
    private final BarChartRacerStart start = new BarChartRacerStart();
    private final Model model = new Model(this, start);

    public void setView(View view) {
        this.view = view;
    }

    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     */
    public void biggest(Group group, String path, Color barColor, Color strokeColor) {

        positionY = reset;
        Names title = new Names("The biggest", 0, 30.0);
        group.getChildren().add(title);
        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[] cityNames = new String[numOfObjs];
        int[] population = new int[numOfObjs];
        Bar[] rectArray = new Bar[numOfObjs];
        Names[] textArray = new Names[numOfObjs];

        for (int i = 0; i < population.length; i++) {

            Bar bar = new Bar(positionY, 0, barColor, strokeColor);
            names = new Names(cityNames[i], positionY + 30, 20.0);
            textArray[i] = names;
            rectArray[i] = bar;
            group.getChildren().addAll(names);
            positionY += 70;

        }

        for (String[] strings : inputFile) {

            if (strings.length > 2) {

                int tempPop = Integer.parseInt(strings[3]) / 100;
                Bar bar2 = new Bar(positionY, tempPop, barColor, strokeColor);
                String tempCity = strings[1];
                List<String> intList = new ArrayList<>(Arrays.asList(cityNames));

                double smallest = rectArray[0].getWidth();

                int smallestPos = 0;
                int duplicate = 0;

                for (int i = 0; i < cityNames.length; i++) {
                    if (cityNames[i] == null){
                        break;
                    }
                    if (cityNames[i].equals(tempCity)){
                        duplicate = i;
                    }
                }

                for (int j = 0; j < rectArray.length; j++) {
                    if (smallest > rectArray[j].getWidth() && !intList.contains(tempCity)) {
                        smallest = rectArray[j].getWidth();
                        smallestPos = j;
                    }
                    else if(intList.contains(tempCity)){
                        smallestPos = duplicate;
                        break;
                    }
                }

                int result = bar2.compareTo(rectArray[smallestPos]);

                if (result > 0) {

                    double position = rectArray[smallestPos].getY();

                    rectArray[smallestPos].setWidth(bar2.getWidth());
                    textArray[smallestPos].setText(tempCity);
                    textArray[smallestPos].setX(rectArray[smallestPos].getWidth());

                    population[smallestPos] = tempPop;
                    cityNames[smallestPos] = tempCity;

                    System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));

                    /*String[] tempVar;

                    for (int i = 0; i < population.length; i++) {
                        for (int j = 0; j < population.length; j++) {
                            model.pop = population[i];
                            int resultSort = model.compareTo(population[j]);

                            if(resultSort > 0) {
                                tempVar = population[j];
                                data[j] = data[i];
                                data[i] = tempVar;
                            }
                        }
                    }*/

                    view.sleep(bar2, group, position, tempCity);

                }
            }
        }

        System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));

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
        positionY = 70;

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world in " + year);
        group.getChildren().add(title);

        Bar[] bars = new Bar[numOfObjs];

        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[][] data = new String[12][2];
        int count = 0;


        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year)) {
                data[count][0] = inputFile[i][1];
                data[count][1] = inputFile[i][3];

                count++;
            }
        }

        String[] tempVar;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                model.pop = Integer.parseInt(data[i][1]);
                int result = model.compareTo(Integer.parseInt(data[j][1]));

                if(result > 0) {
                    tempVar = data[j];
                    data[j] = data[i];
                    data[i] = tempVar;
                }
            }
        }

        for (int i = 0; i < data.length; i++) {
            Bar bar = new Bar(positionY, Integer.parseInt(data[i][1]) / 50, barColor, strokeColor);
            Names names = new Names(data[i][0], positionY + 35, 20.0);
            Names pop = new Names(data[i][1], positionY + 15, 20.0);
            pop.setX(bar.getWidth() + 10);
            group.getChildren().addAll(bar, names, pop);

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

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("Population of " + city + " through the years");
        group.getChildren().add(title);

        String[][] inputFile = view.readFileToStringArray2D(path, ",");

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                if (strings[1].equals(city)) {
                    Bar bar = new Bar(positionY, Double.parseDouble(strings[3]) / 50, barColor, strokeColor);
                    //group.getChildren().addAll(bar);
                    view.sleep(bar, group, Double.valueOf(positionY), city);
                }
            }
        }
    }

    public void smallestInSpecificYear(Group group, String path, String year, Color barColor, Color strokeColor) {
        positionY = 70;

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The least populous cities in the world in " + year);
        group.getChildren().add(title);

        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[][] data = new String[12][2];
        int count = 0;


        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year)) {
                data[count][0] = inputFile[i][1];
                data[count][1] = inputFile[i][3];

                count++;
            }
        }



        Arrays.parallelSort(data, Comparator.comparingInt(o -> Integer.parseInt(o[1])));

        for (int i = 0; i < data.length; i++) {
            Bar bar = new Bar(positionY, Integer.parseInt(data[i][1]) / 50, barColor, strokeColor);
            Names names = new Names(data[i][0], positionY + 35, 20.0);
            Names pop = new Names(data[i][1], positionY + 15, 20.0);
            pop.setX(bar.getWidth() + 10);
            group.getChildren().addAll(bar, names, pop);

            positionY += 70;
        }
    }

    /**
     * @param path Path of the chosen dataset
     * @param stage
     * Generates a information file for the chosen dataset
     */
    public void generateFile(String path, Stage stage){

        String[][] inputFile = view.readFileToStringArray2D(path, ",");

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

            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            chooser.setInitialFileName("data_stats" + ".txt");
            File file = chooser.showSaveDialog(stage);
            Path path2 = file.toPath();

            try {
                Files.write(path2, List.of(datasetData));
            } catch (IOException e) {
                System.out.println("Não foi possível escrever o ficheiro.");
                e.printStackTrace();
            }

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

}
