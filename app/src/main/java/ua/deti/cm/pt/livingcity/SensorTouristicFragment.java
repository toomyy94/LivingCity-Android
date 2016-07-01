package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

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


import ua.deti.cm.pt.livingcity.modules.DistrictsModule;
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

    //On Firebase Get's
    private static List<FireBaseSensorData> fbDataInHour = null;
    //On Map View
    private List<Circle> mCircle = new ArrayList<>();
    private CheckBox chkCity;
    private List<Marker> sensor_markers = new ArrayList<>(); //sensor(amarelos)
    private List<Marker> city_markers = new ArrayList<>(); //turistic(vermelhos)
    private List<Polygon> districts = new ArrayList<>(); //distritos(polígonos)
    private GoogleMap mMap;
    private LocationCoord gps;
    private List<ItemTuristic> lstItem;
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



    public SensorTouristicFragment(LocationCoord gps, List<ItemTuristic> lstItem) {
        this.gps = gps;
        this.lstItem = lstItem;
    }

    public static List<FireBaseSensorData> getValueSensorInFireBase(){
        return fbDataInHour;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Log.i("ola:", fbDataInHour.toString());
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // 0- Algarve ; 1- ; 2- ; 3- ; 4- ; 5- ; 6- ; 7- ; 8- ;
        // 9- ; 10- ; 11- ; 12- ; 13- ; 14- ; 15- ; 16- ; 17- ; 18-
        /*districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.57070524233116, -8.800048828125), new LatLng(37.60552821745789, -7.503662109375), new LatLng(37.19533058280065, -7.415771484374999), new LatLng(37.00255267215955, -7.877197265625), new LatLng(37.125286284966805, -8.54736328125), new LatLng(37.046408899699564, -8.96484375), new LatLng(37.57070524233116, -8.800048828125))
                .strokeColor(Color.argb(40, 50, 0, 255)).strokeWidth(6)
                .fillColor(Color.argb(150, 0, 0, 255))));

        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.57070524233116, -8.7890625), new LatLng(37.85750715625203, -8.81103515625), new LatLng(37.97018468810549, -8.28369140625), new LatLng(38.28131307922966, -8.0859375), new LatLng(38.272688535980976, -7.18505859375),  new LatLng(38.1777509666256, -6.954345703125), new LatLng(37.59682400108367, -7.503662109375), new LatLng(37.57070524233116, -8.7890625))
                .strokeColor(Color.argb(40, 50, 0, 255)).strokeWidth(6)
                .fillColor(Color.argb(150, 0, 255, 0))));

        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(38.26406296833961, -7.152099609375), new LatLng(38.46219172306828, -7.31689453125), new LatLng(38.95940879245423, -7.00927734375), new LatLng(38.96795115401593, -8.184814453125), new LatLng(38.65119833229951, -8.470458984375), new LatLng(38.26406296833961, -8.118896484375), new LatLng(38.26406296833961, -7.152099609375))
                .strokeColor(Color.argb(40, 50, 0, 255)).strokeWidth(6)
                .fillColor(Color.argb(150, 255, 0, 0))));

        Log.i("distric",""+districts);*/



        //firebase Sensor & Circles
        boolean lastValue = true;
        //int indice = fbDataInHour.size() - 1;
        for (int i = fbDataInHour.size() - 1; i >= 0; i--) {
            if (fbDataInHour.size() == 0 || fbDataInHour == null) SystemClock.sleep(600);
            else {
                //float distancia = 0;

                if (DistrictsModule.getDistrict().equals(fbDataInHour.get(i).getDistrct()) && lastValue == true){
                    lastValue = false;

                    sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
                            title((fbDataInHour.get(i).getHora().substring(0, 5) + "h: ") + "Temperature: " + fbDataInHour.get(i).getTemperature() ).icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));

                    //circles around markers
                    if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 9) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 200)).strokeColor(Color.argb(90, 0, 0, 200)).strokeWidth(8)));
                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 9 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 13) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 80)).strokeColor(Color.argb(90, 0, 0, 80)).strokeWidth(6)));

                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 13 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 18) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 120, 200, 0)).strokeColor(Color.argb(90, 120, 200, 0)).strokeWidth(6)));

                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 18 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 28) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 220, 0, 0)).strokeColor(Color.argb(90, 220, 0, 0)).strokeWidth(6)));
                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 28) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 40, 0, 0)).strokeColor(Color.argb(90, 40, 0, 0)).strokeWidth(6)));
                    }

                }

                else if (!DistrictsModule.getDistrict().equals(fbDataInHour.get(i).getDistrct())){

                    sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
                            title((fbDataInHour.get(i).getHora().substring(0, 5) + "h: ") + "Temperature: " + fbDataInHour.get(i).getTemperature() ).icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));

                    //circles around markers
                    if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 9) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 200)).strokeColor(Color.argb(90, 0, 0, 200)).strokeWidth(8)));
                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 9 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 13) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 80)).strokeColor(Color.argb(90, 0, 0, 80)).strokeWidth(6)));

                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 13 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 18) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 120, 200, 0)).strokeColor(Color.argb(90, 120, 200, 0)).strokeWidth(6)));

                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 18 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 28) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 220, 0, 0)).strokeColor(Color.argb(90, 220, 0, 0)).strokeWidth(6)));
                    }
                    else if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 28) {
                        mCircle.add(mMap.addCircle(new CircleOptions().center(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 40, 0, 0)).strokeColor(Color.argb(90, 40, 0, 0)).strokeWidth(6)));
                    }

                }






                    //distancia = distFrom(Float.parseFloat(fbDataInHour.get(j).getLatitude()), Float.parseFloat(fbDataInHour.get(j).getLongitude()), Float.parseFloat(fbDataInHour.get(j - 1).getLatitude()), Float.parseFloat(fbDataInHour.get(j - 1).getLongitude()));
                    //Log.e("Distancia a:", Float.toString(distancia) + "->" + fbDataInHour.get(i).getHora());

                /*
                    if ((Double.parseDouble(fbDataInHour.get(indice).getLatitude())-Double.parseDouble(fbDataInHour.get(i).getLatitude())> - 0.3 && Double.parseDouble(fbDataInHour.get(indice).getLatitude())-Double.parseDouble(fbDataInHour.get(i).getLatitude())<0.3) || (Double.parseDouble(fbDataInHour.get(indice).getLongitude())-Double.parseDouble(fbDataInHour.get(i).getLongitude())>0.3 && Double.parseDouble(fbDataInHour.get(indice).getLongitude())-Double.parseDouble(fbDataInHour.get(i).getLongitude())<-0.3))
                    {
                        if(indice==i){

                        }
                        else continue;
                    }
                    else{
                        indice=i;
                        Log.i("indice:", ""+indice);
                        // int i = fbDataInHour.size()-1;
                        sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(
                                new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
                                title((fbDataInHour.get(i).getHora().substring(0, 5) + "h: ") + "Temperature - " + fbDataInHour.get(i).getTemperature()).icon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));

                        //circles around markers
                        if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 9) {
                            mCircle.add(mMap.addCircle(new CircleOptions().center(
                                    new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 200)).strokeColor(Color.argb(90, 0, 0, 200)).strokeWidth(8)));
                        }
                        if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 9 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 13) {
                            mCircle.add(mMap.addCircle(new CircleOptions().center(
                                    new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 0, 0, 80)).strokeColor(Color.argb(90, 0, 0, 80)).strokeWidth(6)));

                        }
                        if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 13 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 18) {
                            mCircle.add(mMap.addCircle(new CircleOptions().center(
                                    new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 120, 200, 0)).strokeColor(Color.argb(90, 120, 200, 0)).strokeWidth(6)));

                        }

                        if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 18 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) < 28) {
                            mCircle.add(mMap.addCircle(new CircleOptions().center(
                                    new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 220, 0, 0)).strokeColor(Color.argb(90, 220, 0, 0)).strokeWidth(6)));
                        }
                        if (Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0, 2)) >= 28) {
                            mCircle.add(mMap.addCircle(new CircleOptions().center(
                                    new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(8000).fillColor(Color.argb(90, 40, 0, 0)).strokeColor(Color.argb(90, 40, 0, 0)).strokeWidth(6)));
                        }
                    }
                    */
                 }
        }

            //xml to tourist attractions
            for (int k = 0; k < lstItem.size(); k++) {
                city_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lstItem.get(k).getLatitude(), lstItem.get(k).getLongitude())).
                        title(lstItem.get(k).getName()).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_RED))));

            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 10));
    }

    public float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }





}
