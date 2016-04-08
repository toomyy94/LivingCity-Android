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

/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Abril 2016
 */

@SuppressLint("ValidFragment")
public class ConfigsFragment extends Fragment  {

    private String[] lstEstados;
    private LocationCoord gps;
    public ConfigsFragment(LocationCoord gps) throws IOException, JSONException {
        this.gps = gps;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_configs, container,false);
    }

}
