package com.example.projectmanagement.ui.projects;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.projectmanagement.AddProjectActivity;
import com.example.projectmanagement.DataBaseHelper;
import com.example.projectmanagement.R;

public class ProjectsFragment extends Fragment {
    DataBaseHelper mydb;
    TableLayout tl;
    ImageButton imgbtn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        mydb =new DataBaseHelper(getContext());
        imgbtn = (ImageButton) root.findViewById(R.id.addbutton);
        final Cursor res = mydb.getAllProjects();
        final int count = res.getCount();
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(),AddProjectActivity.class);
                startActivity(intent);
            }
        });
        if(count == 0)
        {
            Toast.makeText(getContext(),"no data found",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) root.findViewById(R.id.projects);
            TableRow row1 =  new TableRow(getContext());
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);
            TextView studid = new TextView(getContext());
            studid.setText(" ID ");
            studid.setTypeface(studid.getTypeface(), Typeface.BOLD);
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);
            TextView stname = new TextView(getContext());
            stname.setText("  Name  ");
            stname.setTypeface(stname.getTypeface(),Typeface.BOLD);
            stname.setBackgroundResource(R.drawable.cell_shape);
            stname.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(stname);
            TextView depart = new TextView(getContext());
            depart.setText(" TASK1 ");
            depart.setTypeface(depart.getTypeface(),Typeface.BOLD);
            depart.setBackgroundResource(R.drawable.cell_shape);
            depart.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart);
            TextView  ttam= new TextView(getContext());
            ttam.setText(" TASK2 ");
            ttam.setTypeface(ttam.getTypeface(),Typeface.BOLD);
            ttam.setBackgroundResource(R.drawable.cell_shape);
            ttam.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(ttam);
            TextView  taskee= new TextView(getContext());
            taskee.setText(" TASK3 ");
            taskee.setTypeface(taskee.getTypeface(),Typeface.BOLD);
            taskee.setBackgroundResource(R.drawable.cell_shape);
            taskee.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(taskee);
            tl.addView(row1);
            res.moveToFirst();
            do {
                TableRow row = new TableRow(getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5,0,5,5);
                row.setLayoutParams(lp);
                TextView tid = new TextView(getContext());
                tid.setText(res.getString(0));
                final String id = res.getString(0);
                tid.setBackgroundResource(R.drawable.cell_shape);
                tid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(tid);
                final TextView tname = new TextView(getContext());
                tname.setText(res.getString(1));
                //final String pname = res.getString(1);
                tname.setBackgroundResource(R.drawable.cell_shape);
                tname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(tname);
                TextView task1 = new TextView(getContext());
                task1.setText(res.getString(2));
                task1.setBackgroundResource(R.drawable.cell_shape);
                task1.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(task1);
                TextView task2 = new TextView(getContext());
                task2.setText(res.getString(3));
                task2.setBackgroundResource(R.drawable.cell_shape);
                task2.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(task2);
                TextView task3 = new TextView(getContext());
                task3.setText(res.getString(4));
                task3.setBackgroundResource(R.drawable.cell_shape);
                task3.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(task3);
                ImageButton minusBtn = new ImageButton(getContext());
                minusBtn.setImageResource(R.drawable.remove);
                minusBtn.setBackgroundResource(R.drawable.cell_shape);
                minusBtn.setScaleY(2f);
                minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRemoveproject(id);
                    }
                });
                row.addView(minusBtn);
                tl.addView(row);
            }while(res.moveToNext());
        }

        res.close();

        return root;
    }
    public void onRemoveproject(String id)
    {
        //ntent intent = new Intent(getActivity(),EmployeesFragment.class);

        int msg = mydb.removeProject(id);
        if(msg>0) {
            Toast.makeText(getContext(), "deleted", Toast.LENGTH_LONG).show();
            //startActivity(intent);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }
}
