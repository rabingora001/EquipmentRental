package edu.tacoma.uw.equipmentrental.chores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.tacoma.uw.equipmentrental.R;
import edu.tacoma.uw.equipmentrental.main.EquipmentAddFragment;
import edu.tacoma.uw.equipmentrental.main.EquipmentBrowsingActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentDetailFragment;
import model.Chore;
import model.Equipment;

public class ChoresBrowsingActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;
    private List<Chore> mChoreList;
    private RecyclerView mRecyclerView;



    private void launchChoreAddFragment() {
        ChoresAddFragment choreAddFragment = new ChoresAddFragment();
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, choreAddFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ChoresDetailActivity.class);
            intent.putExtra(ChoresDetailActivity.ADD_CHORE, true);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_browsing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChoreAddFragment();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.item_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mChoreList == null) {
//                new ChoresBrowsingActivity.ChoreTask().execute(getString(R.string.get_chore));
            }
        }
        else {
            Toast.makeText(this,
                    "No network connection available. Displaying locally stored data",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mChoreList != null) {
            mRecyclerView.setAdapter(new ChoresBrowsingActivity.SimpleItemRecyclerViewAdapter
                    (this, mChoreList, mTwoPane));
        }
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, CourseContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ChoresBrowsingActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ChoresBrowsingActivity mParentActivity;
        private final List<Chore> mValues;
        private final boolean mTwoPane;


        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chore item = (Chore) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
//                    arguments.putSerializable(ChoresDetailFragment.ARG_ITEM_ID, item);
                    ChoresDetailFragment fragment = new ChoresDetailFragment();
                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();

                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ChoresDetailActivity.class);
//                    intent.putExtra(ChoresDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
            }
        };


//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Course item = (Course) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(CourseDetailFragment.ARG_ITEM_ID, item.getmCourseId());
//                    CourseDetailFragment fragment = new CourseDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    intent.putExtra(CourseDetailFragment.ARG_ITEM_ID, item.getmCourseId());
//
//                    context.startActivity(intent);
//                }
//            }
//        };

        SimpleItemRecyclerViewAdapter(ChoresBrowsingActivity parent,
                                      List<Chore> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ChoresBrowsingActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ChoresBrowsingActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ChoresBrowsingActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getmChore());
            holder.mContentView.setText(mValues.get(position).getmAddr());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    private class ChoreTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to download the list of chores, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s,
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mChoreList = Chore.parseChoreJson(
                            jsonObject.getString("chore")); // changed "names" to "courses"


                    if (!mChoreList.isEmpty()) {
                        setupRecyclerView((RecyclerView) mRecyclerView);
                    }

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}