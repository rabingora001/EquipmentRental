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
import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

/**
 * This is the main luncher activity for this project.
 */
public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener {

    //member variable for SharedPreferences
    private SharedPreferences mSharedPreferences;

    /*
    displays the SingInActivity with the LoginFragment on top of SignInActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setTitle("Welcome to Equipment Rental");

        /*
        check to see if user is already loggedin using SharedPreferences.
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
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /*
    The declaration of login() from the LoginFragment.LoginFragmentListener.
     */
    @Override
    public void login(String email, String pwd) {
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    The decleration of signUp() form the LoginFragment.LoginFragmentListener.
     */
    @Override
    public void signUp() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    /*
    The method to help load Activity from the LoginFragment.
    because Fragment alone cannot display Activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getSupportFragmentManager().getFragments()) {

            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}