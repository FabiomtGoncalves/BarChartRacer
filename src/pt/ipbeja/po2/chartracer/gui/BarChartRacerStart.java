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
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.View;
import java.io.File;

public class BarChartRacerStart extends Application{

    private Stage stage;
    private String path;
    private final BorderPane borderPane = new BorderPane();
    private final Group group = new Group();
    private final int sceneW = 800;
    private final int sceneH = 600;
    private Model readTxtFile;
    private View view;

    public void setView(View view) {
        this.view = view;
    }

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
        Board board = new Board();
        this.readTxtFile = new Model(board, this);

        try {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
            chooser.setTitle("Escolha o Ficheiro");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showOpenDialog(primaryStage);
            path = file.getAbsolutePath();
            view.path(path);

        } catch (Exception e) {
            System.out.println("Não foi selecionado nenhum ficheiro de texto. O programa foi fechado.");
            System.exit(0);
        }

        borderPane.setTop(view.createMenu(group, path, this.stage));
        borderPane.setLeft(group);
        Scene scene = new Scene(borderPane, sceneW, sceneH);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show();

    }

}
