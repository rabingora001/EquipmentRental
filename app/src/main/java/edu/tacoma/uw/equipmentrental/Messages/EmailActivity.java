package edu.tacoma.uw.equipmentrental.Messages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.authenticate.LoginFragment;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.emailActivity_fragmentPlaceholder_id, new EmailFragment())
                .commit();
    }
}