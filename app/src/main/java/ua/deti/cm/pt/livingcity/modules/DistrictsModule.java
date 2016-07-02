package ua.deti.cm.pt.livingcity.modules;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by roliveira on 30-06-2016.
 */
public class DistrictsModule extends AsyncTask<String, Void, Void> {

    private NodeList nodelist;
    private static String district = null;
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
            nodelist = doc.getElementsByTagName("result");

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void args) {


        if (nodelist.getLength() != 0){
            for (int temp = 0; temp < nodelist.getLength(); temp++) {

                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    if (getNode("type", eElement).equals("administrative_area_level_1")){
                        Log.e("district: ", getNode("formatted_address", eElement));

                        district = getNode("formatted_address", eElement);
                    }


                }
            }
        }


    }


    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }


    public static String getDistrict(){
        return district;
    }

}
