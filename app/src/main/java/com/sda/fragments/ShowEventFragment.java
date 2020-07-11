package com.sda.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sda.R;
import com.sda.entity.EventAPI;
import com.sda.repository.EventRepository;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShowEventFragment extends Fragment {

    private int eventId;
    EventAPI event;
    ImageView imageView;
    TextView titleTextView;
    TextView startDateTextView;
    TextView endDateTextView;
    TextView cityTextView;
    TextView publisherTextView;
    TextView descriptionTextView;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_show_event, container, false);

        startAsyncTask(view);

        imageView = view.findViewById(R.id.imageView_show_event);
        titleTextView = view.findViewById(R.id.title_show_event);
        startDateTextView = view.findViewById(R.id.startDate_show_event);
        endDateTextView = view.findViewById(R.id.endDate_show_event);
        cityTextView = view.findViewById(R.id.city_show_event);
        publisherTextView = view.findViewById(R.id.publisher_show_event);
        descriptionTextView = view.findViewById(R.id.description_show_event);

        return view;
    }

    public void startAsyncTask(View v) {
        ShowEventFragment.GetDataFromApiAsyncTask task = new ShowEventFragment.GetDataFromApiAsyncTask(this);
        task.execute();
    }

    private class GetDataFromApiAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<ShowEventFragment> activityWeakReference;
        private SimpleAdapter adapter;

        GetDataFromApiAsyncTask(ShowEventFragment activity) {
            activityWeakReference = new WeakReference<ShowEventFragment>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            EventRepository eventRepository = new EventRepository();
            event = eventRepository.findById(eventId);

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
            ShowEventFragment activity = activityWeakReference.get();
            Picasso.get().load("https://wegiel.home.pl/ourmeetup/" + event.getPicture()).into(imageView);

            activity.titleTextView.setText(event.getTitle());
            activity.startDateTextView.setText(event.getStartDate().substring(0, 16));
            activity.endDateTextView.setText(event.getEndDate().substring(0, 16));
            activity.cityTextView.setText(event.getCity());
            activity.publisherTextView.setText(event.getUser());
            activity.descriptionTextView.setText(event.getDescription());

        }
    }


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
