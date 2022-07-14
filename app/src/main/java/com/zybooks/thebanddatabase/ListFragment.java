package com.zybooks.thebanddatabase;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayout layout = (LinearLayout) view;

        List<Band> bandList = BandDatabase.getInstance(getContext()).getBands();

        //Iterate over list of bands and create a button for each
        for(int i = 0; i < bandList.size(); i++){
            Button button = new Button(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,10);
            button.setLayoutParams(layoutParams);

            //Set button info to correspond with band name and id
            Band band = BandDatabase.getInstance(getContext()).getBand(i+1);
            button.setText(band.getName());
            button.setTag(Integer.toString(band.getId()));

            //All buttons use the same click listener
            button.setOnClickListener(buttonClickListener);

            //Add button to LinearLayout
            layout.addView(button);
        }

        return view;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //Start DetailsActivity
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            String bandId = (String) view.getTag();
            intent.putExtra(DetailsActivity.EXTRA_BAND_ID, Integer.parseInt(bandId));
            startActivity(intent);
        }
    };
}