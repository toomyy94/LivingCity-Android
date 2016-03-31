package ua.deti.cm.pt.livingcity.modules;

/**
 * Created by Tomás on 01/04/2016.
 */
public class FireBaseDataClass {

        private String Hora;
        private String Humidade;
        private String Latitude;
        private String Longitude;
        private String Temperature;


        public FireBaseDataClass() {
            // empty default constructor, necessary for Firebase to be able to deserialize blog posts
        }
        public String getHora() {
            return Hora;
        }
        public String getTemperature() {
            return Temperature;
        }

        public String getHumidade() {
              return Humidade;
        }

        public String getLatitude() {
            return Latitude;
        }

        public String getLongitude() {
            return Longitude;
        }
}

