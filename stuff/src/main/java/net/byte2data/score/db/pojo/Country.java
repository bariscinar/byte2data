package net.byte2data.score.db.pojo;

/**
 * Created by barisci on 15.11.2016.
 */
public class Country {

    private int id;
    private String countryName;

    public Country(){

    }

    public Country(String countryname){
        this.countryName=countryname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryname) {
        this.countryName = countryname;
    }
    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", countryName='" + countryName +
                '}';
    }
}
