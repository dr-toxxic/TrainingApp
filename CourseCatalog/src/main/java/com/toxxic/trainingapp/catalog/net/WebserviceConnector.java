package com.toxxic.trainingapp.catalog.net;

import android.text.format.DateUtils;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


/**
 * WebserviceConnector - allows access to the training app WebService SOAP Methods
 * @author smcaton
 * @version 8/16/2017
 */

public class WebserviceConnector {
    private static final String NAMESPACE = "http://wpws.bkahlert.com";
    private static final String URL = "https://trainingapp.purplepenguin.net/index.php?/wpws/";
    private static final String METHOD_NAME = "getPosts";
    private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

    public static String getPosts(String args) {
        //Create request
        String posts = "";
        SoapObject request = new SoapObject(WebserviceConnector.NAMESPACE, WebserviceConnector.METHOD_NAME);
        //Property which holds input parameters
        PropertyInfo argsPI = new PropertyInfo();
        //Set Name
        argsPI.setName("args");
        //Set Value
        argsPI.setValue(args);
        //Set dataType
        argsPI.setType(String.class);
        //Add the property to request object
        request.addProperty(argsPI);
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
            SoapObject response = (SoapObject) envelope.getResponse();
            //Assign it to fahren static variable

            posts = response.toString();
            Log.d(WebserviceConnector.class.getSimpleName(), posts);
            int count = response.getPropertyCount();
            for(int i = 0; i < count; i++)
            {
                PropertyInfo propPI;
                SoapObject propSO;
                Object prop = response.getProperty(i);
                Log.d(WebserviceConnector.class.getSimpleName(), prop.toString());
                if( prop instanceof PropertyInfo)
                {
                    propPI = (PropertyInfo)prop;
                    Log.d(WebserviceConnector.class.getSimpleName(), propPI.toString());
                }
                else if( prop instanceof SoapObject)
                {
                    propSO = (SoapObject)prop;
                    Log.d(WebserviceConnector.class.getSimpleName(), propSO.toString());

                    Post post = new Post(propSO);
                    Log.d(WebserviceConnector.class.getSimpleName(), post.toString());
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    public static class WebserviceObject {
        String getStringValue(SoapObject soapObject, String propName)
        {
            String result = "";
            Object prop = soapObject.getProperty(propName);
            if (prop instanceof SoapPrimitive ) {
                result = ((SoapPrimitive) prop).toString();
            }
            else if(prop instanceof SoapObject)
            {
                if(  ((SoapObject)prop).getPropertyCount() > 0 )
                {
                    Log.d(getClass().getSimpleName(), "Unknown SoapObject found: " + prop.toString());
                }
                else{
                    Log.d(getClass().getSimpleName(), "Empty Property Found.");
                }
            }
            else
            {
                Log.d(getClass().getSimpleName(), "Unknown Object found: " + prop.toString());
            }

            return result;
        }
         int getIntValue(SoapObject soapObject, String propName)
        {
            return Integer.parseInt( getStringValue(soapObject, propName));
        }
         Date getDateValue(SoapObject soapObject, String propName){
            String strValue = getStringValue(soapObject, propName);
            Date dateValue = DateFormat.getDateInstance().getCalendar().getTime();
            try {
                dateValue = DateFormat.getDateInstance().parse(            strValue        );

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return dateValue;
        }

    }
    public static class Post extends WebserviceObject{
        public int id;
        public int author;
        public String date;
        public String dateGmt;
        public String content;
        public String title;
        public String excerpt;
        public String status;
        public String commentStatus;
        public String pingStatus;
        public String password;
        public String name;
        public String toPing;
        public String pinged;
        public String modified;
        public String modifiedGmt;
        public String contentFiltered;
        public String parentId;
        public String guid;
        public String menuOrder;
        public String type;
        public String mimeType;
        public String commentCount;
        public String filter;

        Post(SoapObject soapObject)
        {
            this.id = this.getIntValue(soapObject, "id");
            this.author = this.getIntValue(soapObject, "author");
            this.date = this.getStringValue(soapObject, "date");
            this.dateGmt = this.getStringValue(soapObject, "dateGmt");
            this.content = this.getStringValue(soapObject, "content");
            this.title = this.getStringValue(soapObject, "title");
            this.excerpt = this.getStringValue(soapObject, "excerpt");
            this.status = this.getStringValue(soapObject, "status");
            this.commentStatus = this.getStringValue(soapObject, "commentStatus");
            this.pingStatus = this.getStringValue(soapObject, "pingStatus");
            this.password = this.getStringValue(soapObject, "password");
            this.name = this.getStringValue(soapObject, "name");
            this.toPing = this.getStringValue(soapObject, "toPing");
            this.pinged = this.getStringValue(soapObject, "pinged");
            this.modified = this.getStringValue(soapObject, "modified");
            this.modifiedGmt = this.getStringValue(soapObject, "modifiedGmt");
            this.contentFiltered = this.getStringValue(soapObject, "contentFiltered");
            this.parentId = this.getStringValue(soapObject, "parentId");
            this.guid = this.getStringValue(soapObject, "guid");
            this.menuOrder = this.getStringValue(soapObject, "menuOrder");
            this.type = this.getStringValue(soapObject, "type");
            this.mimeType = this.getStringValue(soapObject, "mimeType");
            this.commentCount = this.getStringValue(soapObject, "commentCount");
            this.filter = this.getStringValue(soapObject, "filter");

        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Post{");
            sb.append("id=").append(id);
            sb.append(", author=").append(author);
            sb.append(", date='").append(date).append('\'');
            sb.append(", dateGmt='").append(dateGmt).append('\'');
            sb.append(", content='").append(content).append('\'');
            sb.append(", title='").append(title).append('\'');
            sb.append(", excerpt='").append(excerpt).append('\'');
            sb.append(", status='").append(status).append('\'');
            sb.append(", commentStatus='").append(commentStatus).append('\'');
            sb.append(", pingStatus='").append(pingStatus).append('\'');
            sb.append(", password='").append(password).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", toPing='").append(toPing).append('\'');
            sb.append(", pinged='").append(pinged).append('\'');
            sb.append(", modified='").append(modified).append('\'');
            sb.append(", modifiedGmt='").append(modifiedGmt).append('\'');
            sb.append(", contentFiltered='").append(contentFiltered).append('\'');
            sb.append(", parentId='").append(parentId).append('\'');
            sb.append(", guid='").append(guid).append('\'');
            sb.append(", menuOrder='").append(menuOrder).append('\'');
            sb.append(", type='").append(type).append('\'');
            sb.append(", mimeType='").append(mimeType).append('\'');
            sb.append(", commentCount='").append(commentCount).append('\'');
            sb.append(", filter='").append(filter).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
    public static class Page extends Post{

        Page(SoapObject soapObject) {
            super(soapObject);
        }
    }

}
