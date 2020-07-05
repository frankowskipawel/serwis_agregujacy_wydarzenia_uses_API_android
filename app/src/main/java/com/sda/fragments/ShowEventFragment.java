package com.sda.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sda.R;
import com.sda.entity.EventAPI;
import com.sda.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class ShowEventFragment extends Fragment {

    private int eventId;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_show_event, container, false);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        EventRepository eventRepository = new EventRepository();
        EventAPI event = eventRepository.findById(eventId);
        TextView textView = view.findViewById(R.id.textView_show_event);
        textView.setText(event.toString());

        return view;
    }



    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
