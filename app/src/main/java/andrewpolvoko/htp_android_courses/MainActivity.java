package andrewpolvoko.htp_android_courses;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>>, RecyclerItemClickSupport.OnItemClickListener {
    private MyRecyclerAdapter mAdapter;
    private String mNewsFeedLink = "https://people.onliner.by/feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        getClass();
        RecyclerItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new MyRssLoader(this, mNewsFeedLink);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        if (data != null) {
            mAdapter.setData(data);
            /*String mToastMessage = "Load finished\n" + mNewsFeedLink + "\ndata.size() = " + data.size();
            Toast.makeText(this, mToastMessage, Toast.LENGTH_LONG).show();*/
        }
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        NewsItem item = mAdapter.getItem(position);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.newsLink));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void changeFeedLink(String link) {
        mNewsFeedLink = link;
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.people:
                changeFeedLink("https://people.onliner.by/feed");
                return true;
            case R.id.auto:
                changeFeedLink("https://auto.onliner.by/feed");
                return true;
            case R.id.tech:
                changeFeedLink("https://tech.onliner.by/feed");
                return true;
            case R.id.realt:
                changeFeedLink("https://realt.onliner.by/feed");
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
