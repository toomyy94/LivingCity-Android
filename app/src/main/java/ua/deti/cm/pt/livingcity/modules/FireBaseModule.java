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

    public List<FireBaseDataClass> getFirebaseData_CurrentDay(String currentDateandTime){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com/"+currentDateandTime);

        final HashMap<String, Object> mapaCompleto = new HashMap<>();
        final List<FireBaseDataClass>  fbDataInHour = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange (DataSnapshot usersSnapshot){

                    Log.i("O NUMERO DE DADOS SAO::",usersSnapshot.getChildrenCount()+"dados");


                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        String hora = userSnapshot.child("Hora").getValue(String.class);
                        String temp =userSnapshot.child("Temperature").getValue(String.class);
                        String humi =userSnapshot.child("Humidade").getValue(String.class);
                        String lati =userSnapshot.child("Latitude").getValue(String.class);
                        String longi =userSnapshot.child("Longitude").getValue(String.class);

                        fbDataInHour.add(new FireBaseDataClass(hora, humi,lati,longi,temp));


                    }

                    Log.e("CRLHTomas", fbDataInHour.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) { }
            });

//
//        for ( String key : mapaCompleto.keySet() ) {
//            String hora = ((HashMap<String, String>)mapaCompleto.get(key)).get("Hora").toString();
//            String temp = ((HashMap<String, String>)mapaCompleto.get(key)).get("Temperature").toString();
//            String humi = ((HashMap<String, String>)mapaCompleto.get(key)).get("Humidade").toString();
//            fbDataInHour.add(hora);
//            fbDataInHour.add(temp);
//            fbDataInHour.add(humi);
//        }
//
//        for(int i=0;i<fbDataInHour.size();i++) {
//            Log.i("Valor da hora é:", fbDataInHour.get(i).toString());
//        }
        return fbDataInHour;
    }

}
