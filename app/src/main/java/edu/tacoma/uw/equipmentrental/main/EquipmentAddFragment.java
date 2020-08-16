package edu.tacoma.uw.equipmentrental.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.tacoma.uw.equipmentrental.R;
import model.Equipment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EquipmentAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AddListener mAddListener;

    public interface AddListener {
        public void addEquipment(Equipment eqpuipment);
    }

    public EquipmentAddFragment() {
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
    public static EquipmentAddFragment newInstance(String param1, String param2) {
        EquipmentAddFragment fragment = new EquipmentAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (AddListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_equipment_add, container
                , false);
        getActivity().setTitle("Add New Equipment");
        final EditText equipmentEquipmentEditText = v.findViewById(R.id.add_equipment_equipment);
        final EditText equipmentShortDescEditText = v.findViewById(R.id.add_equipment_short_desc);
        final EditText equipmentPriceEditText = v.findViewById(R.id.add_equipment_price);

        Button addButton = v.findViewById(R.id.btn_add_equipment);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String equipmentEquipment = equipmentEquipmentEditText.getText().toString();
                String equipmentShortDesc = equipmentShortDescEditText.getText().toString();
                String equipmentPrice = equipmentPriceEditText.getText().toString();
                Equipment equipment = new Equipment(equipmentEquipment, equipmentShortDesc, equipmentPrice);
                if (mAddListener != null) {
                    mAddListener.addEquipment(equipment);
                }
            }
        });
        return v;
    }
}