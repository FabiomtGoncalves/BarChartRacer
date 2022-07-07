/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.ReadFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateMenu {

    private final Model model = new Model();
    private final Board board = new Board(model);
    private final ReadFile readFile = new ReadFile();
    private String year;
    private String city;
    private Color strokeColor;

    private Color[] colors;

    private final Color[] palletDefault = {Color.INDIANRED, Color.SKYBLUE, Color.SPRINGGREEN, Color.MEDIUMVIOLETRED,
            Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.AQUAMARINE, Color.CHARTREUSE, Color.CORNFLOWERBLUE,
            Color.CYAN, Color.DARKGREEN};

    private final Color[] pallet1 = {Color.DARKORANGE, Color.DARKORCHID, Color.GOLD, Color.INDIGO, Color.MEDIUMBLUE,
            Color.MEDIUMPURPLE, Color.MEDIUMSPRINGGREEN, Color.MISTYROSE, Color.ORANGERED, Color.PALEGREEN, Color.PLUM, Color.SKYBLUE};

    private final Color[] pallet2 = {Color.YELLOWGREEN, Color.TOMATO, Color.THISTLE, Color.TEAL, Color.STEELBLUE, Color.SLATEGREY,
            Color.SEASHELL, Color.PALETURQUOISE, Color.OLIVEDRAB, Color.PALEGOLDENROD, Color.SIENNA, Color.MOCCASIN};

    /**
     * @param group
     * @param path
     * @param stage
     * Creates the menu with all the options
     */
    public MenuBar create(Group group, String path, Stage stage) {
        colors = palletDefault;
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
            colors = palletDefault;
        });

        stroke.setOnAction(actionEvent -> {
            List<String> choices = new ArrayList<>();
            choices.add("Preto");
            choices.add("Azul");
            choices.add("Verde");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha uma Cor", choices);
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
            choices.add("Pallet 1");
            choices.add("Pallet 2");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Escolha uma pallet", choices);
            dialog.setTitle("Skin");
            dialog.setHeaderText("Cor da Pallet");
            dialog.setContentText("Pallet:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                switch (result.get()) {
                    case "Pallet 1" -> colors = pallet1;
                    case "Pallet 2" -> colors = pallet2;
                }
            }

        });

        generateFile.setOnAction(actionEvent -> {
            this.model.generateFile(path, stage);
        });

        MenuItem biggest = new MenuItem("O Maior de Sempre");
        MenuItem biggestInX = new MenuItem("Os Maiores em Determinado Espaço de Tempo");
        MenuItem specificCity = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        menu.getItems().addAll(biggest, biggestInX, specificCity);

        biggest.setOnAction(event -> {
            group.getChildren().clear();
            this.model.chartRace(group, path, colors, strokeColor);
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
            this.model.biggestInSpecificYear(group, path, year, colors, strokeColor);
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
            this.model.specificCity(group, path, city, colors, strokeColor);
        });
        return menuBar;
    }
}
