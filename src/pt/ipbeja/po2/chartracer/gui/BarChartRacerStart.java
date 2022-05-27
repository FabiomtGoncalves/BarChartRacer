package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class BarChartRacerStart extends Application {

    private ReadTxtFile readTxtFile;
    private Stage stage;
    private TextArea textArea;
    private String path;

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

        BorderPane borderPane = new BorderPane();
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
            System.out.println(elBiggestPopulationCity());
        });

        biggestCityInX.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Ano (1500 - 2018)");
            td.setTitle("Ano");
            td.show();
        });

        population.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Nome da Cidade");
            td.setTitle("Cidade");
            td.show();
        });


        return menuBar;
    }

    public String elBiggestPopulationCity()
    {
        String[][] cities = this.readTxtFile.readFileToStringArray2D(path, ",");
        int population = 0;
        String city = "";
        for (String[] strings : cities) {
            if (strings.length > 2 && Integer.parseInt(strings[3]) > population) {
                population = Integer.parseInt(strings[3]);
                city = "City: " + strings[1] + "\nPopulation: " + strings[3];
            }
        }
        return city;
    }

}
