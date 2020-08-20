package edu.tacoma.uw.equipmentrental.chores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.equipmentrental.Messages.EmailActivity;
import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.EquipmentAddFragment;
import edu.tacoma.uw.equipmentrental.main.EquipmentBrowsingActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailFragment;
import model.Chore;
import model.Equipment;

public class ChoresDetailActivity extends AppCompatActivity
        implements ChoresAddFragment.AddListener{

        public static final String ADD_CHORE = "ADD_CHORE";
        private JSONObject mChoreJSON;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    displayEmailPage();



//                Snackbar.make(view, "Message the renter. don't do anything for now", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                }
            });

            // Show the Up button in the action bar.
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            // savedInstanceState is non-null when there is fragment state
            // saved from previous configurations of this activity
            // (e.g. when rotating the screen from portrait to landscape).
            // In this case, the fragment will automatically be re-added
            // to its container so we don"t need to manually add it.
            // For more information, see the Fragments API guide at:
            //
            // http://developer.android.com/guide/components/fragments.html
            //
            if (savedInstanceState == null) {
                // Create the detail fragment and add it to the activity
                // using a fragment transaction.
                Bundle arguments = new Bundle();
                if (getIntent().getSerializableExtra(ChoresDetailFragment.ARG_ITEM_ID) != null) {
                    arguments.putSerializable(ChoresDetailFragment.ARG_ITEM_ID,
                            getIntent().getSerializableExtra(ChoresDetailFragment.ARG_ITEM_ID));
                    ChoresDetailFragment fragment = new ChoresDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.item_detail_container, fragment)
                            .commit();
                } else if (getIntent().getBooleanExtra(ChoresDetailActivity.ADD_CHORE, false)) {
                    ChoresAddFragment fragment = new ChoresAddFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.item_detail_container, fragment)
                            .commit();
                }
            }
        }

        /**
         * This method starts the EmailActivity.class
         */
        public void displayEmailPage() {
            Intent i = new Intent(this, EmailActivity.class);
            startActivity(i);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {

                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                navigateUpTo(new Intent(this, ChoresBrowsingActivity.class));

                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void addChore(Chore chore) {
            StringBuilder url = new StringBuilder(getString(R.string.chore_url));

            mChoreJSON = new JSONObject();
            try {
                mChoreJSON.put(Chore.CHORE, chore.getmChore());
                mChoreJSON.put(Chore.ADDRESS, chore.getmAddr());
                mChoreJSON.put(Chore.LONGDESC, chore.getmLongDesc());
                mChoreJSON.put(Chore.PRICE, chore.getmPrice());
                mChoreJSON.put(Chore.EMAiL, chore.getmEmail());
                new ChoresDetailActivity.AddChoreAsyncTask().execute(url.toString());
            } catch (JSONException e) {
                Toast.makeText(this, "Error with JSON creation on adding chores: "
                                + e.getMessage()
                        , Toast.LENGTH_SHORT).show();
            }
        }

        private class AddChoreAsyncTask extends AsyncTask<String, Void, String> {
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

                        // For Debugging
                        Log.i(ADD_CHORE, mChoreJSON.toString());
                        wr.write(mChoreJSON.toString());
                        wr.flush();
                        wr.close();

                        InputStream content = urlConnection.getInputStream();

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                        String s = "";
                        while ((s = buffer.readLine()) != null) {
                            response += s;
                        }
                    } catch (Exception e) {
                        response = "Unable to add new chore, Reason: "
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
                if (s.startsWith("Unable to add new chore")) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(getApplicationContext(), "Chore Added successfully"
                                , Toast.LENGTH_SHORT).show();


                        displayChoreBrowsingPage();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), " couldn't be added: "
                                        + jsonObject.getString("error")
                                , Toast.LENGTH_LONG).show();
                        Log.e(ADD_CHORE, jsonObject.getString("error"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding chore"
                                    + e.getMessage()
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_CHORE, e.getMessage());
                }
            }
        }

        public void displayChoreBrowsingPage() {
            Intent i = new Intent(this, ChoresBrowsingActivity.class);
            startActivity(i);
        }
}