package andrewpolvoko.htp_android_courses;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MyCreatePersonFragment extends Fragment implements View.OnClickListener {
    EditText firstName;
    EditText lastName;
    Button savePersonButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_edit_person, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Контакт");

        firstName = (EditText) rootView.findViewById(R.id.firstNameTV);
        lastName = (EditText) rootView.findViewById(R.id.lastNameTV);
        savePersonButton = (Button) rootView.findViewById(R.id.save_person);
        savePersonButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        ContentValues person = new ContentValues();
        person.put(PersonContract.FIRST_NAME, String.valueOf(firstName.getText()));
        person.put(PersonContract.LAST_NAME, String.valueOf(lastName.getText()));
        getActivity().getContentResolver().insert(PersonContract.CONTENT_URI, person);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
