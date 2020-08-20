package edu.tacoma.uw.equipmentrental.chores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.EquipmentAddFragment;
import model.Chore;
import model.Equipment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoresAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoresAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ChoresAddFragment.AddListener mAddListener;

    public interface AddListener {
        public void addChore(Chore chore);
    }

    public ChoresAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquipmentAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoresAddFragment newInstance(String param1, String param2) {
        ChoresAddFragment fragment = new ChoresAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (ChoresAddFragment.AddListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chores_add, container
                , false);
        getActivity().setTitle("Add New Chore");
        final EditText choreEditText = v.findViewById(R.id.add_chore_chore);
        final EditText choreAddreEditText = v.findViewById(R.id.add_chore_addr);
        final EditText choreLongDescEditText = v.findViewById(R.id.add_chore_longdesc);
        final EditText chorePriceEditText = v.findViewById(R.id.add_chore_price);
        final EditText choreEmailEditText = v.findViewById(R.id.add_choreemail);
        Button addButton = v.findViewById(R.id.btn_add_equipment);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choreName = choreEditText.getText().toString();
                String choreAddr = choreAddreEditText.getText().toString();
                String choreLongdesc = choreLongDescEditText.getText().toString();
                String chorePrice = chorePriceEditText.getText().toString();
                String choreEmail = choreEmailEditText.getText().toString();
                Chore chore = new Chore(choreName, choreAddr, choreLongdesc, chorePrice, choreEmail);
                if (mAddListener != null) {
                    mAddListener.addChore(chore);


                }
            }
        });
        return v;
    }
}