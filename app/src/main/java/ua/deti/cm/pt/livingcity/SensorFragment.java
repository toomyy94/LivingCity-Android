package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.deti.cm.pt.livingcity.modules.FireBaseDistrictsData;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.FireBaseStationsData;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SensorFragment extends Fragment implements OnMapReadyCallback {

    //On Map View
    private List<Circle> mCircle = new ArrayList<>();;
//    private CheckBox chkCity;
//    private List<Marker> sensor_markers = new ArrayList<>(); //sensor(amarelos)
//    private List<Marker> city_markers = new ArrayList<>(); //turistic(vermelhos)
    private List<Marker> stations_markers = new ArrayList<>(); //stations(verdes)

//    private List<ItemTuristic> lstItem = null;
    private GoogleMap mMap;
    private LocationCoord gps;
    private String URL=null;
    private NodeList nodelist;
    private ProgressDialog pDialog;

    //On Firebase Get's
    private List<FireBaseDistrictsData> fbDataInDistricts = null;
    private List<FireBaseStationsData> fbDataInStations = null;
//    //Polluent
//    public ComparationData comparated_data = new ComparationData();
//    List<Pollutant> comparated_data_polluentes = this.comparated_data.getPollutants();


    public SensorFragment(LocationCoord gps, List<FireBaseStationsData> fbDataInStations, List<FireBaseDistrictsData> fbDataInDistricts) {
        this.fbDataInDistricts = fbDataInDistricts;
        this.fbDataInStations = fbDataInStations;
        this.gps = gps;
        URL = "http://www.tixik.com/api/nearby?lat="+gps.getLatitude()+"&lng="+gps.getLongitude()+"&limit=20&key=demo";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_sensor, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

//
//        CheckBox chkStations = (CheckBox) v.findViewById(R.id.chkStations);
//        chkStations.setChecked(true);
//        chkStations.setOnCheckedChangeListener(myCheckboxListener);


        return v;
    }
//
//    private CompoundButton.OnCheckedChangeListener myCheckboxListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            switch (buttonView.getId()){
//
//                case R.id.chkStations:
//                    if (isChecked == false) {
//                        for (int i = 0; i < stations_markers.size(); i++) {
//                            stations_markers.get(i).setVisible(false);
//                        }
//
//                    } else {
//                        for (int i = 0; i < stations_markers.size(); i++){
//                            stations_markers.get(i).setVisible(true);
//                        }
//
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firebase inicial

        //Polluent info:
//        double obsValue;
//        double modValue;
//        double comparacao;
//        int tmpHour;
//
//        for(int i=0;i<comparated_data_polluentes.size();i++){
//            tmpHour = comparated_data_polluentes.get(i).getHour();
//            obsValue = comparated_data_polluentes.get(i).getObserved();
//            modValue = comparated_data_polluentes.get(i).getModulated();
//            comparacao = comparated_data_polluentes.get(i).getCompareData();
//            Log.i("hora dentro do for: ", comparated_data_polluentes.get(i).getHour()+"");
//        }
//
//        Log.i("Dados Poluentes: ", comparated_data_polluentes.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        //firebase

        for(int i =0; i<fbDataInStations.size(); i++) {
            if (fbDataInStations.size() == 0 || fbDataInStations == null) SystemClock.sleep(600);
            else {
                stations_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(fbDataInStations.get(i).getLAT(), fbDataInStations.get(i).getLON())).
                        title((fbDataInStations.get(i).getNome_actual())).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));


            }
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 12));

    }

}
