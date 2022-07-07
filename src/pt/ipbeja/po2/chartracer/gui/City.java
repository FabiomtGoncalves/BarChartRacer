package pt.ipbeja.po2.chartracer.gui;

public class City implements Comparable<City>{
    private String year;
    private String cityName;
    private String country;
    private int population;
    private String continent;

    public City(String cityName, int population) {
        //this.year = year;
        this.cityName = cityName;
        //this.country = country;
        this.population = population;
        //this.continent = continent;
    }

    public String getCityName() {
        return cityName;
    }

    public int getPopulation() {
        return population;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @Override
    public int compareTo(City otherCity) {
        //return Integer.compare(getPopulation(), otherCity.getPopulation());

        if (this.population > otherCity.population) {
            return 1;
        } else if (this.population < otherCity.population) {
            return -1;
        } else {
            return 0;
        }
    }
}
