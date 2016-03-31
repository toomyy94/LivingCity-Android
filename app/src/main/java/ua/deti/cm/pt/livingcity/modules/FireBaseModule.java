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

        Map<String, Map<String,String>> mapaCompleto = new HashMap<>();
        mapaCompleto.put(currenthora, map);

        usersRef.setValue(mapaCompleto);
    }




    public ArrayList<String> getFirebaseData(String currentDateandTime){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com");

        Firebase usersRef = mRef.child(currentDateandTime);
        Map<String, Map<String,String>> mapaCompleto = new HashMap<>();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Map<String,String>> mapaCompleto = dataSnapshot.getValue(Map.class);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayList<String> fbDataInHour = new ArrayList<>();

        for ( String key : mapaCompleto.keySet() ) {
            String hora = ((HashMap<String, String>)mapaCompleto.get(key)).get("Hora").toString();
            String temp = ((HashMap<String, String>)mapaCompleto.get(key)).get("Temperature").toString();
            String humi = ((HashMap<String, String>)mapaCompleto.get(key)).get("Humidade").toString();
            fbDataInHour.add(hora);
            fbDataInHour.add(temp);
            fbDataInHour.add(humi);
        }

        return fbDataInHour;
    }


}
