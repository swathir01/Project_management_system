package com.example.projectmanagement;

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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.projectmanagement.R;

public class LeaderProject extends Fragment {
    DataBaseHelper mydb;
    TableLayout tl;

    public LeaderProject() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_leader_project, container, false);
        mydb = new DataBaseHelper(getContext());
        final String tename = getActivity().getIntent().getStringExtra("lpro");
        final Cursor res = mydb.getProject(tename);
        final int count = res.getCount();
        if (count == 0) {
            //Toast.makeText(getContext(), "no data found", Toast.LENGTH_LONG).show();
        } else {
            tl = (TableLayout) root.findViewById(R.id.pros);
            TableRow row1 = new TableRow(getContext());
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5, 0, 5, 5);
            row1.setLayoutParams(lp1);
            TextView studid = new TextView(getContext());
            studid.setText(" Project ID ");
            studid.setTypeface(studid.getTypeface(), Typeface.BOLD);
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);
            TextView stname = new TextView(getContext());
            stname.setText("Deadline");
            stname.setTypeface(stname.getTypeface(), Typeface.BOLD);
            stname.setBackgroundResource(R.drawable.cell_shape);
            stname.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(stname);
            TextView dishonor = new TextView(getContext());
            dishonor.setText("Status");
            dishonor.setTypeface(dishonor.getTypeface(), Typeface.BOLD);
            dishonor.setBackgroundResource(R.drawable.cell_shape);
            dishonor.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(dishonor);
            tl.addView(row1);
            do {
                TableRow row = new TableRow(getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5, 0, 5, 5);
                row.setLayoutParams(lp);
                TextView sid = new TextView(getContext());
                sid.setText(res.getString(0));
                final String id = res.getString(0);
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);
                final TextView sname = new TextView(getContext());
                sname.setText(res.getString(2));
                final String ename = res.getString(2);
                sname.setBackgroundResource(R.drawable.cell_shape);
                sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sname);
                TextView dept = new TextView(getContext());
                dept.setText(res.getString(3));
                dept.setBackgroundResource(R.drawable.cell_shape);
                dept.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept);
                ImageButton editbtn = new ImageButton(getContext());
                editbtn.setImageResource(R.drawable.addbutton);
                editbtn.setBackgroundResource(R.drawable.cell_shape);
                editbtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                editbtn.setScaleY(2f);
                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity().getApplication(), TaskAssign.class);
                        intent.putExtra("pid", id);
                        intent.putExtra("team",tename);
                        intent.putExtra("dead",ename);
                        startActivity(intent);
                    }
                });
                row.addView(editbtn);
                tl.addView(row);
            } while (res.moveToNext());
        }
        res.close();
        return root;
    }
}