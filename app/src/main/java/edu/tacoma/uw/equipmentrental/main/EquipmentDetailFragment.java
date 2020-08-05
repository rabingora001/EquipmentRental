package edu.tacoma.uw.equipmentrental.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import edu.tacoma.uw.equipmentrental.R;
import model.Equipment;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link EquipmentDetailFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class EquipmentDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Equipment mEquipment;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EquipmentDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mEquipment = (Equipment) getArguments().getSerializable(ARG_ITEM_ID);

//                    EquipmentContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mEquipment.getmEquipmentEquipment());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mEquipment != null) {
            ((TextView) rootView.findViewById(R.id.equipment_detail_equipment)).setText(mEquipment.getmEquipmentEquipment());
            ((TextView) rootView.findViewById(R.id.equipment_detail_short_desc)).setText(mEquipment.getmEquipmentShortDesc());
            ((TextView) rootView.findViewById(R.id.equipment_detail_price)).setText(mEquipment.getmEquipmentPrice());
        }

        return rootView;
    }
}