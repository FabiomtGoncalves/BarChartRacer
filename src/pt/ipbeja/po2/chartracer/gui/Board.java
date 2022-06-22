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
import pt.ipbeja.po2.chartracer.model.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Board{

    private CityName cityName;
    private Integer positionY = 50;
    private View view;
    private final int reset = 50;
    private final int numOfObjs = 12;
    Rectangle[] rectArray = new Rectangle[numOfObjs];

    public void setView(View view) {
        this.view = view;
    }


    public void biggest(Group group, String path, Color barColor, Color strokeColor) {

        positionY = reset;
        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world from 1500 to 2018");
        group.getChildren().add(title);
        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[] cityNames = new String[numOfObjs];
        int[] population = new int[numOfObjs];
        //Rectangle[] rectArray = new Rectangle[numOfObjs];
        Text[] textArray = new Text[numOfObjs];

        for (int i = 0; i < population.length; i++) {

            Bar bar = new Bar(positionY, 0, barColor, strokeColor);
            cityName = new CityName(cityNames[i], positionY + 30);
            textArray[i] = cityName;
            rectArray[i] = bar;
            group.getChildren().addAll(bar, cityName);
            positionY += 70;

        }

        for (String[] strings : inputFile) {

            if (strings.length > 2 /*&& Integer.parseInt(cities[line][3]) > population*/) {

                int tempPop = Integer.parseInt(strings[3]) / 100;
                Bar bar2 = new Bar(positionY, tempPop, barColor, strokeColor);
                String tempCity = strings[1];

                List<String> intList = new ArrayList<>(Arrays.asList(cityNames));

                double smallest = rectArray[0].getWidth();

                int x = 0;

                for (int j = 0; j < rectArray.length; j++) {
                    if (smallest > rectArray[j].getWidth() && !intList.contains(tempCity)) {
                        smallest = rectArray[j].getWidth();
                        System.out.println("entrou");
                        x = j;
                    }
                }

                System.out.println("X: " + x);

                int result = bar2.compareTo(rectArray[x]);

                if (result > 0) {

                    double posicao = rectArray[x].getY();

                    view.sleep(bar2, group, posicao);

                    rectArray[x].setWidth(bar2.getWidth());
                    textArray[x].setText(tempCity);
                    textArray[x].setX(rectArray[x].getWidth());

                    population[x] = tempPop;
                    cityNames[x] = tempCity;
                }
            }
        }

        System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));

    }


    public void biggestInSpecificYear(Group group, String path, String year, Color barColor, Color strokeColor) {
        positionY = 70;

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world in " + year);
        group.getChildren().add(title);

        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[][] data = new String[12][2]; //TODO - Fix nesse 12 que isso fica pequeno
        int count = 0;


        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year)) {
                data[count][0] = inputFile[i][1];
                data[count][1] = inputFile[i][3];

                count++;
            }
        }
        Arrays.parallelSort(data, Comparator.comparingInt(o -> Integer.parseInt(o[1])));
        Collections.reverse(Arrays.asList(data));


        for (int i = 0; i < data.length; i++) {
            Bar bar = new Bar(positionY, Integer.parseInt(data[i][1]) / 50, barColor, strokeColor);
            CityName cityName = new CityName(data[i][0], positionY+35);
            CityName pop = new CityName(data[i][1], positionY+15);
            pop.setX(bar.getWidth() + 10);
            group.getChildren().addAll(bar, cityName, pop);

            positionY += 70;
        }
    }

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
                    //TODO - Desenhar barras o width em strings[3] || correr tudo e meter todas as pops num array, correr o array a atualizar a barra
                    Bar bar = new Bar(positionY, Float.parseFloat(strings[3]) / 50, barColor, strokeColor);
                    group.getChildren().addAll(bar);
                    //view.sleep(bar, group, Double.valueOf(positionY));
                }
            }
        }
    }

    public void smallestInSpecificYear(Group group, String path, String year, Color barColor, Color strokeColor) { //TODO - Não faz grande sentido sendo q é o anterior mas pela ordem contraria
        positionY = 70;

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The least populous cities in the world in " + year);
        group.getChildren().add(title);

        String[][] inputFile = view.readFileToStringArray2D(path, ",");
        String[][] data = new String[12][2]; //TODO - Fix nesse 12 que isso fica pequeno
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
            CityName cityName = new CityName(data[i][0], positionY+35);
            CityName pop = new CityName(data[i][1], positionY+15);
            pop.setX(bar.getWidth() + 10);
            group.getChildren().addAll(bar, cityName, pop);

            positionY += 70;
        }
    }

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

        String[] predictions = {"Number of data sets in file: " + numDataSets, "First date: " + firstDate, "Last date: " + lastDate,
                "Average number of lines in each data set: " + (numLines / numDataSets), "Number of columns in each data set: " + numCols,
                "Maximum value considering all data sets: " + max, "Minimum value considering all data sets: " + min};


        try {

            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("..\\17646_20481_BarChartRacer\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            chooser.setInitialFileName("data_stats" + ".txt");
            File file = chooser.showSaveDialog(stage);
            Path path2 = file.toPath();

            try {
                Files.write(path2, List.of(predictions));
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
