package com.sda.repository;

import android.os.StrictMode;

import com.google.gson.GsonBuilder;
import com.sda.entity.EventAPI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventRepository {

    public List<EventAPI> findAll() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String url = "https://ourmeetup.herokuapp.com/api/events";

        try {
            HttpGet httppost = new HttpGet(url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                List<EventAPI> events = Arrays.asList(new GsonBuilder().create().fromJson(data, EventAPI[].class));

                return events;
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> getEventsForListView (){
        List<Map<String, String>> eventsMap = new ArrayList<Map<String, String>>();
        List<EventAPI> events = findAll();

        for (EventAPI event : events) {
            String description =event.getDescription();
            if (description.length()>100){description=description.substring(0,100)+"...";}

            Map<String, String> row = new HashMap<String, String>(2);
            row.put("First Line", "#"+event.getId()+" "+event.getTitle());
            row.put("Second Line", event.getStartDate().substring(0,10)+" godz. "+
                    event.getStartDate().substring(11,16)+"\n"+event.getCity()+
                    "\n"+description);
            eventsMap.add(row);
        }

      return eventsMap;
    }

}
