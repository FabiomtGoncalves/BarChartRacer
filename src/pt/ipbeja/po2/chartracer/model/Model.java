/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import pt.ipbeja.po2.chartracer.gui.Bar;
import pt.ipbeja.po2.chartracer.gui.BarChartRacerStart;
import pt.ipbeja.po2.chartracer.gui.Board;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Model implements Comparable<Integer>{

    private final Board board = new Board(this);
    private final ReadFile readFile = new ReadFile();

    private List<String> stats;
    private String year;
    private String city;
    private Color strokeColor;
    private View view;
    private Color barColor = Color.RED;
    private double count = 0;
    public int pop = 0;


    /**
     * @param bar
     * @param group
     * @param position
     * @param name
     * read all lines to one array of arrays of Strings
     * Source: Projeto de IP 2020-2021
     */
    public void sleep(Bar bar, Group group, Double position, String name, String date, Label dateYear){
        Thread t = new Thread( () ->  {
                Platform.runLater( () ->
                        {
                            Bar barNew = new Bar(position, bar.getWidth() + count,barColor, strokeColor);
                            //Text text = new Text();
                            //text.setText(name);
                            //text.setX(barNew.getWidth());
                            //System.out.println(text.getText());
                            dateYear.setText(date + "");
                            group.getChildren().addAll(barNew);
                            count += 0.1;
                        }
                );

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        t.start();
    }

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
        MenuItem bar = new MenuItem("Bar Color");
        skin.getItems().addAll(defaultSkin, stroke, bar);

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
            strokeColor = Color.TRANSPARENT;
            barColor = Color.RED;
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
                    case "Preto" -> strokeColor = Color.BLACK;
                    case "Azul" -> strokeColor = Color.BLUE;
                    case "Verde" -> strokeColor = Color.GREEN;
                }
            }
        });

        bar.setOnAction(actionEvent -> {
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
                    case "Laranja" -> barColor = Color.ORANGE;
                    case "Azul" -> barColor = Color.ROYALBLUE;
                    case "Verde" -> barColor = Color.SPRINGGREEN;
                }
            }

        });

        generateFile.setOnAction(actionEvent -> {
            this.board.generateFile(path, stage);
        });

        MenuItem biggest = new MenuItem("O Maior de Sempre");
        MenuItem biggestInX = new MenuItem("Os Maiores em Determinado Espaço de Tempo");
        MenuItem smallestInX = new MenuItem("Os Menores em Determinado Espaço de Tempo");
        MenuItem specificCity = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        menu.getItems().addAll(biggest, biggestInX, smallestInX, specificCity);

        biggest.setOnAction(event -> {
            group.getChildren().clear();
            this.board.biggest(group, path, barColor, strokeColor);
        });

        biggestInX.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
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
            this.board.biggestInSpecificYear(group, path, year, barColor, strokeColor);
        });

        smallestInX.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
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
            this.board.smallestInSpecificYear(group, path, year, barColor, strokeColor);
        });

        specificCity.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
            List<String> choices = new ArrayList<>();

            for (String[] strings : inputFile) {
                if (strings.length > 2) {
                    if (!choices.contains(strings[1])) {
                        choices.add(strings[1]);
                    }
                }
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha um cidade", choices);
            dialog.setTitle("Cidade");
            dialog.setHeaderText("Cidade");
            dialog.setContentText("Cidade:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(city -> this.city = city);
            this.board.specificCity(group, path, city, barColor, strokeColor);
        });

        return menuBar;
    }

    @Override
    public int compareTo(Integer pop) {
        if (this.pop > pop) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setView(View view) {
        this.view = view;
    }

}
