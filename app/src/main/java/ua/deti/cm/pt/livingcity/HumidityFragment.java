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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import ua.deti.cm.pt.livingcity.modules.FireBasePolluentData;
import ua.deti.cm.pt.livingcity.modules.FireBaseSensorData;
import ua.deti.cm.pt.livingcity.modules.FireBaseStationsData;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;


/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Abril 2016
 */

@SuppressLint("ValidFragment")
public class HumidityFragment extends Fragment implements OnMapReadyCallback {

    //On Map View

    private List<Polygon> districts = new ArrayList<>(); //distritos(poligonos)
    private GoogleMap mMap;
    private LocationCoord gps;




    private  List<FireBaseSensorData> valueSensorInFireBase = null;

    //Polluent
    private List<Circle> pCircle = new ArrayList<>();

    public HumidityFragment(LocationCoord gps) {
        this.gps = gps;
        valueSensorInFireBase = SensorTouristicFragment.getValueSensorInFireBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_humidity, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        //0- Faro
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.57070524233116, -8.800048828125),
                        new LatLng(37.60552821745789, -7.503662109375),
                        new LatLng(37.19533058280065, -7.415771484374999),
                        new LatLng(37.00255267215955, -7.877197265625),
                        new LatLng(37.125286284966805, -8.54736328125),
                        new LatLng(37.046408899699564, -8.96484375),
                        new LatLng(37.57070524233116, -8.800048828125))));

        // 1- Beja
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.57070524233116, -8.7890625),
                        new LatLng(37.85750715625203, -8.81103515625),
                        new LatLng(37.97018468810549, -8.28369140625),
                        new LatLng(38.28131307922966, -8.0859375),
                        new LatLng(38.272688535980976, -7.18505859375),
                        new LatLng(38.1777509666256, -6.954345703125),
                        new LatLng(37.59682400108367, -7.503662109375),
                        new LatLng(37.57070524233116, -8.7890625)
                )));



        // 2- Évora;
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(38.26406296833961,-7.152099609375 ),
                        new LatLng(38.46219172306828, -7.31689453125),
                        new LatLng(38.95940879245423, -7.00927734375),
                        new LatLng(38.96795115401593, -8.184814453125),
                        new LatLng(38.65119833229951, -8.470458984375),
                        new LatLng(38.26406296833961,-8.118896484375),
                        new LatLng(38.26406296833961,-7.152099609375)
                )));

        //3- Setubal;
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.86618078529668,-8.800048828125 ),
                        new LatLng(38.53957267203905,-8.778076171875 ),
                        new LatLng(38.436379603,-9.195556640625 ),
                        new LatLng(38.57393751557591,-9.217529296875 ),
                        new LatLng(38.96795115401593,-8.19580078125 ),
                        new LatLng(38.659777730712534, -8.4375),
                        new LatLng(38.28131307922966, -8.10791015625),
                        new LatLng(37.97884504049713, -8.272705078125),
                        new LatLng(37.86618078529668, -8.800048828125)
                )));



        //4
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(39.33429742980725,-9.349365234375 ),
                        new LatLng(39.35129035526705, -8.85498046875),
                        new LatLng(39.11301365149975, -8.63525390625),
                        new LatLng(38.736946065676, -8.876953125),
                        new LatLng(38.59970036588819, -9.20654296875),
                        new LatLng(38.762650338334154, -9.4921875),
                        new LatLng(39.33429742980725,-9.349365234375)
                )));



        //5
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(38.976492485539396, -6.998291015625),
                        new LatLng(39.65645604812829,-7.5146484375 ),
                        new LatLng(39.29179704377487,-8.140869140625 ),
                        new LatLng(38.95940879245423, -8.173828125),
                        new LatLng(38.976492485539396, -6.998291015625)
                )));



        //6
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(39.52946653645165, -7.75634765625),
                        new LatLng(39.816975090490004, -8.15185546875),
                        new LatLng(39.7240885773337 , -8.536376953125),
                        new LatLng(39.35978526869001, -8.81103515625 ),
                        new LatLng(39.11301365149975 , -8.646240234375),
                        new LatLng(38.71980474264237, -8.887939453125 ),
                        new LatLng( 38.96795115401593, -8.19580078125),
                        new LatLng(39.308800296002914, -8.162841796875 ),
                        new LatLng( 39.52946653645165, -7.75634765625)
                )));



        //7
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng( 39.816975090490004, -8.15185546875),
                        new LatLng(40.32141999593439 , -7.635498046875),
                        new LatLng(40.27952566881291, -6.8994140625 ),
                        new LatLng(39.68182601089365, -6.998291015625 ),
                        new LatLng( 39.66491373749131, -7.5146484375),
                        new LatLng(39.53793974517625, -7.75634765625 ),
                        new LatLng(39.816975090490004 , -8.15185546875)
                )));


        //8
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(39.96870074491693,-7.998046875 ),
                        new LatLng(39.9602803542957,-8.942871093749998 ),
                        new LatLng(39.36827914916014,-9.371337890625 ),
                        new LatLng(39.36827914916014, -8.85498046875),
                        new LatLng(39.715638134796336,-8.5693359375 ),
                        new LatLng(39.93501296038254,-8.031005859375 ),
                        new LatLng(39.96870074491693, -7.998046875)
                )));



        //9
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(40.287906612507406, -6.888427734375),
                        new LatLng(41.00477542222947,-6.888427734375 ),
                        new LatLng(41.02964338716638, -7.393798828125),
                        new LatLng(40.472024396920546, -7.987060546875),
                        new LatLng(40.204050425113294,-7.778320312499999 ),
                        new LatLng(40.329795743702064, -7.657470703124999),
                        new LatLng(40.287906612507406,-6.888427734375 )
                )));



        //10
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(40.463666324587685, -7.976074218749999),
                        new LatLng(40.463666324587685, -8.778076171875),
                        new LatLng(39.977120098439634, -8.931884765625),
                        new LatLng(39.977120098439634,-7.998046875 ),
                        new LatLng(40.204050425113294, -7.789306640625),
                        new LatLng(40.463666324587685, -7.976074218749999)
                )));



        //11
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(40.48038142908172,-8.426513671875),
                        new LatLng(41.08763212467916, -8.173828125),
                        new LatLng(41.00477542222947,-8.63525390625 ),
                        new LatLng(40.48873742102282,-8.756103515625 ),
                        new LatLng(40.48038142908172,-8.426513671875 )
                )));


        //12
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.08763212467916, -8.162841796875),
                        new LatLng(41.253032440653186, -7.393798828125),
                        new LatLng(41.03793062246529, -7.371826171874999),
                        new LatLng(40.472024396920546, -8.009033203125),
                        new LatLng(40.472024396920546, -8.404541015625),
                        new LatLng(41.08763212467916, -8.162841796875)
                )));



        //13
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.22824901518529, -7.4267578125),
                        new LatLng(41.934976500546604,-7.18505859375 ),
                        new LatLng(41.934976500546604,-6.56982421875 ),
                        new LatLng(41.590796851056005,-6.240234374999999 ),
                        new LatLng(41.0130657870063, -6.8994140625),
                        new LatLng(41.00584631124417, -7.340240478515625),
                        new LatLng(41.22824901518529, -7.4267578125)
                )));


        //14
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.64007838467894, -8.7451171875),
                        new LatLng(41.83682786072714,-8.1298828125 ),
                        new LatLng(42.114523952464246, -8.23974609375),
                        new LatLng(41.86956082699455, -8.9208984375),
                        new LatLng(41.64007838467894,-8.7451171875 )
                )));



        //15

        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.32732632036622, -8.7451171875),
                        new LatLng(41.409775832009565, -8.06396484375),
                        new LatLng(41.1290213474951, -7.998046875),
                        new LatLng(40.9964840143779, -8.6572265625),
                        new LatLng(41.32732632036622, -8.7451171875)
                )));


        //16
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.409775832009565, -8.06396484375),
                        new LatLng(41.82045509614034, -8.15185546875),
                        new LatLng(41.65649719441145, -8.76708984375),
                        new LatLng(41.32732632036622, -8.72314453125),
                        new LatLng(41.409775832009565, -8.06396484375)
                )));



        //17
        districts.add(googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(41.82045509614034, -8.10791015625),
                        new LatLng(41.88592102814744, -7.207031249999999),
                        new LatLng(41.22824901518529, -7.448730468749999),
                        new LatLng(41.1455697310095, -7.976074218749999),
                        new LatLng(41.82045509614034, -8.10791015625)
                )));

        for (int i =0; i<districts.size(); i++){
            districts.get(i).setFillColor(R.color.blackTransparent);
            districts.get(i).setStrokeWidth(4);
            districts.get(i).setStrokeColor(R.color.blackTransparent);
        }




        int color=0;
        boolean aveiro= true, porto = true, santarem = true; //, beja = true, evora = true, setubal = true, portalegre = true, santarem =  ;
        for (int i = valueSensorInFireBase.size() - 1; i >= 0; i--) {

            if (valueSensorInFireBase.get(i).getDistrct().equals("Aveiro District, Portugal") && aveiro == true){

                int humd = (int)Double.parseDouble(valueSensorInFireBase.get(i).getHumidade().split("%")[0]);

                Log.e("fsdfsd", humd +"");


                if (humd<=20)
                    color = R.color.humidity0;
                else if (humd >=21 && humd <=40)
                    color = R.color.humidity21;
                else if (humd >=41 && humd <=60)
                    color = R.color.humidity41;
                else if (humd >=61 && humd <=80)
                    color = R.color.humidity61;
                else if (humd >=81)
                    color = R.color.humidity81;

                districts.get(11).setFillColor(color);
                districts.get(11).setStrokeWidth(4);
                districts.get(11).setStrokeColor(color);

                aveiro = false;
            }

            else if (valueSensorInFireBase.get(i).getDistrct().equals("Porto District") && porto == true){

                int humd = (int)Double.parseDouble(valueSensorInFireBase.get(i).getHumidade().split("%")[0]);

                Log.e("fsdfsd", humd +"");

                if (humd<=20){
                    color = R.color.humidity0;
                    Log.e("kkk","porto20");
                }
                else if (humd >=21 && humd <=40){
                    color = R.color.humidity21;
                    Log.e("kkk","porto40");
                }

                else if (humd >=41 && humd <=60){
                    color = R.color.humidity41;
                    Log.e("kkk","porto60");
                }

                else if (humd >=61 && humd <=80){
                    color = R.color.humidity61;
                    Log.e("kkk","porto80");
                }

                else if (humd >=81){
                    color = R.color.humidity81;
                    Log.e("kkk","porto100");
                }


                districts.get(15).setFillColor(color);
                districts.get(15).setStrokeWidth(4);
                districts.get(15).setStrokeColor(color);

                porto = false;
            }

            else if (valueSensorInFireBase.get(i).getDistrct().equals("Santarém District") && santarem == true){

                int humd = (int)Double.parseDouble(valueSensorInFireBase.get(i).getHumidade().split("%")[0]);

                Log.e("fsdfsd", humd +"");

                if (humd<=20)
                    color = R.color.humidity0;
                else if (humd >=21 && humd <=40)
                    color = R.color.humidity21;
                else if (humd >=41 && humd <=60)
                    color = R.color.humidity41;
                else if (humd >=61 && humd <=80)
                    color = R.color.humidity61;
                else if (humd >=81)
                    color = R.color.humidity81;

                districts.get(6).setFillColor(color);
                districts.get(6).setStrokeWidth(4);
                districts.get(6).setStrokeColor(color);

                santarem = false;
            }

        }

        //









        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 6));

    }

}
