package andrewpolvoko.htp_android_courses;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsLoader extends AsyncTaskLoader<List<Student>> {

    public StudentsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Student> loadInBackground() {
        List<Student> studentsList = new ArrayList<>();
        InputStream in = getContext().getResources().openRawResource(R.raw.students);
        String jsonStr = convertStreamToString(in);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                studentsList.add(new Student(obj.getString("id"), obj.getString("firstName"), obj.getString("lastName")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(studentsList);
        return studentsList;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
