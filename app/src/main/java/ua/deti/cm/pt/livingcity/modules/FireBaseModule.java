package ua.deti.cm.pt.livingcity.modules;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roliveira on 29-03-2016.
 */
public class FireBaseModule {

    private Firebase mRef;

    public FireBaseModule(){

    }

    public void addValuesFireBase(int temperature, int humidade, double latitude, double longitude, String hora){
        mRef = new Firebase("https://livingcityapp.firebaseio.com");

        Firebase usersRef = mRef.child(hora);

        Map<String, String> map = new HashMap<>();
        map.put("Temperature", Integer.toString(temperature));
        map.put("Humidade", Integer.toString(humidade));
        map.put("Latitude", Double.toString(latitude));
        map.put("Longitude", Double.toString(longitude));

        usersRef.setValue(map);
    }




    private void getFirebaseData(String hora){
        //dummy values
        mRef = new Firebase("https://livingcityapp.firebaseio.com");

        Firebase usersRef = mRef.child("database");

        //descomentar para por numa text view
        // fireData = (TextView) findViewById(R.id.firedata);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String tmphora = map.get("Hora");
                String tmphumidade = map.get("Humidade");
                //...
                //descomentar para por numa text view
                // fireData.setText(tmphora);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
