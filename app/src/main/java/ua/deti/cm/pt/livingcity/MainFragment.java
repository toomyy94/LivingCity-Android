package ua.deti.cm.pt.livingcity;


import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;


import ua.deti.cm.pt.livingcity.modules.FireBaseDataClass;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.ItemTuristic;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;



/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment  implements OnMapReadyCallback {

    private CheckBox chkCity;
    List<Marker> sensor_markers = new ArrayList<>();
    List<Marker> city_markers = new ArrayList<>();
    private NodeList nodelist;
    private ProgressDialog pDialog;
    private List<ItemTuristic> lstItem = null;
    private GoogleMap mMap;
    private LocationCoord gps;
    private String URL=null;
    private List<FireBaseDataClass> fbDataInHour = null;
    public MainFragment(LocationCoord gps, List<FireBaseDataClass> fbDataInHour) {
        this.fbDataInHour = fbDataInHour;
        this.gps = gps;
        URL = "http://www.tixik.com/api/nearby?lat="+gps.getLatitude()+"&lng="+gps.getLongitude()+"&limit=20&key=demo";
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
                    if (isChecked == true) {

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
                    if (isChecked == true) {
                        for (int i = 0; i < sensor_markers.size(); i++) {
                            sensor_markers.get(i).setVisible(false);
                        }

                    } else {
                        for (int i = 0; i < sensor_markers.size(); i++){
                            sensor_markers.get(i).setVisible(true);
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadXML().execute(URL);
        SystemClock.sleep(2000);
        chkCity = (CheckBox) getActivity().findViewById(R.id.chkCity);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //firebase
        for(int i =0; i<fbDataInHour.size(); i++) {
            if(fbDataInHour.size()==0 || fbDataInHour==null)  SystemClock.sleep(600);
            else{
                sensor_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(fbDataInHour.get(i).getLatitude()), Double.parseDouble(fbDataInHour.get(i).getLongitude()))).
                        title(fbDataInHour.get(i).getHora().substring(5) + ": Temperature - " + fbDataInHour.get(i).getTemperature() + "/ Humidade - " + fbDataInHour.get(i).getHumidade()).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));
            }
        }

        //xml
        for(int i =0; i<lstItem.size(); i++) {
            if(lstItem.size()==0 || lstItem==null) SystemClock.sleep(600);
            else {
                city_markers.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lstItem.get(i).getLatitude(), lstItem.get(i).getLongitude())).
                        title(lstItem.get(i).getName()).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_RED))));
            }

        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 12));

    }


    // DownloadXML AsyncTask
    private class DownloadXML extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            pDialog = new ProgressDialog(getActivity());
            // Set progressbar title
            pDialog.setTitle("Android Simple XML Parsing using DOM Tutorial");
            // Set progressbar message
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            // Show progressbar
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                nodelist = doc.getElementsByTagName("item");

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {

            lstItem =  new ArrayList<>();

            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    lstItem.add(new ItemTuristic(getNode("name", eElement), Double.parseDouble(getNode("gps_x", eElement))
                            ,Double.parseDouble(getNode("gps_y", eElement))));

                }
            }

            pDialog.dismiss();
        }
    }

    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }



}
