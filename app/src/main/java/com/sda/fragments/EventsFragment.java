package com.sda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sda.R;
import com.sda.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsFragment extends Fragment {


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        ListView listView = view.findViewById(R.id.events_listView);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            EventRepository eventRepository = new EventRepository();
            data = eventRepository.getEventsForListView();

            SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                    android.R.layout.simple_list_item_2,
                    new String[]{"First Line", "Second Line"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            listView.setAdapter(adapter);
        } catch (Exception e) {

            e.printStackTrace();
        }

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
}
