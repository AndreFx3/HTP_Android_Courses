package andrewpolvoko.htp_android_courses;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyCursorAdapter extends CursorRecyclerViewAdapter<MyCursorAdapter.PersonViewHolder> {
    public MyCursorAdapter(Context context) {
        super(context, null);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person, parent, false));
    }

    @Override
    public void onBindViewHolder(PersonViewHolder viewHolder, Cursor cursor) {

        int firstNameIndex = cursor.getColumnIndex(PersonContract.FIRST_NAME);
        int lastNameIndex = cursor.getColumnIndex(PersonContract.LAST_NAME);
        viewHolder.firstName.setText(cursor.getString(firstNameIndex));
        viewHolder.lastName.setText(cursor.getString(lastNameIndex));
        if (cursor.getString(firstNameIndex).length() > 0)
            viewHolder.letter.setText(cursor.getString(firstNameIndex).substring(0, 1));
        else
            viewHolder.letter.setText("");
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView letter, firstName, lastName;

        public PersonViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.letterTV);
            firstName = (TextView) itemView.findViewById(R.id.firstNameTV);
            lastName = (TextView) itemView.findViewById(R.id.lastNameTV);
        }
    }
}
