package andrewpolvoko.htp_android_courses;

import android.net.Uri;
import android.provider.BaseColumns;

public class PersonContract implements BaseColumns {

    public static final String TABLE_NAME = "Person";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(MyContentProvider.AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE = "phone";

    public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " " +
            "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + PHONE + " TEXT);";

    public static final String DROP_TABLE_SCRIPT = "DROP TABLE " + TABLE_NAME;
    public static final String FILL_TABLE_SCRIPT1 =
            "INSERT INTO Person (_id,firstName,lastName) VALUES (1, 'Thomas','Johansson');";
    public static final String FILL_TABLE_SCRIPT2 =
            "INSERT INTO Person (_id,firstName,lastName) VALUES (2, 'Andrew','Marshall');";
    public static final String FILL_TABLE_SCRIPT3 =
            "INSERT INTO Person (_id,firstName,lastName) VALUES (3, 'Byron','Black');";

}
