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
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailActivity;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;
import model.Equipment;

/**
 * This is the main luncher activity for this project.
 */
public class SignInActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener {

    private SharedPreferences mSharedPreferences;
    private JSONObject mLoginJSON;

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
            //lunch the login fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.sign_in_fragment_id, new LoginFragment())
                    .commit();
        } else {
            displayMainMenuPage();
        }
    }

    /**
     * The declaration of signUp() form the LoginFragment.LoginFragmentListener.
     * This will replace the LoginFragment fragment with RegisterFragment.
     */
    @Override
    public void signUp() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.sign_in_fragment_id, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    /*
     method declaration of RegisterFragment.RegisterFragmentListener
     */
    @Override
    public void registerSubmit(String firstName, String lastName, String username, String email, String pwd) {
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        displayMainMenuPage();
    }

    /*
    The declaration of login() from the LoginFragment.LoginFragmentListener.
    displays the MainMenuActivity.class activity.
     */
    @Override
    public void login(String email, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.login_url));

        mLoginJSON = new JSONObject();
        try {
            mLoginJSON.put("email", email);
            mLoginJSON.put("password", pwd);
            new LoginAsyncTask().execute(url.toString());


//            new EquipmentDetailActivity.AddEquipmentAsyncTask().execute(url.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Invalid Login: "
                            + e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }


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

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
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
            if (s.startsWith("Unable to login")) {
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .commit();
                    displayMainMenuPage();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Login"
                            , Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Invalid Login"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }


}