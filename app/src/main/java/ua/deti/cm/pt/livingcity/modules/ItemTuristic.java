package ua.deti.cm.pt.livingcity.modules;

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Abril 2016
 */

public class ItemTuristic {
    private String name;
    private double x;
    private double y;

    public ItemTuristic(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return x;
    }

    public double getLongitude() {
        return y;
    }

    @Override
    public String toString() {
        return "Name: "+name + "X: "+x+"Y: "+y;
    }
}
