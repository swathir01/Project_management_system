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

public class HomeFragment extends Fragment {
    DataBaseHelper mydb;
    TableLayout tl;
    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home_fragment, container, false);
        mydb =new DataBaseHelper(getContext());
        String tname = HomeActivity.getteam();
        Toast.makeText(getContext()," "+tname,Toast.LENGTH_LONG).show();
        final Cursor res = mydb.getidMembers(tname);
        final int count = res.getCount();
        if(count == 0)
        {
            //Toast.makeText(getContext(),"no data found",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) root.findViewById(R.id.members);
            TableRow row1 = new TableRow(getContext());
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);
            TextView studid = new TextView(getContext());
            studid.setText(" ID ");
            studid.setTypeface(studid.getTypeface(),Typeface.BOLD);
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
            depart.setText("DESIGNATION");
            depart.setTypeface(depart.getTypeface(),Typeface.BOLD);
            depart.setBackgroundResource(R.drawable.cell_shape);
            depart.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart);
            tl.addView(row1);
            do {
                TableRow row = new TableRow(getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5,0,5,5);
                row.setLayoutParams(lp);
                TextView sid = new TextView(getContext());
                sid.setText(res.getString(0));
                final String id = res.getString(0);
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);
                final TextView sname = new TextView(getContext());
                sname.setText(res.getString(1));
                //final String ename = res.getString(1);
                sname.setBackgroundResource(R.drawable.cell_shape);
                sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sname);
                TextView dept = new TextView(getContext());
                dept.setText(res.getString(2));
                dept.setBackgroundResource(R.drawable.cell_shape);
                dept.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept);
                tl.addView(row);
            }while(res.moveToNext());
        }
        res.close();
        return root;
    }

}
