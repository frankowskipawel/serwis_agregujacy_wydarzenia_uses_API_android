package com.sda.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.sda.R;
import com.sda.repository.EventRepository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventsFragment extends Fragment {

    ListView listView;
    Spinner spinner;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View view = inflater.inflate(R.layout.fragment_events, container, false);

        listView = (ListView) view.findViewById(R.id.events_listView);

        spinner = view.findViewById(R.id.spinner);
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

                startAsyncTask(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // CLICKED ITEM ON LISTVIEW //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap) clickItemObj;
                String header = (String) clickItemMap.get("First Line");
                String content = (String) clickItemMap.get("Second Line");

                ShowEventFragment fragment = new ShowEventFragment();
                fragment.setEventId(Integer.parseInt(getIdFromString(header)));
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getTag()).addToBackStack("tag").commit(); //addToBackStack is back after back key pressed

            }
        });

        startAsyncTask(view);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public static String getIdFromString(String header) {

        Pattern pattern = Pattern.compile("^#\\d+ ");
        Matcher matcher = pattern.matcher(header);

        while (matcher.find()) {
            String id = matcher.group().substring(1, matcher.group().length() - 1);
            return id;
        }
        return null;
    }

    ////////////////////////////////////Async task/////////////////////////////////////////

    public void startAsyncTask(View v) {
        GetDataFromApiAsyncTask task = new GetDataFromApiAsyncTask(this);
        task.execute();
    }

    private class GetDataFromApiAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<EventsFragment> activityWeakReference;
        private SimpleAdapter adapter;

        GetDataFromApiAsyncTask(EventsFragment activity) {
            activityWeakReference = new WeakReference<EventsFragment>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            EventRepository eventRepository = new EventRepository();
            try {
                data = eventRepository.getEventsForListView(getContext(), spinner.getSelectedItem().toString());

                adapter = new SimpleAdapter(getContext(), data,
                        android.R.layout.simple_list_item_2,
                        new String[]{"First Line", "Second Line"},
                        new int[]{android.R.id.text1, android.R.id.text2});
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "OK";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            return;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            EventsFragment activity = activityWeakReference.get();
            activity.listView.setAdapter(adapter);
        }
    }
}
