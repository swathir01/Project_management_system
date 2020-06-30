package com.example.projectmanagement.ui.employees;

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

import com.example.projectmanagement.AddActivity;
import com.example.projectmanagement.DataBaseHelper;
import com.example.projectmanagement.EditActivity;
import com.example.projectmanagement.R;

public class EmployeesFragment extends Fragment {
    DataBaseHelper mydb;
    TableLayout tl;
    ImageButton imgbtn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_employees, container, false);
        mydb =new DataBaseHelper(getContext());
        imgbtn = (ImageButton) root.findViewById(R.id.addbutton);
        final Cursor res = mydb.getAllData();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(getContext(),"no data found",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) root.findViewById(R.id.employees);
            TableRow row1 = new TableRow(getContext());
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
            depart.setText(" DESIGN. ");
            depart.setTypeface(depart.getTypeface(),Typeface.BOLD);
            depart.setBackgroundResource(R.drawable.cell_shape);
            depart.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart);
            TextView  ttam= new TextView(getContext());
            ttam.setText(" TEAM");
            ttam.setTypeface(ttam.getTypeface(),Typeface.BOLD);
            ttam.setBackgroundResource(R.drawable.cell_shape);
            ttam.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(ttam);
            TextView tle= new TextView(getContext());
            tle.setText("LEADER");
            tle.setTypeface(tle.getTypeface(),Typeface.BOLD);
            tle.setBackgroundResource(R.drawable.cell_shape);
            tle.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(tle);
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
                final String ename = res.getString(1);
                tname.setBackgroundResource(R.drawable.cell_shape);
                tname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(tname);
                TextView tdes = new TextView(getContext());
                tdes.setText(res.getString(2));
                tdes.setBackgroundResource(R.drawable.cell_shape);
                tdes.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(tdes);
                TextView ttn = new TextView(getContext());
                ttn.setText(res.getString(3));
                ttn.setBackgroundResource(R.drawable.cell_shape);
                ttn.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(ttn);
                TextView ttl = new TextView(getContext());
                ttl.setText(res.getString(4));
                ttl.setBackgroundResource(R.drawable.cell_shape);
                ttl.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(ttl);
                ImageButton editbtn = new ImageButton(getContext());
                editbtn.setImageResource(R.drawable.edit);
                editbtn.setBackgroundResource(R.drawable.cell_shape);
                editbtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                editbtn.setScaleY(2f);
                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity().getApplication(), EditActivity.class);
                        intent.putExtra("empid",id);
                        intent.putExtra("name",ename);
                        startActivity(intent);
                    }
                });
                row.addView(editbtn);
                ImageButton minusBtn = new ImageButton(getContext());
                minusBtn.setImageResource(R.drawable.remove);
                minusBtn.setBackgroundResource(R.drawable.cell_shape);
                minusBtn.setScaleY(2f);
                minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRemove(id);
                    }
                });
                row.addView(minusBtn);
                tl.addView(row);
            }while(res.moveToNext());
        }
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddActivity.class);
                startActivity(intent);
            }
        });
        res.close();
        return root;
    }
    public void onRemove(String id)
    {
        //ntent intent = new Intent(getActivity(),EmployeesFragment.class);

        int msg = mydb.removeData(id);
        if(msg>0) {
            Toast.makeText(getContext(), "deleted", Toast.LENGTH_LONG).show();
            //startActivity(intent);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }
    
}

