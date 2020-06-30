package com.example.projectmanagement.ui.database;

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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectmanagement.DataBaseHelper;
import com.example.projectmanagement.AssignActivity;
import com.example.projectmanagement.R;

public class ViewDatabase extends Fragment {
    DataBaseHelper mydb;
    TableLayout tl;
    ImageButton imgbtn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewdatabase, container, false);
        mydb =new DataBaseHelper(getContext());
        final Cursor res = mydb.getAllAssigns();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(getContext(),"no data found",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) root.findViewById(R.id.view_database);
            TableRow row1 = new TableRow(getContext());
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);
            TextView  ttam= new TextView(getContext());
            ttam.setText("PROJECT ID");
            ttam.setTypeface(ttam.getTypeface(), Typeface.BOLD);
            ttam.setBackgroundResource(R.drawable.cell_shape);
            ttam.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(ttam);
            TextView  teame= new TextView(getContext());
            teame.setText("Team");
            teame.setTypeface(teame.getTypeface(), Typeface.BOLD);
            teame.setBackgroundResource(R.drawable.cell_shape);
            teame.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(teame);
            TextView  dee= new TextView(getContext());
            dee.setText(" DEADLINE ");
            dee.setTypeface(dee.getTypeface(), Typeface.BOLD);
            dee.setBackgroundResource(R.drawable.cell_shape);
            dee.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(dee);
            TextView total= new TextView(getContext());
            total.setText("STATUS");
            total.setTypeface(total.getTypeface(), Typeface.BOLD);
            total.setBackgroundResource(R.drawable.cell_shape);
            total.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(total);
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
                tname.setBackgroundResource(R.drawable.cell_shape);
                tname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(tname);
                TextView deadline = new TextView(getContext());
                deadline.setText(res.getString(2));
                deadline.setBackgroundResource(R.drawable.cell_shape);
                deadline.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(deadline);
                TextView comple = new TextView(getContext());
                comple.setText(res.getString(3));
                comple.setBackgroundResource(R.drawable.cell_shape);
                comple.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(comple);
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
        return root;
    }

    public void onRemove(String id)
    {
        //ntent intent = new Intent(getActivity(),EmployeesFragment.class);

        int msg = mydb.removeAssign(id);
        if(msg>0) {
            Toast.makeText(getContext(), "deleted", Toast.LENGTH_LONG).show();
            //startActivity(intent);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }

}
