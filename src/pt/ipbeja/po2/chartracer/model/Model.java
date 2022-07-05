/**
 * Fábio Gonçalves nº17646
 * João Portelinha nº20481
 **/

package pt.ipbeja.po2.chartracer.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pt.ipbeja.po2.chartracer.gui.*;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.CountDownLatch;


public class Model implements Comparable<Integer>{


    private View view;
    private Color barColor = Color.RED;
    private int count = 0;
    private Names names;
    private Integer positionY = 50;
    private final int reset = 50;
    private final int numOfObjs = 12;
    private final ReadFile readFile = new ReadFile();
    public int pop = 0;
    private Label dateYear;


    /**
     * @param group
     * @param path Path of the chosen dataset
     * @param barColor Color of the fill for Bar
     * @param strokeColor Color of the stroke for Bar
     */
    public void chartRace(Group group, String path, Color barColor, Color strokeColor){
        positionY = reset;
        Names title = new Names("The biggest", 0, 30.0);
        group.getChildren().add(title);
        String[][] inputFile = readFile.readFileToStringArray2D(path, ",");
        String[][] data = new String[numOfObjs][2];//TODO

        List<Bar> rectArray = new ArrayList<>();
        List<Names> textArray = new ArrayList<>();


        this.dateYear = new Label("0");
        group.getChildren().addAll(dateYear);

        for (int i = 0; i < numOfObjs; i++) {
            Bar bar = new Bar(positionY, 0, barColor, strokeColor);
            names = new Names(data[i][0], positionY + 30, 20.0);
            textArray.add(i, names);
            rectArray.add(i, bar);
            view.drawRect(group,names);
            //group.getChildren().addAll(names);
            positionY += 70;

            data[i][0] = "";
            data[i][1] = "0";
        }

        for (String[] strings : inputFile) {
            if (strings.length > 2) {
                City city = new City(strings[1], Integer.parseInt(strings[3]) / 100);
                String date = strings[0];

                List<String> cityList = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    cityList.add(data[i][0]);
                }


                double smallest = rectArray.get(0).getWidth();

                int smallestPos = 0;
                int duplicate = 0;

                Arrays.sort(data, new Comparator<String[]>() {
                    @Override
                    public int compare(String[] array1, String[] array2) {
                        Integer i1 = Integer.parseInt(array1[1]);
                        Integer i2 = Integer.parseInt(array2[1]);

                        return i2.compareTo(i1);
                    }
                });
                System.out.println(Arrays.deepToString(data));

                for (int i = 0; i < data.length; i++) {
                    if (data[i][0].equals("")){
                        break;
                    }
                    if (data[i][0].equals(city.getCityName())){
                        duplicate = i;
                    }
                }

                for (int j = 0; j < numOfObjs; j++) {
                    if (smallest > rectArray.get(j).getWidth() && !cityList.contains(city.getCityName())) {
                        smallest = rectArray.get(j).getWidth();
                        smallestPos = j;
                    }
                    else if(cityList.contains(city.getCityName())){
                        smallestPos = duplicate;
                        break;
                    }
                }
                City city2 = new City(data[smallestPos][0], Integer.parseInt(data[smallestPos][1]));
                int result = city.compareTo(city2);

                if (result > 0) {

                    double position = rectArray.get(smallestPos).getY();

                    rectArray.get(smallestPos).setWidth(city.getPopulation());
                    textArray.get(smallestPos).setText(city.getCityName());
                    textArray.get(smallestPos).setX(rectArray.get(smallestPos).getWidth());

                    data[smallestPos][0] = city.getCityName();
                    data[smallestPos][1]  = String.valueOf(city.getPopulation());

                    view.draw(city.getPopulation(), group, position, city.getCityName(), strokeColor);
                    //model.sleep(city.getPopulation(), group, position, city.getCityName(), date, dateYear, strokeColor);
                }
            }
        }
        System.out.println("Cities: " + Arrays.deepToString(data));
    }


    /**
     * @param population
     * @param group
     * @param position
     * @param name
     * read all lines to one array of arrays of Strings
     * Source: Projeto de IP 2020-2021
     */
    public void sleep(int population, Group group, Double position, String name, String date, Label dateYear, Color strokeColor){

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
                                    view.write("asd");
                                    //Text text = new Text();
                                    //text.setText(name);
                                    //text.setX(barNew.getWidth());
                                    //System.out.println(text.getText());
                                    dateYear.setText(date + "");
                                    group.getChildren().addAll(barNew);
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

        /*Thread t = new Thread( () ->  {
                Platform.runLater( () ->
                        {
                            Bar barNew = new Bar(position, population,barColor, strokeColor);
                            //Text text = new Text();
                            //text.setText(name);
                            //text.setX(barNew.getWidth());
                            //System.out.println(text.getText());
                            dateYear.setText(date + "");
                            group.getChildren().addAll(barNew);
                        }
                );

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        t.start();*/

    }



    @Override
    public int compareTo(Integer pop) {
        if (this.pop > pop) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setView(View view) {
        this.view = view;
    }


}
