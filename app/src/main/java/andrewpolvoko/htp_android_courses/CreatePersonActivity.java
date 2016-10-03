package andrewpolvoko.htp_android_courses;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePersonActivity extends AppCompatActivity implements View.OnClickListener {
    EditText firstName;
    EditText lastName;
    Button savePersonButton;

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
    }

    @Override
    public void onClick(View view) {
        ContentValues person = new ContentValues();
        person.put(PersonContract.FIRST_NAME, String.valueOf(firstName.getText()));
        person.put(PersonContract.LAST_NAME, String.valueOf(lastName.getText()));
        getContentResolver().insert(PersonContract.CONTENT_URI, person);
        finish();
    }
}
