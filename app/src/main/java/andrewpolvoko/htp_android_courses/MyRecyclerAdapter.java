package andrewpolvoko.htp_android_courses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.myViewHolder> {
    private List<Student> studentsList;

    public MyRecyclerAdapter(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public MyRecyclerAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View student = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_list_row, parent, false);
        return new myViewHolder(student);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.myViewHolder holder, int position) {
        Student student = studentsList.get(position);
        holder.firstName.setText(student.getFirstName());
        holder.lastName.setText(student.getLastName());
        holder.letter.setText(student.getFirstName().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        public TextView letter, firstName, lastName;

        public myViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.letterTV);
            firstName = (TextView) itemView.findViewById(R.id.firstNameTV);
            lastName = (TextView) itemView.findViewById(R.id.lastNameTV);
        }
    }
}
