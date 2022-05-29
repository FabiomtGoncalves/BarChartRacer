package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BarChartRacerStart extends Application {

    private ReadTxtFile readTxtFile;
    private Stage stage;
    private TextArea textArea;
    private String path;
    private Integer size = 50;
    BorderPane borderPane = new BorderPane();
    private String ano;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\Fábio\\Desktop\\Escola\\3º Ano\\PO2\\17646_20481_BarChartRacer\\src\\pt\\ipbeja\\po2\\chartracer"));
        chooser.setTitle("Escolha o Ficheiro");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(primaryStage);
        path = file.getAbsolutePath();
        this.readTxtFile = new ReadTxtFile(path);

        MenuBar menuBar = createMenu();

        borderPane.setTop(menuBar);
        borderPane.setCenter(textArea);

        Scene scene = new Scene(borderPane, 200, 200);
        stage.setScene(scene);

        //askSign();

        stage.show();

    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuButton btnClose = new MenuButton("Fechar");
        Menu close = new Menu("Fechar");
        Menu txtFile = new Menu("Bar Chart Racer");
        menu.getItems().addAll(txtFile);
        menuBar.getMenus().addAll(menu, close);

        txtFile.setOnAction(actionEvent -> {

        });

        close.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("A maior");
            alert.showAndWait();
        });

        MenuItem biggestCities = new MenuItem("Maiores Cidades");
        MenuItem biggestCityInX = new MenuItem("Maior Cidade em Determinado Ano");
        MenuItem population = new MenuItem("População de Determinada Cidade ao Longo dos Anos");
        txtFile.getItems().addAll(biggestCities, biggestCityInX, population);

        biggestCities.setOnAction(event -> {
            //borderPane.setLeft(null);
            elBiggestPopulationCity();
            //borderPane.setLeft(elBiggestPopulationCity());
        });

        biggestCityInX.setOnAction(event -> {
            borderPane.setLeft(null);
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Ano (1500 - 2018)");
            td.setTitle("Ano");
            Optional<String> result = td.showAndWait();
            result.ifPresent(year -> ano = year);
            borderPane.setLeft(specificYear());
            //System.out.println(td.getContentText());
        });

        population.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Nome da Cidade");
            td.setTitle("Cidade");
            td.show();
        });


        return menuBar;
    }

    public void elBiggestPopulationCity() {
        String[][] cities = this.readTxtFile.readFileToStringArray2D(path, ",");
        List<Integer> list = new ArrayList<Integer>();
        List<String> cityNames = new ArrayList<String>();
        List<String> country = new ArrayList<String>();
        int[] population = new int[12];

        for (int line = 0; line < cities.length; line++) {
            if (cities[line].length > 2 /*&& Integer.parseInt(cities[line][3]) > population*/) {
                list.add(Integer.parseInt(cities[line][3]));
                if (list.size() == 12){
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) > population[i]){
                            population[i] = list.get(i);
                        }
                    }
                    list.clear();
                }
                cityNames.add(cities[line][1]);
                country.add(cities[line][2]);
            }
        }

        /*System.out.println(Arrays.toString(asd));
        Arrays.sort(asd);
        System.out.println(Arrays.toString(asd));*/

        for (int i = 0; i < population.length; i++) {
            Rectangle rect = new Rectangle();
            rect.setX(50);
            rect.setY(size);
            rect.setHeight(50);
            rect.setWidth(population[i] / 100);
            Text cityName = new Text();
            cityName.setFont(new Font(20));
            cityName.setX((population[i] / 100) + 50);
            cityName.setY(size + 20);
            cityName.setText("Cidade: " + cityNames.get(i) + "\n País: " + country.get(i)); //TODO - Países e cidades nao tao com os nomes certos
            borderPane.getChildren().addAll(rect, cityName);
            size += 70;
        }

        //System.out.println(listCopy);
        //return r;
    }

    public Rectangle specificYear() {
        String[][] cities = this.readTxtFile.readFileToStringArray2D(path, ",");
        int population = 0;
        Rectangle r = new Rectangle();
        r.setX(300);
        r.setY(10);
        r.setHeight(100);
        for (String[] city : cities) {
            if (city.length > 2 && city[0].equals(ano) && Integer.parseInt(city[3]) > population) {
                population = Integer.parseInt(city[3]);
                r.setWidth(size = population / 100);
            }
        }
        return r;
    }

}
