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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:\\\\Users\\\\Fábio\\\\Desktop\\\\Escola\\\\3º Ano\\\\PO2\\\\GP4"));
        chooser.setTitle("Escolha o Ficheiro");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(primaryStage);
        String path = file.getAbsolutePath();
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
        txtFile.getItems().addAll(biggestCities, biggestCityInX);

        biggestCities.setOnAction(event -> {

        });

        biggestCityInX.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Introduza o Ano (1500 - 2018)");
            td.setTitle("Ano");
            td.show();
        });

        return menuBar;
    }

}
