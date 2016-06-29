package ua.deti.cm.pt.livingcity.modules;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by roliveira on 30-06-2016.
 */
public class DisplayToast implements Runnable {
    private final Context mContext;
    String mText;

    public DisplayToast(Context mContext, String text){
        this.mContext = mContext;
        mText = text;
    }

    public void run(){
        Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show();
    }
}