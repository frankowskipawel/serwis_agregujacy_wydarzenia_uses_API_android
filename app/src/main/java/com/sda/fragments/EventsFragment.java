package com.sda.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.google.gson.GsonBuilder;
import com.sda.R;
import com.sda.entity.EventAPI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class EventsFragment extends Fragment {


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        Thread thread = new Thread() {
            public void run() {
               List<EventAPI> events = eventsJsonParse();
                for (EventAPI event : events) {
                    Log.i(TAG, "onCreateView: 0 ---- " + event);
                }
            }
        };
        thread.start();

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(EventsFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    private List<EventAPI> eventsJsonParse() {
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
}
