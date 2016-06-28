package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.firebase.client.Firebase;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ProgressDialog;
import android.os.AsyncTask;


import ua.deti.cm.pt.livingcity.modules.FireBaseSensorData;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.ItemTuristic;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Abril 2016
 */

@SuppressLint("ValidFragment")
public class SensorTouristicFragment extends Fragment  implements OnMapReadyCallback {

    //On Map View
    private List<Circle> mCircle = new ArrayList<>();
    private CheckBox chkCity;
    private List<Marker> sensor_markers = new ArrayList<>(); //sensor(amarelos)
    private List<Marker> city_markers = new ArrayList<>(); //turistic(vermelhos)
    private GoogleMap mMap;
    private LocationCoord gps;


    //On Firebase Get's
    private static List<FireBaseSensorData> fbDataInHour = null;
    private List<ItemTuristic> lstItem;

    public SensorTouristicFragment(LocationCoord gps, List<ItemTuristic> lstItem) {
        this.gps = gps;
        this.lstItem = lstItem;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemClock.sleep(1000);
        FireBaseModule fbase = new FireBaseModule();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        fbDataInHour = fbase.getFirebaseData_CurrentDay(currentDateandTime);
        SystemClock.sleep(2000);
        chkCity = (CheckBox) getActivity().findViewById(R.id.chkCity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        CheckBox chkCity = (CheckBox) v.findViewById(R.id.chkCity);
        chkCity.setChecked(true);
        chkCity.setOnCheckedChangeListener(myCheckboxListener);

        CheckBox chkSensor = (CheckBox) v.findViewById(R.id.chkSensor);
        chkSensor.setChecked(true);
        chkSensor.setOnCheckedChangeListener(myCheckboxListener);

        return v;
    }

    private CompoundButton.OnCheckedChangeListener myCheckboxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.chkCity:
                    if (isChecked == false) {

                        for (int i = 0; i < city_markers.size(); i++) {
                            city_markers.get(i).setVisible(false);
                        }

                    } else {
                        for (int i = 0; i < city_markers.size(); i++){
                            city_markers.get(i).setVisible(true);
                        }

                    }
                    break;
                case R.id.chkSensor:
                    if (isChecked == false) {
                        for (int i = 0; i < sensor_markers.size(); i++) {
                            sensor_markers.get(i).setVisible(false);
                            mCircle.get(i).setVisible(false);
                        }

                    } else {
                        for (int i = 0; i < sensor_markers.size(); i++){
                            sensor_markers.get(i).setVisible(true);
                            mCircle.get(i).setVisible(true);
                        }

                    }
                    break;
            }
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.i("ola:", fbDataInHour.toString());
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //firebase Sensor & Circles
        for(int i =0; i<fbDataInHour.size(); i++) {
            if(fbDataInHour.size()==0 || fbDataInHour==null)  SystemClock.sleep(600);
            else{
                //markers
                sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(
                        new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
                        title((fbDataInHour.get(i).getHora().substring(0, 5)+"h: ") + "Temperature - " + fbDataInHour.get(i).getTemperature() + "/ Humidity - " + fbDataInHour.get(i).getHumidade()).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));

                //circles around markers
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<9) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.azulbebe).strokeColor(R.color.azulbebe).strokeWidth(8)));
                }
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=9 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<13) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.laranja).strokeColor(R.color.laranja).strokeWidth(6)));

                }
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=13 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<18) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.amarelo).strokeColor(R.color.amarelo).strokeWidth(6)));

                }
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=18 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<23) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.azul).strokeColor(R.color.azul).strokeWidth(6)));

                }
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=23 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<28) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.laranjaescuro).strokeColor(R.color.laranjaescuro).strokeWidth(6)));
                }
                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=28) {
                    mCircle.add(mMap.addCircle(new CircleOptions().center(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(R.color.vermelho).strokeColor(R.color.vermelho).strokeWidth(6)));
                }
            }
        }

        //xml to tourist attractions
        for(int i =0; i<lstItem.size(); i++) {
            city_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lstItem.get(i).getLatitude(), lstItem.get(i).getLongitude())).
                    title(lstItem.get(i).getName()).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_RED))));
            
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 10));

    }

    public static List<FireBaseSensorData> getValueSensorInFireBase(){
        return fbDataInHour;
    }
}
