package ua.deti.cm.pt.livingcity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.communication.IOnPointFocusedListener;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import java.util.List;
import ua.deti.cm.pt.livingcity.modules.ChartFragment;
import ua.deti.cm.pt.livingcity.modules.DistrictsModule;
import ua.deti.cm.pt.livingcity.modules.FireBaseSensorData;


/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Junho 2016
 */

@SuppressLint("ValidFragment")
public class MonitoringFragment extends ChartFragment {

    private ValueLineChart chartTemp, chartHumd;
    private List<FireBaseSensorData> sensorValue = null;

    public MonitoringFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorValue = SensorTouristicFragment.getValueSensorInFireBase();

        Log.i("Sensor Values:", sensorValue.toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monitoring, container, false);

        TextView tx = (TextView) view.findViewById(R.id.distr);
        tx.clearComposingText();
        tx.append(DistrictsModule.getDistrict());

        chartTemp = (ValueLineChart) view.findViewById(R.id.temp);
        chartHumd = (ValueLineChart) view.findViewById(R.id.humd);
        loadDataTemp();
        loadDataHumd();

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        chartTemp.startAnimation();
        chartHumd.startAnimation();
    }

    @Override
    public void restartAnimation() {
        chartTemp.startAnimation();
        chartHumd.startAnimation();
    }

    @Override
    public void onReset() {
        chartTemp.resetZoom(true);
        chartHumd.resetZoom(true);
    }

    private void loadDataTemp() {
        ValueLineSeries seriesTemp = new ValueLineSeries();
        seriesTemp.setColor(Color.GREEN);

        for (int i=0; i<sensorValue.size(); i++){
            Log.e("ce", sensorValue.get(i).getDistrct());
            if (sensorValue.get(i).getDistrct().equals(DistrictsModule.getDistrict())){
                String temp = sensorValue.get(i).getTemperature().split("°")[0].replace(",",".");
                seriesTemp.addPoint(new ValueLinePoint(sensorValue.get(i).getHora(), Float.parseFloat(temp)));
            }
        }

        chartTemp.addSeries(seriesTemp);
        chartTemp.addStandardValue(27);
        chartTemp.addStandardValue(-5);
        chartTemp.addStandardValue(55);
        chartTemp.setOnPointFocusedListener(new IOnPointFocusedListener() {
            @Override
            public void onPointFocused(int _PointPos) {
                Log.d("Test", "Pos: " + _PointPos);
            }
        });

    }


    private void loadDataHumd() {
        ValueLineSeries seriesHumd = new ValueLineSeries();
        seriesHumd.setColor(Color.GRAY);

        for (int i=0; i<sensorValue.size(); i++){
            if (sensorValue.get(i).getDistrct().equals(DistrictsModule.getDistrict())){
                String temp = sensorValue.get(i).getHumidade().split("%")[0];
                seriesHumd.addPoint(new ValueLinePoint(sensorValue.get(i).getHora(), Float.parseFloat(temp)));
            }
        }

        chartHumd.addSeries(seriesHumd);
        chartHumd.addStandardValue(50);
        chartHumd.addStandardValue(0);
        chartHumd.addStandardValue(100);
        chartHumd.setOnPointFocusedListener(new IOnPointFocusedListener() {
            @Override
            public void onPointFocused(int _PointPos) {
                Log.d("Test", "Pos: " + _PointPos);
            }
        });

    }


}
