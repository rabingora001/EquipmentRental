package edu.tacoma.uw.equipmentrental.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import org.json.JSONException;
import org.json.JSONObject;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;

/**
 * This is the dashboard page(MainMenuActivity) of our app.
 */
public class MainMenuActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextName;
    private TextView mTextEmail;

    /*
    checks for fb login status in this method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mTextName = findViewById(R.id.profile_name_id);
        mTextEmail = findViewById(R.id.profile_email_id);
        mImageView = findViewById(R.id.profile_pic_id);
        //checks for facebook login status.
        checkLoginStatus();
    }

    /*
    Creates the menu with menu_main.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    operates the logout functions from the MenuItem
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {

            //logout from shared preferences.
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                    Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();

            //logout from facebook
            if (AccessToken.getCurrentAccessToken() != null) {
                LoginManager.getInstance().logOut();
            }

            //display the SignInActivity.class.
            displaySignInActivityPage();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    loads the user profile from facebook account.
     */
    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+ id +"/picture?type=normal";

                    mTextEmail.setText(email);
                    mTextName.setText(first_name + " " + last_name);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(MainMenuActivity.this).load(image_url).into(mImageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id" );
        request.setParameters(parameters);
        request.executeAsync();
    }

    /*
    AccessToken Tracker for facebook login.
     */
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {
                mTextName.setText("");
                mTextEmail.setText("");
                mImageView.setImageResource(0);
                Toast.makeText(MainMenuActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    /*
    checks the facebook login status with Access Token.
    If logged in loads the user profile.
     */
    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    /*
    Displays the SignInActivity.class
     */
    public void displaySignInActivityPage(){
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }
}