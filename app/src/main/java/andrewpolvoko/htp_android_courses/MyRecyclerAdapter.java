package andrewpolvoko.htp_android_courses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<Student> studentsList;

    public MyRecyclerAdapter(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View student = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_list_row, parent, false);
        return new MyViewHolder(student);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Student student = studentsList.get(position);
        holder.firstName.setText(student.firstName);
        holder.lastName.setText(student.lastName);
        holder.letter.setText(student.firstName.substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView letter, firstName, lastName;

        public MyViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.letterTV);
            firstName = (TextView) itemView.findViewById(R.id.firstNameTV);
            lastName = (TextView) itemView.findViewById(R.id.lastNameTV);
        }
    }
}
