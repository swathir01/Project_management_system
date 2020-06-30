package com.example.projectmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Management.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS employee");
        db.execSQL("CREATE TABLE IF NOT EXISTS assignPro (projectid VARCHAR,teamname VARCHAR,deadline VARCHAR,completion VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS employee (empid INTEGER PRIMARY KEY AUTOINCREMENT, empname VARCHAR,designation VARCHAR,teamname VARCHAR,teamleader VARCHAR)");
        db.execSQL("CREATE TABLE projects (projectid VARCHAR PRIMARY KEY, proname VARCHAR,task1 VARCHAR,task2 VARCHAR,task3 VARCHAR)");
        db.execSQL("CREATE TABLE registuser (empid VARCHAR PRIMARY KEY, emailid VARCHAR,phnno VARCHAR,pwd VARCHAR)");
        db.execSQL("CREATE TABLE taskassign (projectid VARCHAR,proname VARCHAR, empid VARCHAR,taskid VARCHAR,deadline VARCHAR)");
        db.execSQL("CREATE TABLE updatestatus (empid VARCHAR,projectid VARCHAR, teamname VARCHAR,taskid VARCHAR,taskcom VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public boolean insertData(String name, String desg, String tname, String tlead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("empname",name);
        contentValues.put("designation",desg);
        contentValues.put("teamname",tname);
        contentValues.put("teamleader",tlead);
        long result = db.insert("employee",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public String getlastid()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM employee ORDER BY empid DESC LIMIT 1",null);
        String str = "";
        if(res.moveToFirst())
            str  =  res.getString(0);
        res.close();
        return str;
    }
    public Cursor getlead(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM employee WHERE empid = "+id,null);
        res.moveToFirst();
        return  res;
    }
    public Cursor getProject(String tname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM assignPro WHERE teamname = '"+tname+"'",null);
        res.moveToFirst();
        return res;
    }
    public Cursor getiddata(String eid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM taskassign WHERE empid = "+eid,null);
        res.moveToFirst();
        return res;
    }
    public Cursor getidinfo(String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM assignPro WHERE projectid = '"+pid+"'",null);
        res.moveToFirst();
        return res;
    }
    public Cursor getidMembers(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM employee WHERE teamname ='"+name+"'",null);
        res.moveToFirst();
        return res;
    }
    public boolean insertProjects(String id, String name, String task1, String task2, String task3)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("projectid",id);
        contentValues.put("proname",name);
        contentValues.put("task1",task1);
        contentValues.put("task2",task2);
        contentValues.put("task3",task3);
        long result = db.insert("projects",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean insertassign(String id, String name, String empid, String tid, String dead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("projectid",id);
        contentValues.put("proname",name);
        contentValues.put("empid",empid);
        contentValues.put("taskid",tid);
        contentValues.put("deadline",dead);
        long result = db.insert("taskassign",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean insertStatus(String eid, String pid, String team, String tid, String tcom)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("empid",eid);
        contentValues.put("projectid",pid);
        contentValues.put("teamname",team);
        contentValues.put("taskid",tid);
        contentValues.put("taskcom",tcom);
        long result = db.insert("updatestatus",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean assignProject(String id, String team, String deadline, String completion)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("projectid",id);
        contentValues.put("teamname",team);
        contentValues.put("deadline",deadline);
        contentValues.put("completion",completion);
        long result = db.insert("assignPro",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean updateData(String id, String name, String desg, String tname, String tlead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("empid",id);
        contentValues.put("empname",name);
        contentValues.put("designation",desg);
        contentValues.put("teamname",tname);
        contentValues.put("teamleader",tlead);
        db.update("employee",contentValues,"empid = ?",new String[]{id});
        return true;
    }
    /*public boolean updateProject(String id, String name, String task1, String task2, String tlead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("empid",id);
        contentValues.put("empname",name);
        contentValues.put("designation",desg);
        contentValues.put("teamname",tname);
        contentValues.put("teamleader",tlead);
        db.update("employee",contentValues,"empid = ?",new String[]{id});
        return true;
    }*/
    public boolean updatestatus(String status,String tid,String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM updatestatus WHERE projectid ='"+pid+"' AND taskid = '"+tid+"'",null);
        if(result.getCount()!=0)
        {
            result.moveToFirst();
            updateall(status,tid,pid);

        }
        return true;
    }
    public boolean updatetotal(String status,String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM assignPro WHERE projectid ='"+pid+"'",null);
        if(result.getCount()!=0)
        {
            result.moveToFirst();
            int stat = Integer.parseInt(result.getString(3));
            int st =stat+Integer.parseInt(status);
            String star = String.valueOf(st);
            updatetot(star,pid);
        }
        return true;
    }
    public boolean updatetot(String tot,String proid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("completion",tot);
        db.update("assignPro",contentValues,"projectid = ?",new String[]{proid});
        return true;
    }
    public boolean updateall(String status, String tid, String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskcom",status);
        db.update("updatestatus",contentValues,"projectid = ? AND taskid = ?",new String[]{pid,tid});
        updatetotal(status,pid);
        return true;
    }
    public Cursor getplist()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM assignPro",null);
        res.moveToFirst();
        return res;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM employee",null);
        return res;
    }
    public String getProname(String proname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM projects WHERE projectid = '"+proname+"'",null);
        res.moveToFirst();
        return res.getString(1);
    }
    public Cursor getAllAssigns()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM assignPro",null);
        return res;
    }
    public Cursor getAllProjects()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM projects",null);
        return result;
    }
    public int removeData(String empid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("employee","empid = ?",new String[]{empid});
    }
    public int removeAssign(String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("assignPro","projectid = ?",new String[]{pid});
    }
    public int removeProject(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("projects","projectid = ?",new String[]{id});
    }
    public boolean addUser(String username, String email,String phn,String pass){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("empid",username);
        contentValues.put("emailid",email);
        contentValues.put("phnno",phn);
        contentValues.put("pwd",pass);
        long result = db.insert("registuser",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean checkId(String user)
    {
        String TABLE = "employee";
        String COL1 = "empid";
        String[] column = { COL1 };
        SQLiteDatabase db = getReadableDatabase();
        String select = COL1 + "=?";
        String[] selectionArg = { user };
        Cursor cursor = db.query(TABLE,column,select,selectionArg,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean checkUser(String username, String password){

        final String TABLE_NAME ="registuser";
        final String COL_1 ="empid";
        final String COL_4 ="pwd";
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_1 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public List<String> getAllLabels(){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  projects" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding 1st column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    public List<String> getAllTasks(String id){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  projects WHERE projectid ='"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));
                list.add(cursor.getString(4));//adding 1st column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    public List<String> getidTasks(String id){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM taskassign WHERE empid ="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(3));//adding 1st column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    public List<String> getAllEmps(String name){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  employee WHERE teamname ='"+name+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding 1st column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
}
