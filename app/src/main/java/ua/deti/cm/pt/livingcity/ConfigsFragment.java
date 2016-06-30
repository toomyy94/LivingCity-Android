package ua.deti.cm.pt.livingcity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.json.JSONException;

import java.io.IOException;

import ua.deti.cm.pt.livingcity.modules.LocationCoord;
import ua.deti.cm.pt.livingcity.modules.MyService;

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

        View view = inflater.inflate(R.layout.fragment_configs, container, false);
        Switch toggle = (Switch) view.findViewById(R.id.switch1);
        toggle.setChecked(MyService.isOperational());

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getActivity(), MyService.class);
                    getActivity().startService(intent);
                } else {
                    Intent intent = new Intent(getActivity(), MyService.class);
                    getActivity().stopService(intent);
                }
            }
        });

        return view;

    }


}
