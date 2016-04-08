package ua.deti.cm.pt.livingcity.modules;

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Abril 2016
 */

public class FireBaseStationsData {

        private String code;
        private String Ambiente;
        private String Conselho;
        private String Influencia;
        private Double LAT;
        private Double LON;
        private String Nome_actual;

        public FireBaseStationsData(String code, String Ambiente, String Conselho, String Influencia, Double LAT, Double LON, String Nome_actual){
            this.code = code;
            this.Ambiente = Ambiente;
            this.Conselho = Conselho;
            this.Influencia = Influencia;
            this.LAT = LAT;
            this.LON = LON;
            this.Nome_actual = Nome_actual;
        }

        public String getCode() {return code;}
        public String getAmbiente() {return Ambiente;}
        public String getConselho() {
            return Conselho;
        }
        public String getInfluencia() {
        return Influencia;
    }
        public String getNome_actual() { return Nome_actual;}
        public Double getLAT() { return LAT;  }
        public Double getLON() { return LON;}
        public String toString() { return "STATION{Nome_actual='"+Nome_actual+','+ LAT+": LAT, "+ LON+": LON";}

}