package ua.deti.cm.pt.livingcity.modules;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Abril 2016
 */
public class FireBaseModule {

    private Firebase mRef;

    public FireBaseModule(){

    }

    public void addValuesFireBase(String temperature, String humidade, double latitude, double longitude, String currentDateandTime, String district){
        mRef = new Firebase("https://livingcityapp.firebaseio.com");

        Firebase usersRef = mRef.child(currentDateandTime);

        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        String currenthora = hora.format(new Date());

        Map<String, String> map = new HashMap<>();
        map.put("Temperature",temperature);
        map.put("Humidade", humidade);
        map.put("Hora",currenthora);
        map.put("Latitude", Double.toString(latitude));
        map.put("Longitude", Double.toString(longitude));
        map.put("Distrito", district);


        Map<String, Object> mapaCompleto = new HashMap<>();
        mapaCompleto.put(currenthora, map);

        usersRef.updateChildren(mapaCompleto);
    }

    public List<FireBaseSensorData> getFirebaseData_CurrentDay(String currentDateandTime){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/"+currentDateandTime);

        final List<FireBaseSensorData> fbDataInHour = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    String hora = userSnapshot.child("Hora").getValue(String.class);
                    String temp = userSnapshot.child("Temperature").getValue(String.class);
                    String humi = userSnapshot.child("Humidade").getValue(String.class);
                    String lati = userSnapshot.child("Latitude").getValue(String.class);
                    String longi = userSnapshot.child("Longitude").getValue(String.class);
                    String distr = userSnapshot.child("Distrito").getValue(String.class);

                    fbDataInHour.add(new FireBaseSensorData(hora, humi, lati, longi, temp, distr));

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        return fbDataInHour;
    }

    public List<FireBaseStationsData> getFirebaseStations(){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/Polluent_Stations");

        final List<FireBaseStationsData>  fbDataInStations = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot usersSnapshot){
                String code = usersSnapshot.getKey();

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    String ambiente = userSnapshot.child("Ambiente").getValue(String.class);
                    String concelho =userSnapshot.child("Concelho").getValue(String.class);
                    String influ = userSnapshot.child("Influencia").getValue(String.class);
                    Double lati = userSnapshot.child("LAT").getValue(Double.class);
                    Double longi = userSnapshot.child("LON").getValue(Double.class);
                    String nome = userSnapshot.child("Nome actual").getValue(String.class);

                    fbDataInStations.add(new FireBaseStationsData(code, ambiente, concelho,influ,lati,longi,nome));

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


        return fbDataInStations;
    }

    public List<FireBasePolluentData> getFirebasePolluent(){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/Pollutent_2016-04-04");

        final List<FireBasePolluentData>  fbDataPolluent = new ArrayList<>();


        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot usersSnapshot){

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    Integer dia = userSnapshot.child("day").getValue(Integer.class);
                    Integer mes =userSnapshot.child("month").getValue(Integer.class);
                    Integer ano = userSnapshot.child("year").getValue(Integer.class);
                    Double value = userSnapshot.child("value").getValue(Double.class);
                    Integer hora = userSnapshot.child("hour").getValue(Integer.class);
                    String p_code = userSnapshot.child("pollutant/notation").getValue(String.class);
                    String s_code = userSnapshot.child("station/code").getValue(String.class);


                    fbDataPolluent.add(new FireBasePolluentData(mes, hora,ano,s_code,dia, value, p_code));

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


        return fbDataPolluent;
    }

    public List<FireBaseDistrictsData> getFirebaseDistricts(){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/Portugal_Districts/features");

        final List<FireBaseDistrictsData>  fbDataInDistricts = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot usersSnapshot){
                Double lat=0.0;
                Double lon=0.0;

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    String nome2 = userSnapshot.child("id").getValue(String.class);

                    for(DataSnapshot geometrySnap : userSnapshot.getChildren()){
                        for(DataSnapshot cordinatesSnap : geometrySnap.getChildren()){
                            for(DataSnapshot cordinatesfirst : cordinatesSnap.getChildren()){
                                for(DataSnapshot cordinatessecond : cordinatesfirst.getChildren()){
                                        if (cordinatessecond.getChildrenCount() == 2) {
                                            lat = cordinatessecond.child("0").getValue(Double.class);
                                            lon = cordinatessecond.child("1").getValue(Double.class);

                                            fbDataInDistricts.add(new FireBaseDistrictsData(lat, lon, nome2));
                                        } else{
                                            for (DataSnapshot cordinatesthird : cordinatessecond.getChildren()) {
                                                lat = cordinatesthird.child("0").getValue(Double.class);
                                                lon = cordinatesthird.child("1").getValue(Double.class);

                                                fbDataInDistricts.add(new FireBaseDistrictsData(lat, lon, nome2));
                                            }
                                        }
                                }
                            }
                        }
                    }


                    fbDataInDistricts.add(new FireBaseDistrictsData(lat,lon,nome2));
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


        return fbDataInDistricts;
    }

}
