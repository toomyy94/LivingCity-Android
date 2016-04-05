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

import java.util.ArrayList;
import java.util.List;

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
    //private List<FireBaseSensorData> fbDataInHour = null;
    private List<FireBaseStationsData> fbDataInStations = null;
//    //Polluent
//    public ComparationData comparated_data = new ComparationData();
//    List<Pollutant> comparated_data_polluentes = this.comparated_data.getPollutants();


    public SensorFragment(LocationCoord gps, List<FireBaseStationsData> fbDataInStations) {
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

//        CheckBox chkCity = (CheckBox) v.findViewById(R.id.chkCity);
//        chkCity.setChecked(true);
//        chkCity.setOnCheckedChangeListener(myCheckboxListener);
//
//        CheckBox chkSensor = (CheckBox) v.findViewById(R.id.chkSensor);
//        chkSensor.setChecked(true);
//        chkSensor.setOnCheckedChangeListener(myCheckboxListener);
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
//                case R.id.chkCity:
//                    if (isChecked == false) {
//
//                        for (int i = 0; i < city_markers.size(); i++) {
//                            city_markers.get(i).setVisible(false);
//                        }
//
//                    } else {
//                        for (int i = 0; i < city_markers.size(); i++){
//                            city_markers.get(i).setVisible(true);
//                        }
//
//                    }
//                    break;
//                case R.id.chkSensor:
//                    if (isChecked == false) {
//                        for (int i = 0; i < sensor_markers.size(); i++) {
//                            sensor_markers.get(i).setVisible(false);
//                            mCircle.get(i).setVisible(false);
//                        }
//
//                    } else {
//                        for (int i = 0; i < sensor_markers.size(); i++){
//                            sensor_markers.get(i).setVisible(true);
//                            mCircle.get(i).setVisible(true);
//                        }
//
//                    }
//                    break;
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
        //ATENÇÃO VER...
        //new DownloadXML().execute(URL);
        //SystemClock.sleep(1000);
        //chkCity = (CheckBox) getActivity().findViewById(R.id.chkCity);

        //Firebase inicial
        //Firebase.setAndroidContext(this);

        FireBaseModule fb = new FireBaseModule();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDateandTime = sdf.format(new Date());
//
//        fbDataInHour = fb.getFirebaseData_CurrentDay(currentDateandTime);


        SystemClock.sleep(2000);

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
//
//        Log.i("Dados Poluentes: ", comparated_data_polluentes.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        //firebase
//        for(int i =0; i<fbDataInHour.size(); i++) {
//            if(fbDataInHour.size()==0 || fbDataInHour==null)  SystemClock.sleep(600);
//            else{
//                //markers
//                sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
//                        title((fbDataInHour.get(i).getHora().substring(0,5)) + ": Temperature - " + fbDataInHour.get(i).getTemperature() + "/ Humidade - " + fbDataInHour.get(i).getHumidade()).icon(BitmapDescriptorFactory.
//                        defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));
//
//                //circles around markers
//                if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<9) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.azulbebe).strokeColor(R.color.azulbebe).strokeWidth(8)));
//                }
//                else if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=9 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<13) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.colorAccent).strokeColor(R.color.colorAccent).strokeWidth(8)));
//                }
//                else if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=13 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<18) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.amarelo).strokeColor(R.color.amarelo).strokeWidth(8)));
//                }
//                else if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=18 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<23) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.laranja).strokeColor(R.color.laranja).strokeWidth(8)));
//                }
//                else if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>=23 && Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))<28) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.laranjaescuro).strokeColor(R.color.laranjaescuro).strokeWidth(8)));
//                }
//                else if(Double.parseDouble(fbDataInHour.get(i).getTemperature().substring(0,2))>28) {
//                    mCircle.add(mMap.addCircle(new CircleOptions().center(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).radius(20).fillColor(R.color.colorPrimary).strokeColor(R.color.colorPrimary).strokeWidth(8)));
//                }
//            }
//        }

        for(int i =0; i<fbDataInStations.size(); i++) {
            if (fbDataInStations.size() == 0 || fbDataInStations == null) SystemClock.sleep(600);
            else {
                stations_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(fbDataInStations.get(i).getLAT(), fbDataInStations.get(i).getLON())).
                        title((fbDataInStations.get(i).getNome_actual())).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
            }
        }

        Log.i("Dados dia de hoje: ", fbDataInStations.toString());

        //xml ATENÇÃO VER...
//        for(int i =0; i<lstItem.size(); i++) {
//            if(lstItem.size()==0 || lstItem==null) SystemClock.sleep(600);
//            else {
//                city_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lstItem.get(i).getLatitude(), lstItem.get(i).getLongitude())).
//                        title(lstItem.get(i).getName()).icon(BitmapDescriptorFactory.
//                        defaultMarker(BitmapDescriptorFactory.HUE_RED))));
//            }
//
//        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 12));

    }

//
//    // DownloadXML AsyncTask
//    private class DownloadXML extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Create a progressbar
//            pDialog = new ProgressDialog(getActivity());
//            // Set progressbar title
//            pDialog.setTitle("Android Simple XML Parsing using DOM Tutorial");
//            // Set progressbar message
//            pDialog.setMessage("Loading...");
//            pDialog.setIndeterminate(false);
//            // Show progressbar
//            pDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(String... Url) {
//            try {
//                java.net.URL url = new URL(Url[0]);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory
//                        .newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                // Download the XML file
//                Document doc = db.parse(new InputSource(url.openStream()));
//                doc.getDocumentElement().normalize();
//                // Locate the Tag Name
//                nodelist = doc.getElementsByTagName("item");
//                SystemClock.sleep(1000);
//
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void args) {
//
//            lstItem =  new ArrayList<>();
//            for (int temp = 0; temp < nodelist.getLength(); temp++) {
//
//                Node nNode = nodelist.item(temp);
//                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) nNode;
//
//                    lstItem.add(new ItemTuristic(getNode("name", eElement), Double.parseDouble(getNode("gps_x", eElement))
//                            ,Double.parseDouble(getNode("gps_y", eElement))));
//
//                }
//            }
//
//            pDialog.dismiss();
//        }
//    }
//
//    // getNode function
//    private static String getNode(String sTag, Element eElement) {
//        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
//                .getChildNodes();
//        Node nValue = (Node) nlList.item(0);
//        return nValue.getNodeValue();
//    }


}
