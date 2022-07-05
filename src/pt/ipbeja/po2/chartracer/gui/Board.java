/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.ReadFile;
import pt.ipbeja.po2.chartracer.model.View;
import pt.ipbeja.po2.chartracer.model.WriteToFile;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Board implements View{

    private Names names;
    private Integer positionY = 50;
    private final int reset = 50;
    private final int numOfObjs = 12;
    private final ReadFile readFile = new ReadFile();
    private final WriteToFile writeToFile = new WriteToFile();
    private Model model;
    private Color barColor = Color.RED;
    private View view;


    private Label dateYear;

    public Board(Model model) {
        this.model = model;
        this.model.setView(this);
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

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
        String[][] data = new String[12][2];//TODO
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

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                if (strings[1].equals(city)) {
                    Bar bar = new Bar(positionY, Double.parseDouble(strings[3]) / 50, barColor, strokeColor);
                    //group.getChildren().addAll(bar);
                    //model.sleep(bar, group, Double.valueOf(positionY), city, "", dateYear);//TODO
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

        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
        int count = 0;
        String[][] data = new String[count][2];
        int asdasda = 0;

        for (int i = 0; i < inputFile.length; i++) {
            if(inputFile[i][0].equals(year) && asdasda == 0){
                asdasda = Integer.parseInt(inputFile[i--][0]);
                data = new String[asdasda][2];
            }
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
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            chooser.setInitialFileName("data_stats" + ".txt");
            File file = chooser.showSaveDialog(stage);
            Path path2 = file.toPath();

            writeToFile.write(path2, List.of(datasetData));

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


    @Override
    public void write(String state) {
        if (state.equals("asd")){
            System.out.println("funfou");
            //Alert alert = new Alert(Alert.AlertType.ERROR, "Funfou");
            //alert.showAndWait();
        } else{
            System.out.println("es bonec");
            //Alert alert = new Alert(Alert.AlertType.ERROR, "Es bonec");
            //alert.showAndWait();
        }
    }

    @Override
    public void draw(int population, Group group, double position, String cityName, Color strokeColor) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Bar barNew = new Bar(position, population,barColor, strokeColor);
                                    //Text text = new Text();
                                    //text.setText(name);
                                    //text.setX(barNew.getWidth());
                                    //System.out.println(text.getText());
                                    //dateYear.setText(date + "");
                                    group.getChildren().addAll(barNew);
                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    @Override
    public void drawRect(Group group, Names name) {
        group.getChildren().addAll(name);
    }


}
