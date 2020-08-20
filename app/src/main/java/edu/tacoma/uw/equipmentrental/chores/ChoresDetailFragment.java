package edu.tacoma.uw.equipmentrental.chores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import edu.tacoma.uw.equipmentrental.R;
import model.Chore;
import model.Equipment;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChoresDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Chore mChore;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChoresDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mChore = (Chore) getArguments().getSerializable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mChore.getmChore());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chores_detail, container, false);


        if (mChore != null) {
            ((TextView) rootView.findViewById(R.id.chore_detail_chore)).setText(mChore.getmChore());
            ((TextView) rootView.findViewById(R.id.chore_detail_addr)).setText(mChore.getmAddr());
            ((TextView) rootView.findViewById(R.id.chore_detail_longdesc)).setText(mChore.getmChore());
            ((TextView) rootView.findViewById(R.id.chore_detail_price)).setText(mChore.getmPrice());
            ((TextView) rootView.findViewById(R.id.chore_detail_email)).setText(mChore.getmEmail());
        }

        //share button for sharing equitment details.
        Button shareBtn = rootView.findViewById(R.id.btn_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/Plain");
                String shareTitle = mChore.getmChore();
                String shareDetails = "Description: " + mChore.getmLongDesc() + "\n Rent Price: "
                        + mChore.getmPrice();
                intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareDetails);
                getActivity().startActivity(Intent.createChooser(intent, "Share your chores :"));
            }
        });

        return rootView;
    }
}