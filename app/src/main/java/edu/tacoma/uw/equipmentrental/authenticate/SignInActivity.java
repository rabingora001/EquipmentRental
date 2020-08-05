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
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.home.HomePageActivity;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

/**
 * This is the main luncher activity for this project.
 */
public class SignInActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener {

    //member variable for SharedPreferences
    private SharedPreferences mSharedPreferences;

    /*
    displays the SingInActivity with the LoginFragment on top of SignInActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //checks if already logged in with fb
        checkLoginStatus();

        /*
        check to see if user is already logged in using SharedPreferences.
        if logged in, go to MainMenuActivity. If not, display the login fragment.
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

    /*
    The declaration of signUp() form the LoginFragment.LoginFragmentListener.
    This will replace the LoginFragment fragment with RegisterFragment.
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
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        displayMainMenuPage();
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


}