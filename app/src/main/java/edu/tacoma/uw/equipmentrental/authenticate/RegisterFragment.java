package edu.tacoma.uw.equipmentrental.authenticate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    //submit button listener.
    RegisterFragmentListener mRegisterFragmentListener;


    /**
     * interface for submit button listener
     */
    public interface RegisterFragmentListener{
        public void registerSubmit(String firstName, String lastName, String username, String address, String email, String pwd);

    }
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
    checks for authentication for the text entries for registrations in this method.
    Throws errors if not properly authenticated.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Sign-Up to Equipment Rental");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mRegisterFragmentListener = (RegisterFragmentListener) getActivity();

        final EditText firstNameText = view.findViewById(R.id.register_first_name_id);
        final EditText lastNameText = view.findViewById(R.id.register_last_name_id);
        final EditText usernameText = view.findViewById(R.id.register_username_id);
        final EditText userAddressText = view.findViewById(R.id.register_address_id);
        final EditText emailText = view.findViewById(R.id.register_email_id);
        final EditText passwordText = view.findViewById(R.id.register_password_id);
        final EditText retypedPasswordText = view.findViewById(R.id.register_retype_password_id);

        Button registerSubmitButton = view.findViewById(R.id.register_submit_button_id);
        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String username = usernameText.getText().toString();
                String userAddress = userAddressText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String retypedPassword = retypedPasswordText.getText().toString();

                if (TextUtils.isEmpty(firstName) || firstName.length() < 2) {
                    Toast.makeText(v.getContext(), "Enter valid first name with at least 2 characters",
                            Toast.LENGTH_SHORT).show();
                    firstNameText.requestFocus();

                } else if (TextUtils.isEmpty(lastName) || lastName.length() < 2) {
                    Toast.makeText(v.getContext(), "Enter valid last name with at least 2 characters",
                            Toast.LENGTH_SHORT).show();
                    lastNameText.requestFocus();

                } else if (TextUtils.isEmpty(username) || username.length() < 2) {
                    Toast.makeText(v.getContext(), "Enter valid username with at least 2 characters",
                            Toast.LENGTH_SHORT).show();
                    usernameText.requestFocus();

                } else if (TextUtils.isEmpty(userAddress) || userAddress.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid Address with at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                    userAddressText.requestFocus();

                } else if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                    emailText.requestFocus();

                } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid password with at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                    passwordText.requestFocus();

                } else if (!(password.equals(retypedPassword))) {
                    Toast.makeText(v.getContext(), "password did not match",
                            Toast.LENGTH_SHORT).show();
                    passwordText.requestFocus();

                } else {
                    mRegisterFragmentListener.registerSubmit(firstName, lastName, username, userAddress, email, password);


                }
            }
        });
        return view;
    }
}