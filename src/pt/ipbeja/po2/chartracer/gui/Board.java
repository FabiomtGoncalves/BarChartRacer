package pt.ipbeja.po2.chartracer.gui;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pt.ipbeja.po2.chartracer.model.ReadTxtFile;
import pt.ipbeja.po2.chartracer.model.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Board extends BorderPane implements View {

    private Bar bar;
    private Bar bar2;
    private CityName cityName;
    private Integer positionY = 50;
    private String year;
    private int result;
    private Color color;


    private final int animationTime = 20000;
    private final int reset = 50;
    private final int numOfObjs = 12;

    public MenuBar createMenu(Group group, String path) {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Bar Chart Racer");

        Menu close = new Menu("Fechar");

        Menu skin = new Menu("Skin");
        MenuItem defaultSkin = new MenuItem("Default");
        MenuItem stroke = new MenuItem("Stroke");
        skin.getItems().addAll(defaultSkin, stroke);

        Menu data = new Menu("Data");
        MenuItem generateFile = new MenuItem("Generate File");
        data.getItems().addAll(generateFile);

        menuBar.getMenus().addAll(menu, skin, data, close);

        defaultSkin.setOnAction(actionEvent -> {
            color = Color.TRANSPARENT;
        });

        stroke.setOnAction(actionEvent -> {
            List<String> choices = new ArrayList<>();
            choices.add("Preto");
            choices.add("Azul");
            choices.add("Verde");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha uma cor", choices);
            dialog.setTitle("Cor da Stroke");
            dialog.setHeaderText("Escolha uma cor");
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

        close.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("A maior");
            alert.showAndWait();
        });

        MenuItem biggestCities = new MenuItem("Maiores Cidades");
        MenuItem biggestCityInX = new MenuItem("Maior Cidade em Determinado Ano");
        MenuItem population = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        menu.getItems().addAll(biggestCities, biggestCityInX, population);

        biggestCities.setOnAction(event -> {
            group.getChildren().clear();
            elBiggestPopulationCity(group, path);
        });

        biggestCityInX.setOnAction(event -> {
            group.getChildren().clear();
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Ano (1500 - 2018)");
            td.setTitle("Ano");
            Optional<String> result = td.showAndWait();
            result.ifPresent(year -> this.year = year);
            specificYear(year, group, path);
        });

        population.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Nome da Cidade");
            td.setTitle("Cidade");
            td.show();
        });

        return menuBar;

    }

    public void elBiggestPopulationCity(Group group, String path) {

        positionY = reset;
        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world from 1500 to 2018");
        group.getChildren().add(title);
        String[][] cities = ReadTxtFile.readFileToStringArray2D(path, ",");
        List<Integer> list = new ArrayList<Integer>();
        List<String> listCityNames = new ArrayList<String>();
        String[] cityNames = new String[numOfObjs];
        int[] population = new int[numOfObjs];
        Rectangle[] rectArray = new Rectangle[numOfObjs];
        Text[] textArray = new Text[numOfObjs];
        int tempPop = 0;
        String tempCity = "";


        for (int i = 0; i < population.length; i++) {

            bar = new Bar(positionY, 0, color);
            cityName = new CityName(cityNames[i], positionY+30);
            textArray[i] = cityName;
            rectArray[i] = bar;
            group.getChildren().addAll(bar, cityName);
            positionY += 70;

        }

        for (int line = 0; line < cities.length; line++) { //TODO - para mostrar as cidades de forma mais lenta e ir mudando o for tem de ser mais lento Thread.sleep?

            if (cities[line].length > 2 /*&& Integer.parseInt(cities[line][3]) > population*/) {

                tempPop = Integer.parseInt(cities[line][3]) / 100;
                bar2 = new Bar(positionY, tempPop, color);
                tempCity = cities[line][1];
                list.add(Integer.parseInt(cities[line][3]));
                listCityNames.add(cities[line][1]);

                for (int i = 0; i < population.length; i++) {

                    System.out.println("Comparação: " + bar2.compareTo(rectArray[i]));
                    result = bar2.compareTo(rectArray[i]);
                    List<String> intList = new ArrayList<>(Arrays.asList(cityNames));

                    if (result > 0 && !intList.contains(tempCity)){

                        rectArray[i].setWidth(bar2.getWidth());
                        textArray[i].setText(tempCity);
                        textArray[i].setX(rectArray[i].getWidth());

                        TranslateTransition trans = new TranslateTransition(Duration.millis(animationTime), rectArray[i]);
                        trans.setFromX(rectArray[i].getX());
                        trans.setToX(rectArray[i].getWidth());
                        trans.play();

                        population[i] = tempPop;
                        cityNames[i] = tempCity;
                        break;
                    }
                }
            }
        }

        System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));

    }

    public void specificYear(String ano, Group group, String path) {

        String[][] cities = ReadTxtFile.readFileToStringArray2D(path, ",");
        int population = 0;
        Rectangle r = new Rectangle();
        r.setX(300);
        r.setY(10);
        r.setHeight(100);

        for (String[] city : cities) {
            if (city.length > 2 && city[0].equals(ano) && Integer.parseInt(city[3]) > population) {
                population = Integer.parseInt(city[3]);
                r.setWidth(positionY = population / 100);
            }
        }

        group.getChildren().addAll(r);

    }

}
