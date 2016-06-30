package ua.deti.cm.pt.livingcity.modules;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import android.os.Handler;

import ua.deti.cm.pt.livingcity.modules.DisplayToast;
import ua.deti.cm.pt.livingcity.modules.DistrictsModule;
import ua.deti.cm.pt.livingcity.modules.FireBaseModule;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;

public class MyService extends IntentService{

    private static boolean operational;
    private Handler mHandler;


    public MyService(){
        super("Thread");
        operational = true;
        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Automatic collection ON!", Toast.LENGTH_LONG).show();
        operational = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Automatic collection OF!", Toast.LENGTH_LONG).show();
        operational = false;
        super.onDestroy();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (this){
            int count = 0;
            while (operational){
                try {

                    count++;

                    double mTemperature = getTempRandom();
                    double mHumidity = getHumRandom();

                    Log.e("Valores automaticos", count + "\nNew value added! (temp:" + mTemperature + "humd:" + mHumidity + ")");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());

                    LocationCoord gps = new LocationCoord(this);

                    FireBaseModule fbm = new FireBaseModule();
                    fbm.addValuesFireBase(String.valueOf(mTemperature), String.valueOf(mHumidity), gps.getLatitude(), gps.getLongitude(), currentDateandTime, DistrictsModule.getDistrict());

                    mHandler.post(new DisplayToast(this, "New value added!\n(temp:" + mTemperature + "humd:" + mHumidity + ")"));

                    wait(180000);

                    //wait(60000);

                }catch (InterruptedException ex){
                    ex.getStackTrace();
                }
            }
        }

    }



    private double getTempRandom(){
        Random random = new Random();
        return  random.nextInt((32 - 22) + 1) + 22;
    }

    private double getHumRandom(){
        Random random = new Random();
        return  random.nextInt((63 - 47) + 1) + 47;
    }


    public static boolean isOperational(){
        return operational;
    }

}

