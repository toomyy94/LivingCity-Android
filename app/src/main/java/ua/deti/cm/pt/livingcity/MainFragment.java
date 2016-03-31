package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ua.deti.cm.pt.livingcity.modules.FireBaseDataClass;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment  implements OnMapReadyCallback {


    private GoogleMap mMap;
    private LocationCoord gps;

    public MainFragment(LocationCoord gps) {
        this.gps = gps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        // O Q É ISTO?
        //debug
       // Toast toast = Toast.makeText(getActivity(), "latitude:" + getLocationName(gps.getLatitude(), gps.getLongitude()), Toast.LENGTH_SHORT);
       // toast.show();

        FireBaseModule fb = new FireBaseModule();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        ArrayList<String> fbDataInHour = new ArrayList<>();

        //fbDataInHour = fb.getFirebaseData(currentDateandTime);


        googleMap.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).title("Aveiro").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).title("Hora:"+fbDataInHour.get(0)+"/n"+"Temp:"+fbDataInHour.get(1)+"/n"+"Humidade:"+fbDataInHour.get(2)+"/n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 12));

    }





}
