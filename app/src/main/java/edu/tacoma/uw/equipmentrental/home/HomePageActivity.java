package edu.tacoma.uw.equipmentrental.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentAddFragment;
import edu.tacoma.uw.equipmentrental.main.EquipmentBrowsingActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailActivity;
import edu.tacoma.uw.equipmentrental.main.MainMenuActivity;
import edu.tacoma.uw.equipmentrental.maps.MapsActivity;

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

    /**
     * This method starts the MainMenuActivity.class
     * @param view
     */
    public void displayMainMenuPage(View view) {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    /**
     * This method starts the Equipment Detail Activity
     * @param view
     */
    public void displayEquipmentAddFragement(View view) {
        launchEquipmentAddFragment();
    }


    /**
     * This method starts the MapsActivity.class
     * @param view
     */
    public void displayMapPage(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }



    private void launchEquipmentAddFragment() {
        EquipmentAddFragment equipmentAddFragment = new EquipmentAddFragment();

            Intent intent = new Intent(this, EquipmentDetailActivity.class);
            intent.putExtra(EquipmentDetailActivity.ADD_EQUIPMENT, true);
            startActivity(intent);

    }


}