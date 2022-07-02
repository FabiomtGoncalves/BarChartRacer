package pt.ipbeja.po2.chartracer.gui;

public class City implements Comparable<City>{
    private int year;
    private String city;
    private String country;
    private int population;
    private String continent;

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public int compareTo(City otherCity) {
        return Integer.compare(getPopulation(), otherCity.getPopulation());
    }
}
