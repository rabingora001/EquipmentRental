package edu.tacoma.uw.equipmentrental.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentBrowsingActivity;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (AccessToken.getCurrentAccessToken() != null) {

            setContentView(R.layout.activity_home_page);
//        }
    }

    /*
//checks login status for facebook with currentToken.
// */
//    private void checkLoginStatus() {
//        if (AccessToken.getCurrentAccessToken() != null) {
//            displayMainMenuPage();
//        }
//    }


    public void displayBrowserPage(View view) {
        Intent intent = new Intent(this, EquipmentBrowsingActivity.class);
        startActivity(intent);
    }

//    /*
//checks the facebook login status with Access Token.
//If logged in loads the user profile.
// */
//    private void checkLoginStatus() {
//        if (AccessToken.getCurrentAccessToken() != null) {
//            loadUserProfile(AccessToken.getCurrentAccessToken());
//        }
//    }



}