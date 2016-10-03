package andrewpolvoko.htp_android_courses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public static final String MYDB_SQLITE = "mydb.sqlite";
    public static final int VERSION = 3;

    public MySqliteOpenHelper(Context context) {
        super(context, MYDB_SQLITE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonContract.CREATE_TABLE_SCRIPT);
        db.execSQL(PersonContract.FILL_TABLE_SCRIPT1);
        db.execSQL(PersonContract.FILL_TABLE_SCRIPT2);
        db.execSQL(PersonContract.FILL_TABLE_SCRIPT3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PersonContract.DROP_TABLE_SCRIPT);
        onCreate(db);
    }
}
