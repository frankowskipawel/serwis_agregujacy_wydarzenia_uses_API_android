package com.sda.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sda.R;
import com.sda.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.android.volley.VolleyLog.TAG;

public class EventsFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View view = inflater.inflate(R.layout.fragment_events, container, false);

        final ListView listView = view.findViewById(R.id.events_listView);

        final Spinner spinner = view.findViewById(R.id.spinner);
        List<String> items = new ArrayList<String>();
        items.add("all");
        items.add("future");
        items.add("past");
        items.add("ongoing");
        ArrayAdapter<String> materialAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, items);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(materialAdapter);

     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             Log.i(TAG, "onItemSelected: ");
             List<Map<String, String>> data = new ArrayList<Map<String, String>>();
             EventRepository eventRepository = new EventRepository();
             try {
                 data = eventRepository.getEventsForListView(getContext(),spinner.getSelectedItem().toString());

                 SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                         android.R.layout.simple_list_item_2,
                         new String[]{"First Line", "Second Line"},
                         new int[]{android.R.id.text1, android.R.id.text2});
                 listView.setAdapter(adapter);
             } catch (Exception e) {
                 e.printStackTrace();
             }

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });

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
