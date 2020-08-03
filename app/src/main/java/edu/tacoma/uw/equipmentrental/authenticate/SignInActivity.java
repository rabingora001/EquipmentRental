package edu.tacoma.uw.equipmentrental.authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener {

    //member variable for SharedPreferences
    private SharedPreferences mSharedPreferences;

//    fb
    private LoginButton mFbLoginButton;

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFbLoginButton = findViewById(R.id.fb_sign_in_btn_id1);

        mCallbackManager = CallbackManager.Factory.create();
        mFbLoginButton.setPermissions(Arrays.asList("email", "public_profile"));

        checkLoginStatus();

//         Callback registration
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                displayMainMenuPage();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

        });



        //check to see if user is already loggedin using SharedPreferences.
        //if logged in, go to MainMenuActivity,
        //if not logged in, bring the login fragment.

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);

//        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
//            //lunch the login fragment
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.sign_in_fragment_id, new LoginFragment())
//                    .commit();
//        } else {
//            Intent intent = new Intent(this, MainMenuActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void displayMainMenuPage() {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
//        finish();
    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            displayMainMenuPage();
        }
    }
}