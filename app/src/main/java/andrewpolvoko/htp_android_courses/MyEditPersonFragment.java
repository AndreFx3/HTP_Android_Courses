package andrewpolvoko.htp_android_courses;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MyEditPersonFragment extends Fragment implements View.OnClickListener {
    EditText firstName;
    EditText lastName;
    Button savePersonButton;
    private long personId;
    private Uri personUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_edit_person, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Контакт");
        setHasOptionsMenu(true);

        firstName = (EditText) rootView.findViewById(R.id.firstNameTV);
        lastName = (EditText) rootView.findViewById(R.id.lastNameTV);
        savePersonButton = (Button) rootView.findViewById(R.id.save_person);
        savePersonButton.setOnClickListener(this);

        personId = getArguments().getLong("id");
        personUri = PersonContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(personId)).build();

        Cursor cursor = getActivity().getContentResolver().query(personUri, null, null, null, null);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(PersonContract.FIRST_NAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(PersonContract.LAST_NAME)));
        cursor.close();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_person_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            getActivity().getContentResolver().delete(personUri, null, null);
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        ContentValues person = new ContentValues();
        person.put(PersonContract.FIRST_NAME, String.valueOf(firstName.getText()));
        person.put(PersonContract.LAST_NAME, String.valueOf(lastName.getText()));
        getActivity().getContentResolver().update(personUri, person, null, null);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
