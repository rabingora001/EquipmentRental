/**
 * TCSS 450 project-Equipment Rental
 * Summer 2020
 * Rabin Gora
 * Daniel Flynn
 */
package edu.tacoma.uw.equipmentrental.authenticate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.home.HomePageActivity;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

/**
 * This is the main luncher activity for this project.
 */
public class SignInActivity extends AppCompatActivity
        implements RegisterFragment.RegisterFragmentListener {

    private SharedPreferences mSharedPreferences;
    private static ProgressBar mProgressBar;
    private JSONObject mRegisterJSON;

    /*
    displays the SingInActivity with the LoginFragment on top of SignInActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //checks if already logged in with fb
        checkLoginStatus();

        /**
         * check to see if user is already logged in using SharedPreferences.
         * if logged in, go to MainMenuActivity. If not, display the login fragment.
         */
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);

        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            //launch the login fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.signInActivity_fragmentPlaceholder_id, new LoginFragment())
                    .commit();
        } else {
            displayMainMenuPage();
        }
    }


    /*
     method declaration of RegisterFragment.RegisterFragmentListener
     */
    @Override
    public void registerSubmit(String firstName, String lastName, String username, String userAddress, String email, String pwd) {



        //register method
        register(firstName, lastName, username, userAddress, email, pwd);
    }




    /*
    The method to help load Activity from the LoginFragment.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getSupportFragmentManager().getFragments()) {

            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*
    checks login status for facebook with currentToken.
     */
    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            displayMainMenuPage();
        }
    }

    /*
    This method starts the MainMenuActivity.class
     */
    public void displayMainMenuPage() {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }


    /*
     * Displays the home page.
     * @param view
     */
    public void displayHomePage(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void register(String firstName, String lastName, String userName, String userAddress, String email, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.register_url));

        mRegisterJSON = new JSONObject();
        try {
            mRegisterJSON.put("first", firstName);
            mRegisterJSON.put("last", lastName);
            mRegisterJSON.put("username", userName);
            mRegisterJSON.put("address", userAddress);
            mRegisterJSON.put("email", email);
            mRegisterJSON.put("password", pwd);
            new RegisterAsyncTask().execute(url.toString());

        } catch (JSONException e) {
            Toast.makeText(this, "Invalid Registration!!!!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private class RegisterAsyncTask extends AsyncTask<String, Void, String> {
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

                    wr.write(mRegisterJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to register, Reason: "
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
            if (s.startsWith("Unable to register")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .commit();
                    displayMainMenuPage();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not valid Registration"
                            , Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Invalid Registration!"
                        , Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * progress bar for loading process.
         * @param progress
         */
        @Override
        protected void onProgressUpdate(Void... progress) {
            mProgressBar = findViewById(R.id.register_progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(10);
        }

    }




}