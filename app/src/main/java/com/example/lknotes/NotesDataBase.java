package com.example.lknotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NotesDataBase extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "NotesDB";
    private  static final int DATABASE_VERSION = 1;
    private static String Table_Name;
    private static final String NOTE_ID = "Note_ID";
    private static final String NOTE_TITLE = "Note_Title";
    private static final String NOTE_DATA = "Note_Data";


    public NotesDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        loadUID();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + " (" +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT, " +
                NOTE_DATA + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        loadUID();

        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    void saveNote(String title, String data, FragmentManager fragmentManager){
           loadUID();

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(NOTE_TITLE, title);
            contentValues.put(NOTE_DATA, data);

            long result = database.insert(Table_Name, NOTE_TITLE, contentValues);
            if (result == -1) {
                Toast.makeText(context, "Failed to save !!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Note Saved!!", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainFragment());
                fragmentTransaction.commit();
            }
    }


    Cursor readAllData(){
        loadUID();

        SQLiteDatabase databasew = this.getWritableDatabase();  // Explicitly open the database
        databasew.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + " (" +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT, " +
                NOTE_DATA + " TEXT)");

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + Table_Name;


        Cursor cursor = null;
        if(database!=null){
           cursor = database.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateNote(String id , String title, String note,FragmentManager fragmentManager){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOTE_TITLE,title);
        contentValues.put(NOTE_DATA,note);

        database.beginTransaction();
        long result = database.update(Table_Name,contentValues,  "Note_ID ="+id,null);
        database.setTransactionSuccessful();
        database.endTransaction();

//        Log.d("Update Result", "Rows affected: " + result); //debugging

       if(result==-1){
           Toast.makeText(context,"Failed to update",Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(context,"Successfully updated",Toast.LENGTH_SHORT).show();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container, new MainFragment());
           fragmentTransaction.commit();

       }

    }

    void deleteNote(String id){
        SQLiteDatabase database = getWritableDatabase();
        long result = database.delete(Table_Name,"Note_ID ="+id,null);
        if(result==-1){
            Toast.makeText(context,"Couldn't Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Note Deleted",Toast.LENGTH_SHORT).show();
        }


    }


    void loadUID(){
        SharedPreferences loggedInUser = context.getSharedPreferences("LoggedInUser",MODE_PRIVATE);
        Table_Name = loggedInUser.getString("currentUser",null);
    }
}
