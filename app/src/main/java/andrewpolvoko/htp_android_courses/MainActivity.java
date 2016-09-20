package andrewpolvoko.htp_android_courses;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Student>> {
    private List<Student> studentsList = new ArrayList<>();
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new MyRecyclerAdapter(studentsList);
        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<List<Student>> onCreateLoader(int id, Bundle args) {
        return new StudentsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> data) {
        adapter.setStudentsList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {
        adapter.setStudentsList(new ArrayList<Student>());
    }
}
