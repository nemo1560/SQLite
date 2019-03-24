package com.example.nemo1.sqliteexample;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;
import com.example.nemo1.sqliteexample.View.ControlFragment;
import com.example.nemo1.sqliteexample.View.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SendData {
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout,new MainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchId(int id) {

    }

    //Goi fragment ControlFragment
    @Override
    public void onCallBack() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout,new ControlFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onSendResult(String result) {

    }

    @Override
    public void onSendListResult(List<Note> noteList) {

    }

    @Override
    public void onSendSingleResult(Note note) {

    }

    @Override
    protected void onStop() {
        Toast.makeText(this,"Bye bye",Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}
