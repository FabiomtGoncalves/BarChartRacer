package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.ReadTxtFile;

import java.io.File;


public class BarChartRacerStart extends Application {

    private Stage stage;
    private String path;
    private BorderPane borderPane = new BorderPane();
    private Group group = new Group();
    private final int sceneW = 800;
    private final int sceneH = 600;
    private ReadTxtFile readTxtFile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        Board board = new Board();
        //Scene scene = new Scene(board);

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("..\\17646_20481_BarChartRacer\\src\\pt\\ipbeja\\po2\\chartracer"));
        chooser.setTitle("Escolha o Ficheiro");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(primaryStage);
        path = file.getAbsolutePath();
        this.readTxtFile = new ReadTxtFile(path);

        borderPane.setTop(board.createMenu(group, path));
        borderPane.setLeft(group);
        Scene scene = new Scene(borderPane, sceneW, sceneH);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show();

    }


}
