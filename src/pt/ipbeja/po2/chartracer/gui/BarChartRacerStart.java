package pt.ipbeja.po2.chartracer.gui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.ipbeja.po2.chartracer.model.Bar;
import pt.ipbeja.po2.chartracer.model.CityName;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BarChartRacerStart extends Application {

    private Bar bar;
    private Bar bar2;
    private CityName cityName;
    private ReadTxtFile readTxtFile;
    private Stage stage;
    private String path;
    private Integer positionY = 50;
    private BorderPane borderPane = new BorderPane();
    private String year; //TODO mudar nome desta merda
    private Group group = new Group();
    private final int animationTime = 20000;
    private final int reset = 50;
    private final int sceneW = 800;
    private final int sceneH = 600;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("..\\17646_20481_BarChartRacer\\src\\pt\\ipbeja\\po2\\chartracer"));
        chooser.setTitle("Escolha o Ficheiro");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(primaryStage);
        path = file.getAbsolutePath();
        this.readTxtFile = new ReadTxtFile(path);

        MenuBar menuBar = createMenu();

        borderPane.setTop(menuBar);

        borderPane.setLeft(group);

        Scene scene = new Scene(borderPane, sceneW, sceneH);
        stage.setScene(scene);

        //stage.setFullScreen(true);
        stage.setMaximized(true);

        stage.show();

        /*Bar b1 = new Bar(positionY);
        Bar b2 = new Bar(positionY);

        System.out.println("Comparação: " + b1.compareTo(b2));
        int result = b1.compareTo(b2);

                        /*if (result < 0){
                            System.out.println("b1" + b1.compareTo(b2));
                        }
                        else if (result > 0) {
                            System.out.println("Comparação: " + b1.compareTo(b2));
                        }
                        else{
                            System.out.println("Comparação: " + b1.compareTo(b2));
                        }*/

    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Bar Chart Racer");

        Menu close = new Menu("Fechar");

        Menu skin = new Menu("Skin");
        MenuItem cor1 = new MenuItem("Cor1");
        skin.getItems().addAll(cor1);

        Menu data = new Menu("Data");
        MenuItem generateFile = new MenuItem("Generate File");
        data.getItems().addAll(generateFile);

        menuBar.getMenus().addAll(menu, skin, data, close);

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
            elBiggestPopulationCity();
        });

        biggestCityInX.setOnAction(event -> {
            group.getChildren().clear();
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Ano (1500 - 2018)");
            td.setTitle("Ano");
            Optional<String> result = td.showAndWait();
            result.ifPresent(year -> this.year = year);
            specificYear(year);
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
        positionY = reset;
        Text title = new Text();
        title.setFont(new Font(30));
        title.setText("The most populous cities in the world from 1500 to 2018");
        group.getChildren().add(title);

        String[][] cities = this.readTxtFile.readFileToStringArray2D(path, ",");
        List<Integer> list = new ArrayList<Integer>();
        List<String> listCityNames = new ArrayList<String>();
        String[] cityNames = new String[12];
        int[] population = new int[12];

        Rectangle[] rectArray = new Rectangle[12];
        Text[] textArray = new Text[12];

        int tempPop = 0;
        String tempCity = "";


        for (int i = 0; i < population.length; i++) {
            bar = new Bar(positionY, 0);

            cityName = new CityName(cityNames[i], positionY+30);
            textArray[i] = cityName;
            rectArray[i] = bar;
            group.getChildren().addAll(bar, cityName);
            positionY += 70;
        }

        for (int line = 0; line < cities.length; line++) { //TODO - para mostrar as cidades de forma mais lenta e ir mudando o for tem de ser mais lento Thread.sleep?
            if (cities[line].length > 2 /*&& Integer.parseInt(cities[line][3]) > population*/) {

                tempPop = Integer.parseInt(cities[line][3]);

                bar2 = new Bar(positionY, tempPop);

                tempCity = cities[line][1];
                list.add(Integer.parseInt(cities[line][3]));
                listCityNames.add(cities[line][1]);

                for (int i = 0; i < population.length; i++) {

                    System.out.println("Comparação: " + bar2.compareTo(rectArray[i]));
                    int result = bar2.compareTo(rectArray[i]);


                    //if (tempPop > population[i] && tempCity != cityNames[i]){

                    if (result > 0){
                        rectArray[i].setWidth(bar2.getWidth());
                        textArray[i].setText(tempCity);
                        textArray[i].setX(rectArray[i].getWidth());

                        TranslateTransition trans = new TranslateTransition(Duration.millis(animationTime), rectArray[i]);
                        trans.setFromX(rectArray[i].getX());
                        trans.setToX(rectArray[i].getWidth());
                        trans.play();

                        /*ScaleTransition st = new ScaleTransition(Duration.millis(animationTime), rectArray[i]);
                        st.setByX(100);
                        st.play();*/
                        /*TranslateTransition translate = new TranslateTransition();
                        translate.setNode(textArray[i]);
                        translate.setDuration(Duration.millis(animationTime));
                        translate.setByX(rectArray[i].getX());
                        translate.play();*/

                        population[i] = tempPop;
                        cityNames[i] = tempCity;
                        break;
                    }
                }
            }
        }

        System.out.println("Pop: " + Arrays.toString(population) + "Cities: " + Arrays.toString(cityNames));
        /*System.out.println("Rects lix: " + Arrays.toString(rectArray));
        System.out.println("Text lix: " + Arrays.toString(textArray));*/

    }

    public void specificYear(String ano) {
        String[][] cities = this.readTxtFile.readFileToStringArray2D(path, ",");
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
