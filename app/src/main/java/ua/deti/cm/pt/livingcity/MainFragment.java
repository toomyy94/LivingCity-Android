package ua.deti.cm.pt.livingcity;


import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.SupportActionModeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final LatLng AVEIRO = new LatLng(40.640139687483234, -8.652763366699219);

    public MainFragment() {

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

        googleMap.addMarker(new MarkerOptions().position(AVEIRO).title("Aveiro"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AVEIRO, 13));
    }


}
