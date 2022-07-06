/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class BarChartRacerStart extends Application{

    private Stage stage;
    private String path;
    private final BorderPane borderPane = new BorderPane();
    private final CreateMenu createMenu = new CreateMenu();
    private final Group group = new Group();
    private final Group group2 = new Group();
    private final int sceneW = 800;
    private final int sceneH = 600;


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage
     * Prompt's the user for a choice of the file form the dataset folder, draws and starts the borderPane
     */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        try {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
            chooser.setTitle("Escolha o Ficheiro");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showOpenDialog(primaryStage);
            path = file.getAbsolutePath();

        } catch (Exception e) {
            System.out.println("Não foi selecionado nenhum ficheiro de texto. O programa foi fechado.");
            System.exit(0);
        }

        borderPane.setTop(createMenu.create(group, path, this.stage));
        borderPane.setCenter(group2);
        borderPane.setLeft(group);
        Scene scene = new Scene(borderPane, sceneW, sceneH);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show();
    }
}
