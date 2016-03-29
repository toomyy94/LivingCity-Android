package ua.deti.cm.pt.livingcity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import ua.deti.cm.pt.livingcity.modules.LocationCoord;
import ua.deti.cm.pt.livingcity.modules.PanoramioReader;

@SuppressLint("ValidFragment")
public class PhotosList extends Fragment  {

    private String[] lstEstados;
    private LocationCoord gps;
    public PhotosList(LocationCoord gps) throws IOException, JSONException {
        this.gps = gps;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View myInflatedView = inflater.inflate(R.layout.fragment_list_photo, container,false);


        //PanoramioReader sd = new PanoramioReader() ;

        // Set the Text to try this out
        TextView t = (TextView) myInflatedView.findViewById(R.id.textView2);
        t.setText("glats"+gps.getLatitude());


        return myInflatedView;
    }





}
