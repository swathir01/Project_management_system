package com.example.projectmanagement.ui.status;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.projectmanagement.DataBaseHelper;
import com.example.projectmanagement.R;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class ViewStatus extends Fragment {
    DataBaseHelper mydb;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_viewstatus, container, false);
        mydb =new DataBaseHelper(getContext());
        Cursor result = mydb.getplist();
        if(result.getCount()==0)
        {
            Toast.makeText(getContext(),"No Project Assigned!",Toast.LENGTH_LONG).show();
        }
        else {
            String[] plist = new String[result.getCount()];
            int k=0;
            String[] tlist = new String[result.getCount()];
            do{
                plist[k]=result.getString(0);
                tlist[k]=result.getString(3);
                k++;
            }while(result.moveToNext());
            result.close();
            CreatePieChart(plist,tlist);
        }

        return root;
    }
    private void CreatePieChart(String[] code,String[] val)
    {
        // Pie Chart Section Names
        // String[] code = new String[] { "IOS", "ANDROID" };
        double[] distribution = new  double[val.length];
        // Pie Chart Section Value
        for(int i=0;i<val.length;i++)
        {
         distribution[i]=Double.parseDouble(val[i]);
        }

        // Color of each Pie Chart Sections
        int[] colors = { Color.RED,Color.CYAN,Color.YELLOW,Color.MAGENTA };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Projects Status");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
        defaultRenderer.setLegendTextSize(60);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLabelsTextSize(50);
        defaultRenderer.setChartTitle("Project Analysis");
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setBackgroundColor(Color.TRANSPARENT);

        // Creating an intent to plot bar chart using dataset and
        // multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getContext(),
                distributionSeries, defaultRenderer, "Analysis");

        // Start Activity
        startActivity(intent);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.activity_main, menu);
    }
}
