package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.w3c.dom.NodeList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.deti.cm.pt.livingcity.modules.FireBaseDistrictsData;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.FireBasePolluentData;
import ua.deti.cm.pt.livingcity.modules.FireBaseStationsData;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SensorFragment extends Fragment implements OnMapReadyCallback {

    //On Map View
    private List<Polygon> mPolygon = new ArrayList<>();
    //private List<Circle> mCircle = new ArrayList<>();
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
    private List<FireBasePolluentData> fbDataPolluent = null;
    private List<FireBaseStationsData> fbDataInStations = null;
//    //Polluent
    private List<Circle> pCircle = new ArrayList<>();
//    public ComparationData comparated_data = new ComparationData();
//    List<Pollutant> comparated_data_polluentes = this.comparated_data.getPollutants();


    public SensorFragment(LocationCoord gps, List<FireBaseStationsData> fbDataInStations, List<FireBasePolluentData> fbDataPolluent) {
        this.fbDataPolluent = fbDataPolluent;
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
        Double PM25 = 0.0;
        Double O3 = 0.0;
        Double NO2 = 0.0;
        Double PM10 = 0.0;

        Double mPM25 = 0.0;
        Double mO3 = 0.0;
        Double mNO2 = 0.0;
        Double mPM10 = 0.0;

        Log.i("teste:" ,fbDataPolluent.toString());

        for(int i =0; i<fbDataPolluent.size(); i++) {
            if(fbDataPolluent.get(i).getPollutant_code().equals("PM25")) PM25 += fbDataPolluent.get(i).getValue();
            if(fbDataPolluent.get(i).getPollutant_code().equals("O3")) O3 += fbDataPolluent.get(i).getValue();
            if(fbDataPolluent.get(i).getPollutant_code().equals("NO2")) NO2 += fbDataPolluent.get(i).getValue();
            if(fbDataPolluent.get(i).getPollutant_code().equals("PM10")) PM10 += fbDataPolluent.get(i).getValue();
        }

        mPM25 = PM25/fbDataPolluent.size();
        mO3 = mO3/fbDataPolluent.size();
        mNO2 = mNO2/fbDataPolluent.size();
        mPM10 = PM10/fbDataPolluent.size();

        int qualidade_do_ar=0;

        for(int i =0; i<fbDataInStations.size(); i++) {
            if (fbDataInStations.size() == 0 || fbDataInStations == null) SystemClock.sleep(600);
            else {
                stations_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(fbDataInStations.get(i).getLAT(), fbDataInStations.get(i).getLON())).
                        title((fbDataInStations.get(i).getNome_actual())).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
                if(fbDataPolluent.get(i).getPollutant_code().equals("PM2.5"))
                    if(fbDataPolluent.get(i).getValue()<7500)qualidade_do_ar+=1;
                if(fbDataPolluent.get(i).getPollutant_code().equals("O3"))
                    if(fbDataPolluent.get(i).getValue()<160)qualidade_do_ar+=1;
                if(fbDataPolluent.get(i).getPollutant_code().equals("NO2"))
                    if(fbDataPolluent.get(i).getValue()<180)qualidade_do_ar+=1;
                if(fbDataPolluent.get(i).getPollutant_code().equals("PM10"))
                    if(fbDataPolluent.get(i).getValue()<50)qualidade_do_ar+=1;

                if(qualidade_do_ar>2) pCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(fbDataInStations.get(i).getLAT(), fbDataInStations.get(i).getLON())).radius(10000).fillColor(R.color.colorMenu).strokeColor(R.color.colorPrimaryText).strokeWidth(8)));
                else pCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(fbDataInStations.get(i).getLAT(), fbDataInStations.get(i).getLON())).radius(10000).fillColor(R.color.vermelho).strokeColor(R.color.colorPrimaryText).strokeWidth(8)));

            }
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 10));

    }

}
