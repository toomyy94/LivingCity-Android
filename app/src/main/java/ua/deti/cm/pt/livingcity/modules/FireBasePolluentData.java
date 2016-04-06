package ua.deti.cm.pt.livingcity.modules;

/**
 * Created by tomas on 06-04-2016.
 */
public class FireBasePolluentData {

    private int month;

    private int hour;

    private int year;

    private String station_code;

    private int day;

    private double value;

    private String pollutant_code;


    public FireBasePolluentData(int month, int hour, int year, String station_code, int day, double value, String pollutant_code) {

        this.month = month;

        this.hour = hour;

        this.year = year;

        this.station_code = station_code;

        this.day = day;

        this.value = value;

        this.pollutant_code = pollutant_code;

    }


    public int getMonth() {

        return month;

    }

    public void setMonth(int month) {

        this.month = month;

    }

    public int getHour() {

        return hour;

    }

    public void setHour(int hour) {

        this.hour = hour;

    }

    public int getYear() {

        return year;

    }

    public void setYear(int year) {

        this.year = year;

    }

    public String getStation_code() {

        return station_code;

    }

    public void setStation_code(String station_code) {

        this.station_code = station_code;

    }


    public int getDay() {

        return day;

    }

    public void setDay(int day) {

        this.day = day;

    }

    public double getValue() {

        return value;

    }

    public void setValue(double value) {

        this.value = value;

    }

   public String getPollutant_code() {

        return pollutant_code;

    }

    public void setPollutastringcode(String pollutant_code) {

        this.pollutant_code = pollutant_code;

    }
}
