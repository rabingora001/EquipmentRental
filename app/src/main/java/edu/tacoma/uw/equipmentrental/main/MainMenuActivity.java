package edu.tacoma.uw.equipmentrental.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;

/*
This is the dashboard page(MainMenuActivity) of our app.
 */
public class MainMenuActivity extends AppCompatActivity {

    private CircleImageView mCircleImageView;
    private TextView mTextName;
    private TextView mTextEmail;

    /*
    checks for login Status.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mTextName = findViewById(R.id.profile_name_id);
        mTextEmail = findViewById(R.id.profile_email_id);
        mCircleImageView = findViewById(R.id.profile_pic_id);
        checkLoginStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                    Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();

            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    mTextEmail.setText(email);
                    mTextName.setText(first_name + " " + last_name);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(MainMenuActivity.this).load(image_url).into(mCircleImageView);

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

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {
                mTextName.setText("");
                mTextEmail.setText("");
                mCircleImageView.setImageResource(0);
                Toast.makeText(MainMenuActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
                displaySignInActivityPage();
            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    public void displaySignInActivityPage(){
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }
}