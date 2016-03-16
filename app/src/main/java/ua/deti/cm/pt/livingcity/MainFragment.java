package ua.deti.cm.pt.livingcity;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationCoord gps;
    private static final LatLng AVEIRO = new LatLng(40.640139687483234, -8.652763366699219);

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





        //debug
       // Toast toast = Toast.makeText(getActivity(), "latitude:" + getLocationName(gps.getLatitude(), gps.getLongitude()), Toast.LENGTH_SHORT);
       // toast.show();


        googleMap.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).title("Aveiro").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 12));
    }





}
