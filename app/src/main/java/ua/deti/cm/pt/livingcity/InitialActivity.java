package ua.deti.cm.pt.livingcity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings("ALL")
public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);


        //Beginning the loading animation as we attempt to verify registration with SIP
        ImageView ivLoader = (ImageView) findViewById(R.id.IVloadinganimation);
        ivLoader.setBackgroundResource(R.anim.animationloader);


        AnimationDrawable frameAnimation = (AnimationDrawable) ivLoader.getBackground();
        frameAnimation.start();




    }

    public void onClickBtn(View v)
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

}
