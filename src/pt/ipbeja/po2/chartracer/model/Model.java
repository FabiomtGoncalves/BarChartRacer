/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.gui.Bar;
import pt.ipbeja.po2.chartracer.gui.BarChartRacerStart;
import pt.ipbeja.po2.chartracer.gui.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Model implements View{

    private List<String> stats;
    private final Board board;
    private final BarChartRacerStart barChartRacerStart;
    private String year;
    private String city;
    private Color color;
    private final int animationTime = 20000;

    public Model(Board board, BarChartRacerStart barChartRacerStart){
        this.barChartRacerStart = barChartRacerStart;
        this.barChartRacerStart.setView(this);
        this.board = board;
        this.board.setView(this);
    }

    public void path(String path){
        Path p = Paths.get(path);
        try {
            this.stats = Files.readAllLines(p);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao Ler o Ficheiro de Texto.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

     public String[][] readFileToStringArray2D(String filename, String separator) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separator);
            }
            return allData;
        } catch (IOException e) {
            String errorMessage = "Error reading file " + filename;
            //showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + e.toString())  ;
            return new String[0][];
        }
    }

    public void sleep(Bar bar, Group group, Double position){
        Thread t = new Thread( () ->  {
            for(int j = 0; j < 20; j++) {
                Platform.runLater( () ->
                        {
                            Bar barNew = new Bar(position, bar.getWidth(), Color.BLACK);
                            barNew.setX(500);
                            group.getChildren().addAll(barNew);
                        }
                );

                try {
                    Thread.sleep(999000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
            this.board.generateFile(path, stage);
        });

        MenuItem biggest = new MenuItem("O Maior de Sempre");
        MenuItem biggestInX = new MenuItem("Os Maiores em Determinado Espaço de Tempo");
        MenuItem smallestInX = new MenuItem("Os Menores em Determinado Espaço de Tempo");
        MenuItem specificCity = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        menu.getItems().addAll(biggest, biggestInX, smallestInX, specificCity);

        biggest.setOnAction(event -> {
            group.getChildren().clear();
            this.board.biggest(group, path, color);
        });

        biggestInX.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = this.readFileToStringArray2D(path, ",");
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
            this.board.biggestInSpecificYear(group, path, year, color);
        });

        smallestInX.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = this.readFileToStringArray2D(path, ",");
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
            this.board.smallestInSpecificYear(group, path, year, color);
        });

        specificCity.setOnAction(event -> {
            group.getChildren().clear();

            String[][] inputFile = this.readFileToStringArray2D(path, ",");
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
            this.board.specificCity(group, path, city, color);
        });

        return menuBar;

    }

}
