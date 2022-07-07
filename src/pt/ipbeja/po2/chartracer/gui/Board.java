/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.gui;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipbeja.po2.chartracer.model.Model;
import pt.ipbeja.po2.chartracer.model.View;
import pt.ipbeja.po2.chartracer.model.WriteToFile;
import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Board implements View{

    private final WriteToFile writeToFile = new WriteToFile();
    private final int dateY = 900;
    private final int dateX = 1400;
    private final int removeY = 800;
    private final int removeX = 110;


    public Board(Model model) {
        model.setView(this);
    }

    /**
     * @param population Number of population
     * @param group
     * @param position Position where the bar will be drawn
     * @param cityName Name
     * @param barColor Color of the bar
     * @param strokeColor Color of the bar stroke
     * @param date Date
     * Draws and animates the bars, name and date
     */
    @Override
    public void draw(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date) {
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
                                    Bar barNew = new Bar(position, population / 25.0, barColor, strokeColor);
                                    Names city = new Names(cityName, position + 25, 20.0);
                                    Names size = new Names("" + population, position + 40, 15.0);
                                    Bar remove = new Bar(removeY, 600, Color.WHITESMOKE, Color.WHITESMOKE);
                                    remove.setHeight(removeX);
                                    remove.setX(dateX);
                                    Names currentDate = new Names(date, dateY, 100.0);
                                    currentDate.setX(dateX);
                                    group.getChildren().addAll(barNew, city, size, remove, currentDate);
                                } finally{
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

    /**
     * @param group Number of population
     * @param title Title of the chart
     * Draws the chart title
     */
    @Override
    public void drawTitle(Group group, String title) {
        Names titleName = new Names(title, 0, 30.0);
        group.getChildren().add(titleName);
    }

    /**
     * @param group Number of population
     * @param position Position where the bar will be drawn
     * @param population Number of population
     * @param name Name
     * @param barColor Color of the Bar
     * @param strokeColor Color of the Bar Stroke
     * Draws the chart title
     */
    @Override
    public void drawBiggestInSpecificYear(Group group, int position, int population, String name, Color barColor, Color strokeColor) {
        Bar bar = new Bar(position, population / 50.0, barColor, strokeColor);
        Names names = new Names(name, position + 25, 20.0);
        Names size = new Names("" + population, position + 40, 15.0);
        group.getChildren().addAll(bar, names, size);
    }

    /**
     * @param group Number of population
     * @param position Position where the bar will be drawn
     * @param population Number of population
     * @param barColor Color of the Bar
     * @param strokeColor Color of the Bar Stroke
     * Draws the chart title
     */
    @Override
    public void drawSpecificCityBar(Group group, int position, int population, Color barColor, Color strokeColor) {
        Bar remove = new Bar(position, 1500, Color.WHITESMOKE, Color.WHITESMOKE);
        remove.setHeight(800);
        Bar bar = new Bar(position, population / 50.0, barColor, strokeColor);
        Names cityName = new Names("" + population, position + 30, 20.0);
        cityName.setX((population / 50.0) + 10);
        group.getChildren().addAll(remove, cityName, bar);
    }

    /**
     * @param stage Number of population
     * @param datasetData Title of the chart
     * Draws the chart title
     */
    @Override
    public void generateFile(Stage stage, String[] datasetData) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("..\\17646_FabioGoncalves_20481_JoaoPortelinha_TP_PO2_2021-2022\\src\\pt\\ipbeja\\po2\\chartracer\\results"));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        chooser.setInitialFileName("data_stats" + ".txt");
        File file = chooser.showSaveDialog(stage);
        Path path2 = file.toPath();
        writeToFile.write(path2, List.of(datasetData));
    }




}
