/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.ReadFile;
import pt.ipbeja.po2.chartracer.model.View;
import pt.ipbeja.po2.chartracer.model.WriteToFile;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Board implements View{

    private Integer positionY = 50;
    private final ReadFile readFile = new ReadFile();
    private final WriteToFile writeToFile = new WriteToFile();
    private Model model;
    private Color barColor = Color.RED;

    public Board(Model model) {
        this.model = model;
        this.model.setView(this);
    }

    @Override
    public void draw(int population, Group group, double position, String cityName, Color strokeColor, Names textArrays) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Bar barNew = new Bar(position, population,barColor, strokeColor);
                                    Names city = new Names(cityName, position + 25, 20.0);
                                    Names size = new Names("" + population * 75, position + 40, 15.0);
                                    group.getChildren().addAll(barNew, city, size);
                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    @Override
    public void drawTitle(Group group, String title) {
        Names titleName = new Names(title, 0, 30.0);
        group.getChildren().add(titleName);
    }

    @Override
    public void drawBiggestInSpecificYear(Group group, int position, String population, String name, Color barcolor, Color strokeColor) {
        Bar bar = new Bar(position, Integer.parseInt(population) / 50, barColor, strokeColor);
        Names names = new Names(name, position + 35, 20.0);
        Names pop = new Names(population, position + 15, 20.0);
        group.getChildren().addAll(bar, names, pop);
    }


    @Override
    public void generateFile(Stage stage, String[] datasetData) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\datasets"));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        chooser.setInitialFileName("data_stats" + ".txt");
        File file = chooser.showSaveDialog(stage);
        Path path2 = file.toPath();
        writeToFile.write(path2, List.of(datasetData));
    }

    @Override
    public void drawSpecificCityBar(Group group, int position, String barWidth, Color barcolor, Color strokeColor) {
        Bar bar = new Bar(positionY, Double.parseDouble(barWidth) / 50, barColor, strokeColor);
        group.getChildren().addAll(bar);
    }


}
