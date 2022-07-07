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

    @Override
    public void draw(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date, double checkFile) {
        thread(population,group,position,  cityName,  barColor,  strokeColor,  date,  1, checkFile);
    }

    @Override
    public void drawTitle(Group group, String title) {
        Names titleName = new Names(title, 0, 30.0);
        group.getChildren().add(titleName);
    }

    @Override
    public void drawBiggestInSpecificYear(Group group, int position, int population, String name, Color barColor, Color strokeColor, double checkFile) {
        Bar bar = new Bar(position, population / checkFile, barColor, strokeColor);
        Names names = new Names(name, position + 25, 20.0);
        Names size = new Names("" + population, position + 40, 15.0);
        group.getChildren().addAll(bar, names, size);
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
    public void drawSpecificCityBar(Group group, int position, int population, Color barColor, Color strokeColor, double checkFile) {
        Bar remove = new Bar(position, 1500, Color.WHITESMOKE, Color.WHITESMOKE);
        remove.setHeight(800);
        Bar bar = new Bar(position, population / checkFile, barColor, strokeColor);
        Names cityName = new Names("" + population, position + 30, 20.0);
        cityName.setX((population / 50.0) + 10);
        group.getChildren().addAll(remove, cityName, bar);
    }

    @Override
    public void drawFinal(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date, int i, double checkFile) {
        thread(population,group,position,  cityName,  barColor,  strokeColor,  date,  i, checkFile);
    }

    private void thread(int population, Group group, double position, String cityName, Color barColor, Color strokeColor, String date, int i, double checkFile){
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
                                if(i == 0){
                                    group.getChildren().clear();
                                    Names titleName = new Names("The biggest", 0, 30.0);
                                    group.getChildren().add(titleName);
                                }
                                try{
                                    Bar barNew = new Bar(position, population / checkFile, barColor, strokeColor);
                                    Bar removeBar = new Bar(position, 500, Color.WHITESMOKE, strokeColor);
                                    removeBar.setX(barNew.getWidth());
                                    Names city = new Names(cityName, position + 25, 20.0);
                                    city.setX(barNew.getWidth());
                                    Names size = new Names("" + population, position + 40, 15.0);
                                    size.setX(barNew.getWidth());
                                    Bar remove = new Bar(removeY, 600, Color.WHITESMOKE, Color.WHITESMOKE);
                                    remove.setHeight(removeX);
                                    remove.setX(dateX);
                                    Names currentDate = new Names(date, dateY, 100.0);
                                    currentDate.setX(dateX);
                                    group.getChildren().addAll(barNew, removeBar, city, size, remove, currentDate);

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
}
