package ua.deti.cm.pt.livingcity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.IOException;

import ua.deti.cm.pt.livingcity.modules.LocationCoord;

@SuppressLint("ValidFragment")
public class Configs extends Fragment  {

    private String[] lstEstados;
    private LocationCoord gps;
    public Configs(LocationCoord gps) throws IOException, JSONException {
        this.gps = gps;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.fragment_configs, container,false);


        return myInflatedView;
    }





}
