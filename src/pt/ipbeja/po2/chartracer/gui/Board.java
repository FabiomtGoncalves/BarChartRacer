/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.ReadTxtFile;
import pt.ipbeja.po2.chartracer.model.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Board implements View{

    private CityName cityName;
    private Integer positionY = 50;
    private String year;
    private Color color;
    public View view;


    private final int animationTime = 20000;
    private final int reset = 50;
    private final int numOfObjs = 12;
    Rectangle[] rectArray = new Rectangle[numOfObjs];


    @Override
    public MenuBar createMenu(Group group, String path, Stage stage) {


        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Bar Chart Racer");

        Menu settings = new Menu("Definições");
        MenuItem devs = new MenuItem("Devs");
        MenuItem restart = new MenuItem("Reiniciar programa");
        MenuItem close = new MenuItem("Fechar programa");
        settings.getItems().addAll(devs, restart, close);

        Menu skin = new Menu("Skin");
        MenuItem defaultSkin = new MenuItem("Default");
        MenuItem stroke = new MenuItem("Stroke");
        skin.getItems().addAll(defaultSkin, stroke);

        Menu data = new Menu("Data");
        MenuItem generateFile = new MenuItem("Generate File");
        data.getItems().addAll(generateFile);

        menuBar.getMenus().addAll(menu, skin, data, settings);

        devs.setOnAction(actionEvent -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Devs");
            info.setHeaderText("Elaborado Por:");
            info.setContentText("""
                    Fábio Gonçalves nº17646
                    João Portelinha nº20481
                    
                    UC: Programação Orientada por Objetos (PO2)
                    """);
            info.show();
        });

        restart.setOnAction(actionEvent -> {
            BarChartRacerStart barChartRacerStart = new BarChartRacerStart();
            barChartRacerStart.start(stage);
        });

        close.setOnAction(actionEvent -> {
            System.exit(0);
        });

        defaultSkin.setOnAction(actionEvent -> {
            color = Color.TRANSPARENT;
        });

        stroke.setOnAction(actionEvent -> {
            List<String> choices = new ArrayList<>();
            choices.add("Preto");
            choices.add("Azul");
            choices.add("Verde");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha uma cor", choices);
            dialog.setTitle("Skin");
            dialog.setHeaderText("Cor da Stroke");
            dialog.setContentText("Cor:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                switch (result.get()) {
                    case "Preto" -> color = Color.BLACK;
                    case "Azul" -> color = Color.BLUE;
                    case "Verde" -> color = Color.GREEN;
                }
            }
        });

        generateFile.setOnAction(actionEvent -> {
            generateFile(path, stage);
        });

        MenuItem biggest = new MenuItem("O Maior de Sempre");
        MenuItem biggestInX = new MenuItem("Os Maiores em Determinado Espaço de Tempo");
        MenuItem population = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        menu.getItems().addAll(biggest, biggestInX, population);

        biggest.setOnAction(event -> {
            group.getChildren().clear();
            biggest(group, path);
        });

        biggestInX.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = ReadTxtFile.readFileToStringArray2D(path, ",");
            List<String> choices = new ArrayList<>();

            for (String[] strings : inputFile) {
                if (strings.length > 2) {
                    if (!choices.contains(strings[0])) {
                        choices.add(strings[0]);
                    }
                }
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha um tempo", choices);
            dialog.setTitle("Tempo Desejado");
            dialog.setHeaderText("Tempo Desejado");
            dialog.setContentText("Data / Tempo:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(year -> this.year = year);
            specificYear(group, path);
        });

        population.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Nome da Cidade");
            td.setTitle("Cidade");
            td.show();
        });

        return menuBar;

    }

    private void biggest(Group group, String path) {

        positionY = reset;
        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world from 1500 to 2018");
        group.getChildren().add(title);
        String[][] inputFile = ReadTxtFile.readFileToStringArray2D(path, ",");
        String[] cityNames = new String[numOfObjs];
        int[] population = new int[numOfObjs];
        //Rectangle[] rectArray = new Rectangle[numOfObjs];
        Text[] textArray = new Text[numOfObjs];

        for (int i = 0; i < population.length; i++) {

            Bar bar = new Bar(positionY, 0, color);
            cityName = new CityName(cityNames[i], positionY + 30);
            textArray[i] = cityName;
            rectArray[i] = bar;
            group.getChildren().addAll(bar, cityName);
            positionY += 70;

        }

        for (String[] strings : inputFile) {

            if (strings.length > 2 /*&& Integer.parseInt(cities[line][3]) > population*/) {

                int tempPop = Integer.parseInt(strings[3]) / 100;
                Bar bar2 = new Bar(positionY, tempPop, color);
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

                    sleep(bar2, group, posicao);

                    rectArray[x].setWidth(bar2.getWidth());
                    textArray[x].setText(tempCity);
                    textArray[x].setX(rectArray[x].getWidth());
                    /*rectArray[x].setWidth(bar2.getWidth());
                    textArray[x].setText(tempCity);
                    textArray[x].setX(rectArray[x].getWidth());*/

                    population[x] = tempPop;
                    cityNames[x] = tempCity;
                }
            }
        }

        System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));

    }

    private void sleep(Bar bar2, Group group, Double posicao){

        Thread t = new Thread( () ->  {
            for(int j = 0; j < 20; j++) {
                Platform.runLater( () ->
                        {
                            Bar barNew = new Bar(posicao, bar2.getWidth(), Color.BLACK);
                            barNew.setX(500);
                            group.getChildren().addAll(barNew);
                        }

                );

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void specificYear(Group group, String path) {
        positionY = 70;

        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world in " + year);
        group.getChildren().add(title);

        String[][] inputFile = ReadTxtFile.readFileToStringArray2D(path, ",");
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
            Bar bar = new Bar(positionY, Integer.parseInt(data[i][1]) / 50, color);
            CityName cityName = new CityName(data[i][0], positionY+35);
            CityName pop = new CityName(data[i][1], positionY+15);
            pop.setX(bar.getWidth() + 10);
            group.getChildren().addAll(bar, cityName, pop);

            positionY += 70;
        }
    }

    private void generateFile(String path, Stage stage){

        String[][] inputFile = ReadTxtFile.readFileToStringArray2D(path, ",");

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
