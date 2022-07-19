package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnBandSelectedListener{
        void onBandSelected(int bandId);
    }

    //Reference to the activity
    private OnBandSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.band_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send bands to recycler view
        BandAdapter adapter = new BandAdapter(BandDatabase.getInstance(getContext()).getBands());
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBandSelectedListener) {
            mListener = (OnBandSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBandSelectedListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    private class BandHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Band mBand;

        private TextView mNameTextView;

        public BandHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_band, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.bandName);
        }

        public void bind(Band band) {
            mBand = band;
            mNameTextView.setText(mBand.getName());
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what band was clicked
            mListener.onBandSelected(mBand.getId());
        }
    }

    private class BandAdapter extends RecyclerView.Adapter<BandHolder> {

        private List<Band> mBands;

        public BandAdapter(List<Band> bands) {
            mBands = bands;
        }

        @Override
        public BandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BandHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BandHolder holder, int position) {
            Band band = mBands.get(position);
            holder.bind(band);
        }

        @Override
        public int getItemCount() {
            return mBands.size();
        }
    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //Start DetailsActivity
            String bandId = (String) view.getTag();
            mListener.onBandSelected(Integer.parseInt(bandId));
        }
    };
}