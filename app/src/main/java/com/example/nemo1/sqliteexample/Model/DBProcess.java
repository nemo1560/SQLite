package com.example.nemo1.sqliteexample.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;

import java.util.ArrayList;
import java.util.List;

public class DBProcess extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "Work_note";
    //Table name
    private static final String TABLE_NAME = "Note";
    //Columns name
    private static final String COLUMN_NOTEID = "Note_ID";
    private static final String COLUMN_NOTE_TITLE = "Note_Title";
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";

    private SQLiteDatabase sqLiteDatabase;
    private SendData sendData;

    public DBProcess(@Nullable Context context,SendData sendData) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.sendData = sendData;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME +"("+ COLUMN_NOTEID +" INTEGER PRIMARY KEY,"+COLUMN_NOTE_TITLE+" TEXT,"+COLUMN_NOTE_CONTENT+" TEXT"+")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void addNote(Note note){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE_TITLE,note.getNoteTitle());
        contentValues.put(COLUMN_NOTE_CONTENT,note.getNoteContent());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        sendData.onSendResult("Added");
    }

    public void getNote(int Id){
        Note note = new Note();
        String[]column = {COLUMN_NOTEID,COLUMN_NOTE_TITLE,COLUMN_NOTE_CONTENT};
        String[]seletions = {String.valueOf(Id)};
        sqLiteDatabase = this.getReadableDatabase();
        //Query co dieu kien tim kiem
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,column,COLUMN_NOTEID + "=?",seletions,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                note.setNoteId(cursor.getInt(0));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
            }
            else {
                sendData.onSendResult("Null");
            }
        }
        sendData.onSendSingleResult(note);
    }

    public void getAllNote(){
        List<Note> noteList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if(cursor != null){
            cursor.moveToFirst();
            do {
                Note note = new Note();
                note.setNoteId(cursor.getInt(0));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                noteList.add(note);
            }
            while (cursor.moveToNext());
        }
        if(noteList.size() == 0){
            sendData.onSendResult("No database");
        }
        else {
            sendData.onSendListResult(noteList);
        }
    }

    public void updateNote(Note note,int Id){
        sqLiteDatabase = this.getWritableDatabase();
        String[]seletions = {String.valueOf(Id)};
        ContentValues contentValues = new ContentValues(); // lay value de update lai db
        contentValues.put(COLUMN_NOTE_TITLE,note.getNoteTitle());
        contentValues.put(COLUMN_NOTE_CONTENT,note.getNoteContent());
        sqLiteDatabase.update(TABLE_NAME,contentValues,COLUMN_NOTEID+" =?",seletions);
        sqLiteDatabase.close();
        sendData.onSendResult("Updated");
    }

    public void delNote(int Id){
        sqLiteDatabase = this.getWritableDatabase();
        String[]seletions = {String.valueOf(Id)};
        sqLiteDatabase.delete(TABLE_NAME,COLUMN_NOTEID+" =?",seletions);
        sqLiteDatabase.close();
        sendData.onSendResult("Deleted");
    }


}
