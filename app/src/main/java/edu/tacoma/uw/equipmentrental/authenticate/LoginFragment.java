package edu.tacoma.uw.equipmentrental.authenticate;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static ProgressBar mProgressBar;

    private SharedPreferences mSharedPreferences;

    private JSONObject mLoginJSON;


//    private LoginFragmentListener mLoginFragmentListener;

    /*
    Interface for the LoginFragment Listener
     */
//    public interface LoginFragmentListener {
//         void login(String email, String pwd);
//         void signUp();
//    }


    // for fb
    private LoginButton mFbLoginButton;
    CallbackManager mCallbackManager;

    private boolean mIsCustomLogin;



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.fragment_login);
//        mProgressBar = getActivity().findViewById(R.id.progressBar);
//        mProgressBar.setVisibility(View.GONE);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
    }

    /**
     * Checks for authentication for custom login and throws toast error message
     * if not authenticate. Calls the login with facebook id for alternate login.
     * OnClick listeners for the login and signup button.
     * If everything is successful, displays the MainMenuActivity.class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Welcome to Equipment Rental");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

//        mLoginFragmentListener = (LoginFragmentListener) getActivity();

        mCallbackManager = CallbackManager.Factory.create();

        mFbLoginButton = view.findViewById(R.id.fb_sign_in_btn_id1);
        mFbLoginButton.setPermissions(Arrays.asList("email", "public_profile"));

        //Callback registration for fb login
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                displayMainMenuPage();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //custom login
        final EditText emailText = view.findViewById(R.id.sign_in_email_id);
        final EditText pwdText = view.findViewById(R.id.sign_in_password_id);
        //sign In button click listener
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
                    //
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .commit();
                    displayMainMenuPage();
                    //store the email in shared preference session.
                    mSharedPreferences.edit().putString("email", email).commit();
                    //run the login check in heroku backend
                    login(email, pwd);
                }
            }
        });

        //signUp button click listener
        Button singUpButton = view.findViewById(R.id.sign_up_button_id);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        return view;
    }

    /**
     * method to callback manager for the facebook login
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * method to display the MainMenuActivity.class Activity.
     */
    public void displayMainMenuPage() {
        Intent i = new Intent(getActivity(), MainMenuActivity.class);
        getActivity().startActivity(i);
    }


    public void signUp() {
        getFragmentManager().
                beginTransaction()
                .replace(R.id.signInActivity_fragmentPlaceholder_id, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }


    public void login(String email, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.login_url));

        mLoginJSON = new JSONObject();
        try {
            mLoginJSON.put("email", email);
            mLoginJSON.put("password", pwd);
            new LoginAsyncTask().execute(url.toString());

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Invalid Login: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            publishProgress();
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    wr.write(mLoginJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to login, Reason: "
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
            mProgressBar.setVisibility(View.GONE);
            if (s.startsWith("Unable to login")) {
                Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {

                } else {
                    Toast.makeText(getContext(), "Invalid Login"
                            , Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Not valid Login"
                        , Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * progress bar to display the loading progress.
         * @param progress
         */
        @Override
        protected void onProgressUpdate(Void... progress) {
            mProgressBar = getActivity().findViewById(R.id.login_progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(10);
        }
    }

}