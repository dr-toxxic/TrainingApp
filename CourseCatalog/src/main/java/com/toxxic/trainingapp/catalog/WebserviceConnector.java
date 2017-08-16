package com.toxxic.trainingapp.catalog;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by smcaton on 8/16/2017.
 */

public class WebserviceConnector {
    private static final String NAMESPACE = "http://wpws.bkahlert.com";
    private static final String URL = "https://trainingapp.purplepenguin.net/index.php?/wpws/?wsdl";
    private static final String METHOD_NAME = "getPosts";
    private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

    public static String getPosts(String args) {
        //Create request
        String posts = "";
        SoapObject request = new SoapObject(WebserviceConnector.NAMESPACE, WebserviceConnector.METHOD_NAME);
        //Property which holds input parameters
        PropertyInfo celsiusPI = new PropertyInfo();
        //Set Name
        celsiusPI.setName("args");
        //Set Value
        celsiusPI.setValue(args);
        //Set dataType
        celsiusPI.setType(String.class);
        //Add the property to request object
        request.addProperty(celsiusPI);
        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            //Invole web service
            androidHttpTransport.call(WebserviceConnector.SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //Assign it to fahren static variable
            posts = response.toString();
            Log.d(WebserviceConnector.class.getSimpleName(), posts);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

}
