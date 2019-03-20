package com.example.nemo1.sqliteexample.Interface;

import com.example.nemo1.sqliteexample.Entity.Note;

import java.util.List;

public interface SendData {
    void onSearchId(int id);
    void onCallBack();
    void onSendResult(String error);
    void onSendListResult(List<Note>noteList);
    void onSendSingleResult(Note note);
}
