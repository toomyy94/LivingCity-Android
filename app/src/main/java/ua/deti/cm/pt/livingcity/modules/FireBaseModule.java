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
 * Created by roliveira on 29-03-2016.
 */
public class FireBaseModule {

    private Firebase mRef;

    public FireBaseModule(){

    }

    public void addValuesFireBase(String temperature, String humidade, double latitude, double longitude, String currentDateandTime){
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

        Map<String, Object> mapaCompleto = new HashMap<>();
        mapaCompleto.put(currenthora, map);

        usersRef.updateChildren(mapaCompleto);
    }

    public List<FireBaseSensorData> getFirebaseData_CurrentDay(String currentDateandTime){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/"+currentDateandTime);

        final List<FireBaseSensorData>  fbDataInHour = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                Log.i("O Nº de medições é:", usersSnapshot.getChildrenCount() + "dados");


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    String hora = userSnapshot.child("Hora").getValue(String.class);
                    String temp = userSnapshot.child("Temperature").getValue(String.class);
                    String humi = userSnapshot.child("Humidade").getValue(String.class);
                    String lati = userSnapshot.child("Latitude").getValue(String.class);
                    String longi = userSnapshot.child("Longitude").getValue(String.class);

                    fbDataInHour.add(new FireBaseSensorData(hora, humi, lati, longi, temp));


                }

                Log.e("Firebase Sensors: ", fbDataInHour.toString());
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

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    String ambiente = userSnapshot.child("Ambiente").getValue(String.class);
                    String concelho =userSnapshot.child("Concelho").getValue(String.class);
                    String influ = userSnapshot.child("Influencia").getValue(String.class);
                    Double lati = userSnapshot.child("LAT").getValue(Double.class);
                    Double longi = userSnapshot.child("LON").getValue(Double.class);
                    String nome = userSnapshot.child("Nome actual").getValue(String.class);

                    fbDataInStations.add(new FireBaseStationsData(ambiente, concelho,influ,lati,longi,nome));


                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


        return fbDataInStations;
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
                    Log.i("distrito: ",nome2);

                    for(DataSnapshot geometrySnap : userSnapshot.getChildren()){
                        for(DataSnapshot cordinatesSnap : geometrySnap.getChildren()){
                            for(DataSnapshot cordinatesfirst : cordinatesSnap.getChildren()){
                                for(DataSnapshot cordinatessecond : cordinatesfirst.getChildren()){
                                    //for(DataSnapshot cordinate3 : cordinatessecond.getChildren()) {
                                        if (cordinatessecond.getChildrenCount() == 2) {
                                            lat = cordinatessecond.child("0").getValue(Double.class);
                                            lon = cordinatessecond.child("1").getValue(Double.class);
                                            Log.i("lat for cima: ", lat + "");
                                            fbDataInDistricts.add(new FireBaseDistrictsData(lat, lon, nome2));
                                        } else{
                                            for (DataSnapshot cordinatesthird : cordinatessecond.getChildren()) {
                                                lat = cordinatesthird.child("0").getValue(Double.class);
                                                lon = cordinatesthird.child("1").getValue(Double.class);
                                                Log.i("lat for baixo: ", lat + "");
                                                fbDataInDistricts.add(new FireBaseDistrictsData(lat, lon, nome2));
                                            }
                                        }
                                    //}
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
