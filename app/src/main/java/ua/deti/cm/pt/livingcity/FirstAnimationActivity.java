package ua.deti.cm.pt.livingcity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ua.deti.cm.pt.livingcity.modules.ItemTuristic;
import ua.deti.cm.pt.livingcity.modules.LocationCoord;

@SuppressWarnings("ALL")
public class FirstAnimationActivity extends AppCompatActivity {

    private String URL=null;
    private NodeList nodelist;
    private ProgressDialog pDialog;
    private List<String> tmpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);


        URL = "http://www.tixik.com/api/nearby?lat=40.640534805019804&lng=-8.655166625976562&limit=30&key=demo";
        new DownloadXML().execute(URL);

        //Beginning the loading animation as we attempt to verify registration with SIP
        ImageView ivLoader = (ImageView) findViewById(R.id.IVloadinganimation);
        ivLoader.setBackgroundResource(R.anim.animationloader);

        AnimationDrawable frameAnimation = (AnimationDrawable) ivLoader.getBackground();
        frameAnimation.start();

    }

    public void onClickBtn(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putStringArrayListExtra("Tourist", (ArrayList<String>) tmpList);
        startActivity(myIntent);
    }



    // DownloadXML AsyncTask
    private class DownloadXML extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

            tmpList = new ArrayList<String>();

            for (int temp = 0; temp < nodelist.getLength(); temp++) {

                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    tmpList.add(getNode("name", eElement) +"!"+getNode("gps_x", eElement)+"!"+ getNode("gps_y", eElement));

                }
            }

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
