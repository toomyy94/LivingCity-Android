package ua.deti.cm.pt.livingcity.modules;

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * Abril 2016
 */
public class FireBaseDistrictsData {

    private Double LAT;
    private Double LON;
    private String Distrito;

    public FireBaseDistrictsData(Double LAT, Double LON, String Distrito){
        this.LAT = LAT;
        this.LON = LON;
        this.Distrito = Distrito;
    }

    public String getDistrito() { return Distrito;}
    public Double getLAT() { return LAT;  }
    public Double getLON() { return LON;}
    public String toString() { return "Distritos{Nome='"+Distrito+','+ LAT+": LAT, "+ LON+": LON";}
}
