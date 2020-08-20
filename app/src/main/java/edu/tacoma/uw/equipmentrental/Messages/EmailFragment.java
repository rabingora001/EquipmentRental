package edu.tacoma.uw.equipmentrental.Messages;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.LoginFragment;
import edu.tacoma.uw.equipmentrental.main.EquipmentAddFragment;
import model.Equipment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmailFragment extends Fragment {

    public static final String SEND_EMAIL = "SEND_EMAIL";
    private JSONObject mEmailJSON;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private EmailListener mEmailListener;

//    public interface EmailListener {
//        public void sendEmail(String sendTo, String subject, String email, String message);
//    }

    public EmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_email, container, false);
        final EditText toIdEditText = v.findViewById(R.id.to_id);
        final EditText emailSubjectEditText = v.findViewById(R.id.email_subject_id);
        final EditText emailAddEditText = v.findViewById(R.id.email_add_id);
        final EditText emailMesEditText = v.findViewById(R.id.email_mes_id);
        Button submitButton = v.findViewById(R.id.email_submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendTo= toIdEditText.getText().toString();
                String subject = emailSubjectEditText.getText().toString();
                String email = emailAddEditText.getText().toString();
                String message = emailMesEditText.getText().toString();

                if (TextUtils.isEmpty(sendTo) || !sendTo.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter Valid Email to send to", Toast.LENGTH_SHORT).show();
                    toIdEditText.requestFocus();

                } else if (TextUtils.isEmpty(subject)) {
                    Toast.makeText(v.getContext(), "Enter subject",
                            Toast.LENGTH_SHORT).show();
                    emailSubjectEditText.requestFocus();

                } else if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter valid password with at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                    emailAddEditText.requestFocus();

                } else if (TextUtils.isEmpty(message) || message.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid password with at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                    emailMesEditText.requestFocus();

                } else {
                    sendEmail(sendTo, subject, email, message);
                }






//                Equipment equipment = new Equipment(equipmentEquipment, equipmentShortDesc, equipmentPrice, equipmentEmail);
//                if (mEmailListener != null) {
//                    mEmailListener.sendEmail(equipment);
//
//
//                }
            }
        });
        return v;


    }




    public void sendEmail(String sendTo, String subject, String email, String message) {
        StringBuilder url = new StringBuilder(getString(R.string.email_url));

        mEmailJSON = new JSONObject();
        try {
            mEmailJSON.put("sendto", sendTo);
            mEmailJSON.put("subject", subject);
            mEmailJSON.put("email", email);
            mEmailJSON.put("message", message);


            new EmailFragment.EmailAsyncTask().execute(url.toString());

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Invalid "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private class EmailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    // For Debugging
//                    Log.i(SEND_EMAIL, mEmailJSON.toString());
                    wr.write(mEmailJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add new equipment, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to send email")) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getContext(), "Email sent successfully"
                            , Toast.LENGTH_SHORT).show();

                    //go back to equipment list page
//                    displayEquipmentBrowsingPage();
                }
                else {
                    Toast.makeText(getContext(), "Email could not be sent: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(SEND_EMAIL, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "JSON Parsing error on sending email"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
//                Log.e(SEND_EMAIL, e.getMessage());
            }
        }
    }







}