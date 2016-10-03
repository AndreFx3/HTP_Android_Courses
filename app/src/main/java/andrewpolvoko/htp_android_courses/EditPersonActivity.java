package andrewpolvoko.htp_android_courses;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPersonActivity extends AppCompatActivity implements View.OnClickListener {
    EditText firstName;
    EditText lastName;
    Button savePersonButton;
    private long personId;
    private Uri personUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Контакт");

        firstName = (EditText) findViewById(R.id.firstNameTV);
        lastName = (EditText) findViewById(R.id.lastNameTV);
        savePersonButton = (Button) findViewById(R.id.save_person);
        savePersonButton.setOnClickListener(this);

        personId = getIntent().getExtras().getLong("id");
        personUri = PersonContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(personId)).build();

        Cursor cursor = getContentResolver().query(personUri, null, null, null, null);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(PersonContract.FIRST_NAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(PersonContract.LAST_NAME)));
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_person_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            getContentResolver().delete(personUri, null, null);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        ContentValues person = new ContentValues();
        person.put(PersonContract.FIRST_NAME, String.valueOf(firstName.getText()));
        person.put(PersonContract.LAST_NAME, String.valueOf(lastName.getText()));
        getContentResolver().update(personUri, person, null, null);
        finish();
    }
}
