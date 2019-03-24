package com.example.nemo1.sqliteexample.Presenter;

import android.content.Context;

import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;
import com.example.nemo1.sqliteexample.Model.DBProcess;

import java.util.List;

public class DBPresenter implements SendData{
    private SendData sendData;
    private DBProcess dbProcess;
    private Context context;

    public DBPresenter(Context context,SendData sendData) {
        this.sendData = sendData;
        this.context = context;
    }

    public void getNote(int Id){
        dbProcess = new DBProcess(context,this);
        dbProcess.getNote(Id);
    }

    public void getAllNotes(){
        dbProcess = new DBProcess(context,this);
        dbProcess.getAllNote();
    }

    public void addNote(Note note){
        dbProcess = new DBProcess(context,this);
        dbProcess.addNote(note);
    }

    public void deleteNote(int Id){
        dbProcess = new DBProcess(context,this);
        dbProcess.delNote(Id);
    }

    public void updateNote(int Id){
        dbProcess = new DBProcess(context,this);
        dbProcess.updateNote(Id);
    }

    @Override
    public void onSearchId(int id) {

    }

    @Override
    public void onCallBack() {

    }

    @Override
    public void onSendResult(String result) {
        sendData.onSendResult(result);
    }

    @Override
    public void onSendListResult(List<Note> noteList) {
        sendData.onSendListResult(noteList);
    }

    @Override
    public void onSendSingleResult(Note note) {
        sendData.onSendSingleResult(note);
    }
}
