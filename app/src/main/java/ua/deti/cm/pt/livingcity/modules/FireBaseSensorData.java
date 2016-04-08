package ua.deti.cm.pt.livingcity.modules;

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Abril 2016
 */
public class FireBaseSensorData {

        private String hora;
        private String humidade;
        private String latitude;
        private String longitude;
        private String temperature;

        public FireBaseSensorData(String hora, String humidade, String latitude, String longitude, String temperature){
            this.hora = hora;
            this.humidade = humidade;
            this.latitude = latitude;
            this.longitude = longitude;
            this.temperature = temperature;
        }

        public String getHora() {
            return hora;
        }
        public String getTemperature() {
            return temperature;
        }
        public String getHumidade() {
              return humidade;
        }
        public String getLatitude() {
            return latitude;
        }
        public String getLongitude() { return longitude;}
        public String toString() { return hora+"{Hora='"+hora+','+ humidade+": Humidade, "+ temperature+": Temperature";}

}

