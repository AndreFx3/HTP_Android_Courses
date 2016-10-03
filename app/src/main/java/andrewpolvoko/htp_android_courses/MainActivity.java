package andrewpolvoko.htp_android_courses;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, RecyclerItemClickSupport.OnItemClickListener {
    private MyCursorAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Контакты");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyCursorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        getClass();
        RecyclerItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, PersonContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(this, EditPersonActivity.class);
        intent.putExtra("id", mAdapter.getItemId(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(this, CreatePersonActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
