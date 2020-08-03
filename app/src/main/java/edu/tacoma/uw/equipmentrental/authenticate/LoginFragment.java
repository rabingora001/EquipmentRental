package edu.tacoma.uw.equipmentrental.authenticate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private LoginFragmentListener mLoginFragmentListener;


    public interface LoginFragmentListener {
         void login(String email, String pwd);
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().setTitle("Welcome to Equipment Rental");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginFragmentListener = (LoginFragmentListener) getActivity();
        final EditText emailText = view.findViewById(R.id.sign_in_email_id);
        final EditText pwdText = view.findViewById(R.id.sign_in_password_id);
        Button loginBtn = view.findViewById(R.id.sign_in_button_id);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString();
                String pwd = pwdText.getText().toString();

                if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                    emailText.requestFocus();

                } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid password with at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                    pwdText.requestFocus();

                } else {
                    mLoginFragmentListener.login(email, pwd);
                }
            }
        });
        return view;
    }

}