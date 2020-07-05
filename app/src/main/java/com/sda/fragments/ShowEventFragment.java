package com.sda.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sda.R;
import com.sda.entity.EventAPI;
import com.sda.repository.EventRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

        ImageView imageView = view.findViewById(R.id.imageView_show_event);
        Picasso.get().load("https://wegiel.home.pl/ourmeetup/"+event.getPicture()).into(imageView);

        TextView titleTextView = view.findViewById(R.id.title_show_event);
        titleTextView.setText(event.getTitle());

        TextView startDateTextView = view.findViewById(R.id.startDate_show_event);
        startDateTextView.setText(event.getStartDate().substring(0,16));

        TextView endDateTextView = view.findViewById(R.id.endDate_show_event);
        endDateTextView.setText(event.getEndDate().substring(0,16));

        TextView cityTextView = view.findViewById(R.id.city_show_event);
        cityTextView.setText(event.getCity());

        TextView publisherTextView = view.findViewById(R.id.publisher_show_event);
        publisherTextView.setText(event.getUser());

        TextView descriptionTextView = view.findViewById(R.id.description_show_event);
        descriptionTextView.setText(event.getDescription());

        return view;
    }


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
